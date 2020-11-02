#!/bin/bash

module="The yhkk Server"
# the waiting time for yhkk startup. in unit seconds
waitingyhkkStartup=20
# the checking interval without HA. in unit seconds
monitorInterval=60
# the http port for debug tool
jpdaPort="33159"
#JAVA_MEM_OPTS=" -server -Xms512m -Xmx512m -XX:PermSize=128m -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=18888"
JAVA_MEM_OPTS=""

status_file="../logs/yhkk_status.log"
jstack_file="../logs/yhkk_jstack.txt"
jmap_file="../logs/yhkk_jmap_histo.txt"
pidfile[0]=".MonitorPID"
pidfile[1]=".PID"

debug=0
jpda=0
pid=0
nofork=""

cd `dirname $0`
yhkk_HOME=`pwd`

jarfile="yhkk-0.0.0.2.jar"

jpdaOptions="-agentlib:jdwp=transport=dt_socket,address=$jpdaPort,server=y,suspend=n"


showUsage()
{
	printf "Usage: $0 <start|stop|status|debug>\\n"
}

pidCounter()
{
	RESULT=`ps -p $1 |grep $1 |grep -v grep | wc -l`
}

tcpServerPortCounter()
{
	RESULT=`lsof -i TCP |awk '{print \$2}'|grep $1 |grep -v grep|wc -l`
}

logMemState()
{
	echo '' >>$status_file
	echo 'free -m' >>$status_file
	free -m >>$status_file
}

logCPUState()
{
	echo '' >>$status_file
	if [ $1 -ne 0 ]; then
		top -b -p $1 -n 1 -H >>$status_file
	else
		top -b -n 1 >>$status_file
	fi
}

logDiskState()
{
	echo '' >>$status_file
	echo 'df -h' >>$status_file
	df -h >>$status_file
}

logIOState()
{
	echo '' >>$status_file
	echo 'iostat -t -x -m 1 2' >>$status_file
	iostat -t -x -m 1 2 >>$status_file
}

logOpenFiles()
{
	echo '' >>$status_file
	echo "lsof |wc -l" >>$status_file
	if [ $1 -ne 0 ]; then
		lsof -p $1|wc -l >>$status_file
	else
		lsof|wc -l >>$status_file
	fi
}

collectModuleInfo()
{
	if [ $1 -ne 0 ]; then
		$JAVA_HOME/bin/jstack -l $1 > $jstack_file
		$JAVA_HOME/bin/jmap -histo:live $1 > $jmap_file
	fi
}

logServerState()
{
	logMemState
	logCPUState $1
	logDiskState
	logIOState
	logOpenFiles $1
	echo '' >>$status_file
}

logServerExited()
{
	echo `date` "$module (pid=$1) was exited." >> $status_file
	logServerState 0
}

logServerInWrongState()
{
	echo `date` "$module (pid=$1) was in wrong state." >> $status_file
	logServerState $1
}

logServerDump()
{
	echo `date` "$module (pid=$1) is dumping." >> $status_file

	debug=1
	logServerState $1
}

