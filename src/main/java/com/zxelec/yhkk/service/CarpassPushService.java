package com.zxelec.yhkk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zxelec.yhkk.entity.CarpassPushEntity;
import com.zxelec.yhkk.jpa.CarpassPushJpa;
import com.zxelec.yhkk.jpa.TImgErrorMsgJpa;
import com.zxelec.yhkk.po.CarpassPushPO;
import com.zxelec.yhkk.po.TImgErrorMsgPO;
import com.zxelec.yhkk.utils.DateUtils;

@Service
public class CarpassPushService {
	
	private Logger logger = LogManager.getLogger(CarpassPushService.class);
	
	@Autowired
	private CarpassPushJpa carpassPushJpa;
	
	@Autowired
	private TImgErrorMsgJpa tIimgErrorMsgJpa;
	
	@Value("${img.error.retain.days}")
	private int retainDays;
	
	
	/**
	 * 新增图片获取失败后写入数据库
	 * @param entity 过车对象
	 */
	public void insMotionVehicle(CarpassPushEntity entity) {
		logger.info("请求图片失败数据写入DB:过车时间：{},车牌：{},设备编号:{}",
				DateUtils.data2String(entity.getPassTime(),"yyyy-MM-dd HH:mm:ss")
				,entity.getPlateNo(),entity.getDeviceID());
		CarpassPushPO po = this.handleCarpassInfo(entity);
		po.setInsTime(new Date());
		try {
			carpassPushJpa.save(po);
		}catch (RuntimeException e) {
			logger.error("DB#ERROR#e.getMessage:{},MESSAGE:{},e",
					e.getMessage(),JSONObject.toJSONString(entity));
		}
	}
	
	/**
	 * 二次发送数据失败更新次数
	 * @param entity
	 */
	public void updateMotionVehicle(CarpassPushPO entity) {
		carpassPushJpa.save(entity);
	}
	
	public List<CarpassPushPO> findMotionVehicle() {
		List<CarpassPushPO> imgErrorMotionList = carpassPushJpa.findAll(new Specification<CarpassPushPO>() {
			@Override
			public Predicate toPredicate(Root<CarpassPushPO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				Date s_time = DateUtils.dateString2Date("yyyy-MM-dd");
				Date e_time = DateUtils.getDateSubtractNday(DateUtils.dateString2Date("yyyy-MM-dd"),1);
				predicates.add(cb.greaterThanOrEqualTo(root.get("insTime"), s_time));
				predicates.add(cb.lessThan(root.get("insTime"), e_time));
				predicates.add(cb.equal(root.get("status"), 0));
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		});
		return imgErrorMotionList;
	}
	
	/**
	 * 查询指定插入时间的记录
	 * @return
	 */
	public List<CarpassPushPO> findInsTimeMotionVehicle(){
		List<CarpassPushPO> imgErrorMotionList = carpassPushJpa.findAll(new Specification<CarpassPushPO>() {
			@Override
			public Predicate toPredicate(Root<CarpassPushPO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				Date s_time = DateUtils.getDateSubtractNday(
						DateUtils.dateString2Date("yyyy-MM-dd"),-retainDays);
				predicates.add(cb.lessThanOrEqualTo(root.get("insTime"), s_time));
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		});
		return imgErrorMotionList;
	}
	
	public void insTimgErrorMsg(Map<String,Object> param) {
		TImgErrorMsgPO entity = new TImgErrorMsgPO();
		entity.setCode(param.get("statusCode")+"");
		entity.setImgErrorMessage(param.get("errMessage")+"");
		entity.setInsTime(new Date());
		entity.setMotorVehicleId(param.get("motorVehicleId")+"");
		entity.setImgUrl(param.get("imgUrl")+"");
		tIimgErrorMsgJpa.save(entity);
	}
	/**
	 * 删除数据
	 * @param entities
	 */
	public void delMotionVehicle(List<CarpassPushPO> entities) {
		carpassPushJpa.delete(entities);
	}
	
	/**
	 * 数据库PO转怡和过车对象
	 * @param e
	 * @return
	 */
	public CarpassPushEntity handleCarpassInfo(CarpassPushPO e) {
		CarpassPushEntity po = new CarpassPushEntity();
		po.setMotorVehicleID(e.getMotorVehicleID());
		po.setDeviceID(e.getDeviceID());
		po.setDirection(e.getDirection());
		po.setLaneId(e.getLaneId());
		po.setLeftTopX(e.getLeftTopX());
		po.setLeftTopY(e.getLeftTopY());
		po.setPassTime(e.getPassTime());
		po.setPlaceCode(e.getPlaceCode());
		po.setPlateClass(e.getPlateClass());
		po.setPlateColor(e.getPlateColor());
		po.setPlateNo(e.getPlateNo());
		
		po.setRightBtmX(e.getRightBtmX());
		po.setRightBtmY(e.getRightBtmY());
		po.setSourceID(e.getSourceID());
		po.setSpeed(e.getSpeed());
	
		po.setStorageUrl1(e.getStorageUrl1());
		po.setStorageUrl2(e.getStorageUrl2());
		po.setStorageUrl3(e.getStorageUrl3());
		po.setStorageUrl4(e.getStorageUrl4());
		po.setStorageUrl5(e.getStorageUrl5());
		po.setTransportID(e.getTransportID());
		po.setVehicleBrand(e.getVehicleBrand());
		po.setVehicleClass(e.getVehicleClass());
		po.setVehicleColor(e.getVehicleColor());
		po.setVehicleColorDepth(e.getVehicleColorDepth());
		po.setVehicleHeight(e.getVehicleHeight());
		po.setVehicleLength(e.getVehicleLength());
		po.setVehicleWidth(e.getVehicleWidth());
		return po;
	}
	
	/**
	 * 推送对象转换数据库PO对象
	 * @param e 推送对象
	 * @return 数据库PO对象
	 */
	private CarpassPushPO handleCarpassInfo(CarpassPushEntity e) {
		CarpassPushPO po = new CarpassPushPO();
		po.setMotorVehicleID(e.getMotorVehicleID());
		po.setDeviceID(e.getDeviceID());
		po.setDirection(e.getDirection());
		po.setLaneId(e.getLaneId());
		po.setLeftTopX(e.getLeftTopX());
		po.setLeftTopY(e.getLeftTopY());
		po.setPassTime(e.getPassTime());
		po.setPlaceCode(e.getPlaceCode());
		po.setPlateClass(e.getPlateClass());
		po.setPlateColor(e.getPlateColor());
		po.setPlateNo(e.getPlateNo());
		
		po.setRightBtmX(e.getRightBtmX());
		po.setRightBtmY(e.getRightBtmY());
		po.setSourceID(e.getSourceID());
		po.setSpeed(e.getSpeed());
	
		po.setStorageUrl1(e.getStorageUrl1());
		po.setStorageUrl2(e.getStorageUrl2());
		po.setStorageUrl3(e.getStorageUrl3());
		po.setStorageUrl4(e.getStorageUrl4());
		po.setStorageUrl5(e.getStorageUrl5());
		po.setTransportID(e.getTransportID());
		po.setVehicleBrand(e.getVehicleBrand());
		po.setVehicleClass(e.getVehicleClass());
		po.setVehicleColor(e.getVehicleColor());
		po.setVehicleColorDepth(e.getVehicleColorDepth());
		po.setVehicleHeight(e.getVehicleHeight());
		po.setVehicleLength(e.getVehicleLength());
		po.setVehicleWidth(e.getVehicleWidth());
		po.setRetriesNumber(0);
		po.setStatus(0);
		return po;
	}
}
