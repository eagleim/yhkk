package com.zxelec.yhkk.entity.vc;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 视图库过车记录列表对象
 */
public class MotorVehicleListObject {
	@JSONField(name = "MotorVehicleObject")
	private List<MotorVehicleObject> motorVehicleObject;
	
	public MotorVehicleListObject() {
		// TODO Auto-generated constructor stub
	}
	
	
	public MotorVehicleListObject(List<MotorVehicleObject> motorVehicleObject) {
		this.motorVehicleObject = motorVehicleObject;
	}


	public List<MotorVehicleObject> getMotorVehicleObject() {
		return motorVehicleObject;
	}

	public void setMotorVehicleObject(List<MotorVehicleObject> motorVehicleObject) {
		this.motorVehicleObject = motorVehicleObject;
	}
	
}
