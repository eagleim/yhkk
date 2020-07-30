package com.zxelec.yhkk.entity.vc;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *
 * 视图库过车记录最外层
 */

public class MotorVehicle {
	@JSONField(name = "MotorVehicleListObject")
	private MotorVehicleListObject motorVehicleListObject;
	
	public MotorVehicle() {
		// TODO Auto-generated constructor stub
	}

	public MotorVehicle(MotorVehicleListObject motorVehicleListObject) {
		this.motorVehicleListObject = motorVehicleListObject;
	}

	public MotorVehicleListObject getMotorVehicleListObject() {
		return motorVehicleListObject;
	}

	public void setMotorVehicleListObject(MotorVehicleListObject motorVehicleListObject) {
		this.motorVehicleListObject = motorVehicleListObject;
	}
}