startyhkk()
{
	
	if [ $jpda -eq 0 ]; then
		jpdaOptions=""
	fi
	
	#nohup java $JAVA_MEM_OPTS $jpdaOptions -jar -Dloader.path=.,resources,lib $jarfile > /dev/null &
	$JAVA_HOME/bin/java $JAVA_MEM_OPTS -jar -Dloader.path=.,resources,lib $jarfile > /dev/null &
 	# Keep the JAVA process's pid in the file
	pid=$!
	echo $pid > ${pidfile[1]}
	RESULT=0
	leftTime=$waitingyhkkStartup
	while [ $RESULT -eq 0 -a $leftTime -gt 0 ]
	do
			sleep 1
			tcpServerPortCounter $pid
			leftTime=$[${leftTime}-1]
	done
	if [ $RESULT -gt 0 ]; then
		echo `date` "$module (pid=$pid) is started." >> $status_file
		echo -e "yhkk Startup SUCCESS ...."
	else
		echo `date` "$module (pid=$pid) is failed to start in $waitingyhkkStartup seconds." >> $status_file
		echo -e "yhkk Startup fail ...."
	fi
	
	
}
startAndMonitorServer()
{
	echo `date` "Starting $module ..." >> $status_file
	startyhkk
	if [ "$nofork" != "-nofork" ]; then
# sub process
( while [ 1 ]
do
	# monitor the JAVA process
	running=1
	while [ $running -eq 1 ]
	do
		sleep $monitorInterval

		pidCounter $pid
		if [ $RESULT -eq 0 ]; then
			# The JAVA process was exited abnormally
			running=0
			logServerExited $pid
		else
			tcpServerPortCounter $pid
			if [ $RESULT -eq 0 ]; then
				# The UDP server was in wrong state
				running=0
				logServerInWrongState $pid
				kill -9 $pid
			fi
		fi
	done

	echo `date` "Restarting $module ..." >> $status_file
	startyhkk
done; )&

		# Keep the monitor process's pid in the file
		echo $! > ${pidfile[0]};

	else
		rm -f ${pidfile[0]}
	fi
}

startServer()
{
	#mkdir -p logs;
	#touch $jstack_file;
	touch $status_file;
	#touch $jmap_file;

	if [ -e ${pidfile[1]} ]; then
		pid=`cat ${pidfile[1]}`
		if [ $pid -ne 0 ]; then
			pidCounter $pid
			if [ $RESULT -gt 0 ]; then
				printf "$module was running!\\n"
				exit 0
			fi
		fi
	fi

	RESULT=`ps -fu $LOGNAME |grep $jarfile  |grep -v grep| wc -l`
	if [ $RESULT -gt 0 ]; then
		printf "$module was running!\\n"
		exit 0
	fi

	startAndMonitorServer
}


stopServer()
{
# kill the monitor process at first if exits
# and then kill the JAVA process if exits

	for i in 0 1
	do
		if [ -e ${pidfile[$i]} ]; then
			pid=`cat ${pidfile[$i]}`
			if [ $pid -ne 0 ]; then
				pidCounter $pid
				if [ $RESULT -gt 0 ]; then
					if [ $i -eq 1 ]; then
						echo `date` "Stopping $module (pid=$pid) ..." >> $status_file
					fi
					kill -9 $pid
				fi
			fi
			rm -f ${pidfile[$i]}
		fi
	done

	ps -fu $LOGNAME |grep $yhkkCmd |grep -v grep |awk '{print "kill -9 "$2}' | bash
	ps -fu $LOGNAME |grep $runfile |grep -v grep |awk '{print "kill -9 "$2}' | bash
}

checkStatus()
{
	if [ -e ${pidfile[1]} ]; then
		pid=`cat ${pidfile[1]}`
		if [ $pid -ne 0 ]; then
			# check the process whether it was running or not
			pidCounter $pid
			if [ $RESULT -gt 0 ]; then
				# check the UDP server whether it was listening or not
				tcpServerPortCounter $pid
				if [ $RESULT -gt 0 ]; then
					printf "$module is running!\\n"
					exit 0
				else
					logServerInWrongState $pid
				fi
			else
				logServerExited $pid
			fi
		fi
	fi

	printf "$module was stopped!\\n"
	exit 1
}

dumpServer()
{
	if [ -e ${pidfile[1]} ]; then
		pid=`cat ${pidfile[1]}`
		if [ $pid -ne 0 ]; then
			pidCounter $pid
			if [ $RESULT -gt 0 ]; then
				logServerDump $pid
			fi
		fi
	fi
}

showVersion()
{
	echo "3.6.1.3.1"
}

yhkkCmd=$0
case $1 in
	start)
		nofork=$2
		startServer        
		;;
	debug)
		nofork=$2
		jpda=1
		debug=1
		startServer
		;;
	stop)
		stopServer
		;;
	status)
		checkStatus
		;;
	dump)
		dumpServer
		;;
	version)
		showVersion
		;;
	*)
		showUsage
		;;
esac
