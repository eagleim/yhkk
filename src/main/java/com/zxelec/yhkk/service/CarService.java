package com.zxelec.yhkk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxelec.yhkk.entity.CarPushReq;
import com.zxelec.yhkk.entity.CarpassPushEntity;
import com.zxelec.yhkk.entity.MessageMetaDataType;
import com.zxelec.yhkk.entity.MotionVehicleType;
import com.zxelec.yhkk.entity.ViidResult;
import com.zxelec.yhkk.entity.VissMessage;
import com.zxelec.yhkk.entity.vc.MotorVehicle;
import com.zxelec.yhkk.entity.vc.MotorVehicleListObject;
import com.zxelec.yhkk.entity.vc.MotorVehicleObject;
import com.zxelec.yhkk.utils.CarpassPush2;
import com.zxelec.yhkk.utils.DateUtils;
import com.zxelec.yhkk.utils.HttpUtil;
import com.zxelec.yhkk.utils.VissMessageConvertUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author: dily
 * @create: 2019-07-12 20:32
 * 过车记录发送
 **/
@PropertySource(value = {"classpath:server.properties"}, encoding = "UTF-8")
@Service
public class CarService {

    @Autowired
    private KafkaTemplate kafkaTemplate;
    
    @Autowired
    private ViidQueueService viidQueueService;
    
    @Autowired
    private  ThreadPoolTaskExecutor asyncServiceExecutor;

    private Logger logger = LogManager.getLogger(CarService.class);
    @Value("${kafka.topic}")
    private String topic;
    @Value("${message.sender}")
    private String sender;
    @Value("${message.source}")
    private String source;
    @Value("${VIID.Vc.Url}")
    private String VcUrl;
    @Value("${VIID.Vc.username}")
    private String username;
    @Value("${VIID.Vc.password}")
    private String password;
    @Value("${kafka.status}")
    private Integer kafkaStatus;
    @Value("${VIID.Vc.package.size}")
    private Integer server_viidPackageSize;
    
    @Autowired
    private CarpassPush2 carpassPush2;
    
	
	@Autowired
	private CarpassPushService carpassPushService;
    
    /**
     * 异步多线程逐条处理过车记录
     * @param
     */
//    @Async
    public void DealWithVehicleRecord(CarpassPushEntity carpassPushEntity){
        //logger.fatal(carpassPushEntity.getDeviceID());
        /** 发送给kafka **/
        if (kafkaStatus.equals(0)) {
            MotionVehicleType motionVehicleKafka = CarpassPush2.carpassPush2MotionVehicle(carpassPushEntity);
            analysisMotionVehicleData(motionVehicleKafka);
        }
        /** 发送到视图库 **/
        List<MotorVehicleObject> motorVehicleObjectList = new ArrayList<>();
        long sp1 = System.currentTimeMillis();
        MotorVehicleObject motorVehicleVc = carpassPush2.carpass2Vc(carpassPushEntity);
        if(motorVehicleVc==null) {
        	logger.info("motorVehicleVc==null,停止上传该条过车记录至视图库，并写入DB！");
        	carpassPushService.insMotionVehicle(carpassPushEntity);
        	return;
        }
        motorVehicleObjectList.add(motorVehicleVc);
        MotorVehicleListObject motorVehicleListObject = new MotorVehicleListObject(motorVehicleObjectList);
        MotorVehicle motorVehicle = new MotorVehicle(motorVehicleListObject);
        long sp2 = System.currentTimeMillis();
        logger.info("下载图片和组织报文耗时:{}",(sp2-sp1));
        sendMotionVehicle2Vc(motorVehicle);
        long sp3 = System.currentTimeMillis();
        logger.info("发送视图库耗时:{}",(sp3-sp2));
    }
    
    /**
     * 
     * 异步下载图片并加入视图库上传队列
     * 
     * @param carpassPush
     */    
    public void AsyncDownloadVehiclePIC(CarpassPushEntity carpassPushEntity) {
    	
		long sp1 = System.currentTimeMillis();

		MotorVehicleObject motorVehicleVc = carpassPush2.carpass2Vc(carpassPushEntity);
        if(motorVehicleVc==null) {
        	logger.info("motorVehicleVc==null,停止上传该条过车记录至视图库，并写入DB！");
        	carpassPushService.insMotionVehicle(carpassPushEntity);
        	return;
         }
        viidQueueService.putQueue(motorVehicleVc);
    	
    	long sp2 = System.currentTimeMillis();
        logger.info("下载图片并加入队列耗时:{}",(sp2-sp1));
    	

    }

