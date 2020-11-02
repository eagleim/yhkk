package com.zxelec.yhkk.job;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zxelec.yhkk.jpa.TImgErrorMsgJpa;
import com.zxelec.yhkk.po.CarpassPushPO;
import com.zxelec.yhkk.service.CarpassPushService;

/**
 * 
 * @author liu.yongquan
 *
 */
@Component
public class DelImgDBJob {
	
	private Logger logger = LogManager.getLogger(DelImgDBJob.class);
	
	@Autowired
	private CarpassPushService carpassPushService;
	
	@Autowired
	private TImgErrorMsgJpa tImgErrorMsgJpa;

	
	@Scheduled(cron = "${img.del.motion.vehicle.task}")
	public void triggerJob() {
		logger.info("定时删除数据库中的过车记录！");
		handleData();
	}
	
	private void handleData() {
		List<CarpassPushPO> poList=carpassPushService.findInsTimeMotionVehicle();
		
		poList.forEach(c->{
			tImgErrorMsgJpa.delTimgErrorMsg(c.getMotorVehicleID());
		});
		
		carpassPushService.delMotionVehicle(poList);
	}
}
