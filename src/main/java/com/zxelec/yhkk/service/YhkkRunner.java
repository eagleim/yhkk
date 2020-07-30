package com.zxelec.yhkk.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class YhkkRunner implements CommandLineRunner {
	
	private Logger logger = LogManager.getLogger(YhkkRunner.class);

	@Autowired
	private ConsumptionQueueService consumptionQueueService;
    @Override
    public void run(String... args) throws Exception {
    	logger.info("监听队列启动");
        new Thread(consumptionQueueService).start();
    }
}