    /**
     * 异步发送过车记录到视图库
     * 每次发送10条
     */
    public void AsyncSendToViid() {
    	
    	if (viidQueueService.size() == 0) {
    		return;
    	}
    	int packageSize = 0;
    	if (viidQueueService.size() < server_viidPackageSize) {
    		packageSize = viidQueueService.size();	
    	} else {
    		packageSize = server_viidPackageSize;
    	}
    	
    	long sp2 = System.currentTimeMillis();
    	
    	List<MotorVehicleObject> motorVehicleObjectList = new ArrayList<>();
    	for(int j = 0; j < packageSize; j++) {
    		MotorVehicleObject motorVehicleVc = viidQueueService.pollQueue();
    		motorVehicleObjectList.add(motorVehicleVc);
    	}
    	MotorVehicleListObject motorVehicleListObject = new MotorVehicleListObject(motorVehicleObjectList);
        MotorVehicle motorVehicle = new MotorVehicle(motorVehicleListObject);
        
        sendMotionVehicle2Vc(motorVehicle);
        long sp3 = System.currentTimeMillis();
        logger.info("发送视图库耗时:{}",(sp3-sp2));
    }
    
    /**
     * 接收平台数据
     *
     * @param carPushReq 数据对象
     */
    public void receiveMotionVehicleData(CarPushReq carPushReq) {
        
        if (carPushReq == null) {
            logger.error("没有接收到过车记录carPushReq");
        } else {
        	
        	List<CarpassPushEntity> carpassPush = carPushReq.getMotorVehicleObjectList();
            
        	if (carpassPush.isEmpty()) {
        		logger.error("carPushReq中没有过车记录carpassPush");
        		return;
        	}
        	
        	logger.info("本次从怡和接收数据包总计:{}条过车记录",(carpassPush.size()));

        	/**
        	 * 
        	 * 原线程池异步处理方式：逐条过车记录丢给线程池处理
        	 * 
        	 * 2020-03-25 15：20屏蔽
        	 * 
        	 */
            for (CarpassPushEntity carpassPushEntity : carpassPush) {
            	asyncServiceExecutor.execute(()->{
            		logger.info("异步发送！！！");
            		DealWithVehicleRecord(carpassPushEntity);
            	});
            }


            /**
             * 新线程池异步处理方式：逐条过车图片下载，再将过车记录打包丢给线程池处理
             * 
             * 2020-03-25 15:20开启
             * 
             **/

        	/**
        	 * 
        	for (CarpassPushEntity carpassPushEntity : carpassPush) {
            	asyncServiceExecutor.execute(()->{
            		logger.info("异步从交警服务器下载过车图片！！！");
            		AsyncDownloadVehiclePIC(carpassPushEntity);
            	});
        		
        	}
        	
        	asyncServiceExecutor.execute(()->{
        		logger.info("异步发送过车记录到视图库！！！");
        		AsyncSendToViid();
        	});

                    	 */

            
            
        } 
    }

    /**
     * 解析实体对象转换为二进制
     *
     * @param motionVehicleType
     */
    private void analysisMotionVehicleData(MotionVehicleType motionVehicleType) {

        /** 消息头 **/
        MessageMetaDataType messageMetaDataType = new MessageMetaDataType();
        messageMetaDataType.setSendTime(DateUtils.data2String());
        VissMessage<MotionVehicleType> vissMessage = new VissMessage();
        messageMetaDataType.setSender(sender);
        messageMetaDataType.setSouce(source);
        vissMessage.setType(messageMetaDataType);
        vissMessage.setBody(motionVehicleType);
        try {
            byte[] by = VissMessageConvertUtils.vissMessage2Byte(vissMessage);
            sendMotionVehicleKafka(by);
        } catch (UnsupportedEncodingException e) {
            logger.error("过车记录转二进制失败！！！");
            e.printStackTrace();
        }
    }

    /**
     * 把转码后的二进制数组发给kafka
     *
     * @param by
     */
    private void sendMotionVehicleKafka(byte[] by) {
        ListenableFuture listenableFuture = kafkaTemplate.send(topic, by);
        String receive = "";
        try {
            receive = JSON.toJSONString(listenableFuture.get());
            if (receive.isEmpty())
                logger.error("数据发送失败");
            else
                logger.info("生产成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把过车记录发给视图库
     *
     * @param motorVehicle
     */
    private void sendMotionVehicle2Vc(MotorVehicle motorVehicle) {
        
		String motorVehcle = JSON.toJSONString(motorVehicle);
		
		if(motorVehcle.equals("")) {
			return;
		}
		
		HttpUtil.postToVIID(VcUrl, motorVehcle, username, password);
    		
    }

}
