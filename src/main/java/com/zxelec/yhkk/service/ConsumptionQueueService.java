package com.zxelec.yhkk.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zxelec.yhkk.service.CarService;
import com.zxelec.yhkk.entity.CarPushReq;

@Component
public class ConsumptionQueueService extends Thread {
	
	private Logger logger = LogManager.getLogger(ConsumptionQueueService.class);
	
	@Autowired
	private YhkkQueueService yhkkQueueService;
	
	@Autowired
	private CarService carService;
	
    @Override
    public void run() {
    	this.setDaemon(true);
    	this.setName("yhkkConsumptionQueue");
        consumptionQueueData();
    }
    
    

    /**
     * 消费队列数据
     */
    private void consumptionQueueData(){
    	while(true) {
    		CarPushReq carEntity= yhkkQueueService.pollQueue();
        	if(carEntity!=null) {
        		carService.receiveMotionVehicleData(carEntity);// 调用其他人方法发送给kafka
        	}else {
        		logger.info("队列中暂无数数据休眠2秒...");
        		try {
					Thread.sleep(2*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
    	}
    }
}
