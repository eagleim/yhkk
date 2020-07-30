package com.zxelec.yhkk.service;


import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zxelec.yhkk.entity.vc.MotorVehicleObject;


@Component
public class ViidQueueService {
	
	private Logger logger = LogManager.getLogger(YhkkQueueService.class);

    private LinkedBlockingQueue<MotorVehicleObject> queue = new LinkedBlockingQueue<MotorVehicleObject>(10000);
    
    
    public void putQueue(MotorVehicleObject motorVehicleVc){
        try {
			queue.put(motorVehicleVc);
		} catch (InterruptedException e) {
			logger.error("添加元素失败:e.:{} date:{}",e,JSONObject.toJSONString(motorVehicleVc));
		}
    }
    

    public MotorVehicleObject pollQueue() {
    	MotorVehicleObject motorVehicleVc = queue.poll();
		return motorVehicleVc;
    }
    
    public int size() {
    	return queue.size();
    }


}
