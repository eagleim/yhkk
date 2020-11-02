package com.zxelec.yhkk.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zxelec.yhkk.entity.vc.MotorVehicle;
import com.zxelec.yhkk.entity.vc.MotorVehicleListObject;
import com.zxelec.yhkk.entity.vc.MotorVehicleObject;
import com.zxelec.yhkk.po.CarpassPushPO;
import com.zxelec.yhkk.service.CarpassPushService;
import com.zxelec.yhkk.utils.CarpassPush2;
import com.zxelec.yhkk.utils.HttpUtil;

@Component
public class CarpassPushJob {
	
	private Logger logger = LogManager.getLogger(CarpassPushJob.class);
	@Value("${VIID.Vc.Url}")
    private String VcUrl;
    @Value("${VIID.Vc.username}")
    private String username;
    @Value("${VIID.Vc.password}")
    private String password;

	@Autowired
	private CarpassPushService carpassPushService;
	
	@Autowired
	private CarpassPush2 carpassPush2;
//	@Scheduled(cron = "0/5 * * * * ?")
	@Scheduled(cron = "${img.error.motion.vehicle.task}")
	public void triggerJob() {
		logger.info("执行图片重新发送任务");
		this.handleCarpass();
	}
	
	private void handleCarpass(){
		List<CarpassPushPO> satisfyList = carpassPushService.findMotionVehicle();
		satisfyList.forEach(c->{
			MotorVehicleObject mvObj = carpassPush2.carpass2Vc(carpassPushService.handleCarpassInfo(c));
			if(null == mvObj) {//等于null表示图片下载失败
				c.setRetriesNumber(c.getRetriesNumber()+1);
				logger.info("图片重新下载失败次数【{}】，修改数据！！！",c.getRetriesNumber());
				carpassPushService.updateMotionVehicle(c);
			}else {
				logger.info("图片重新成功,一共请求图片【{}】次，修改数据并放入队列中！！！",c.getRetriesNumber());
				c.setStatus(1);
				c.setSuccessTime(new Date());
				try {
					carpassPushService.updateMotionVehicle(c);
				}catch (RuntimeException e) {
					logger.error("数据更新异常：{}",e.getMessage(),e);
				}
				sendViid(mvObj);
			}
		});
	}
	
	private void sendViid(MotorVehicleObject mvObj) {
		List<MotorVehicleObject> motorVehicleObjectList = new ArrayList<>();
		motorVehicleObjectList.add(mvObj);
        MotorVehicleListObject motorVehicleListObject = new MotorVehicleListObject(motorVehicleObjectList);
        MotorVehicle motorVehicle = new MotorVehicle(motorVehicleListObject);
        long sp2 = System.currentTimeMillis();
        sendMotionVehicle2Vc(motorVehicle);
        long sp3 = System.currentTimeMillis();
        logger.info("发送视图库耗时:{}",(sp3-sp2));
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
