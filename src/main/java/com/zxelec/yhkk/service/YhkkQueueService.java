package com.zxelec.yhkk.service;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zxelec.yhkk.entity.CarPushReq;

@Component
public class YhkkQueueService {

	
	private Logger logger = LogManager.getLogger(YhkkQueueService.class);
	
	@Value("${yhkk.queue.size}")
	private Integer size;
	
    private LinkedBlockingQueue<CarPushReq> queue = new LinkedBlockingQueue<CarPushReq>(10000);

    public void putQueue(CarPushReq carPushReq){
        try {
			queue.put(carPushReq);
		} catch (InterruptedException e) {
			logger.error("添加元素失败:e.:{} date:{}",e,JSONObject.toJSONString(carPushReq));
		}
    }
    

    public CarPushReq pollQueue() {
    	CarPushReq carPushReq = queue.poll();
		return carPushReq;
    }

}
