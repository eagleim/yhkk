package com.zxelec.yhkk.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 过车记录推送实体
 * @author liu.yongquan
 *
 */
@Entity
@Table(name = "t_motion_vehicle")
public class CarpassPushPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8043262670346628099L;
	/**
	 * 
	 * @Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "disposition_id", nullable = false, length = 64)
	 */
//	@Id	//主键id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)//主键生成策略
//	@Column(name="id")//数据库字段名
//	private Long id;
	@Id	//主键id
	@Column(name="motor_vehicle_id")//数据库字段名
	private String motorVehicleID;
	@Column(name="source_id")//数据库字段名
    private String sourceID;
	
	@Column(name="transport_id")//数据库字段名
    private String transportID;
	
	@Column(name="device_id")//数据库字段名
    private String deviceID;
	
	@Column(name="storage_url1")//数据库字段名
    private String storageUrl1;
    
	@Column(name="storage_url2")//数据库字段名
    private String storageUrl2;
	
	@Column(name="storage_url3")//数据库字段名
    private String storageUrl3;
	
	@Column(name="storage_url4")//数据库字段名
    private String storageUrl4;
	
	@Column(name="storage_url5")//数据库字段名
	private String storageUrl5;
	
	@Column(name="left_top_x")//数据库字段名
	private int leftTopX;
    
	@Column(name="left_top_y")//数据库字段名
	private int leftTopY;
    
	@Column(name="right_btm_x")//数据库字段名
	private int rightBtmX;
    
	@Column(name="right_btm_y")//数据库字段名
	private int rightBtmY;
	
	@Column(name="plate_class")//数据库字段名
	private String plateClass;
    
	@Column(name="plate_color")//数据库字段名
	private String plateColor;
    
	@Column(name="plate_no")//数据库字段名
	private String plateNo;
    
	@Column(name="speed")//数据库字段名
	private int speed;
    
	@Column(name="direction")//数据库字段名
	private String direction;
    
	@Column(name="vehicle_class")//数据库字段名
	private String vehicleClass;
	
	@Column(name="vehicle_brand")//数据库字段名
	private String vehicleBrand;
	
	@Column(name="vehicle_length")//数据库字段名
	private int vehicleLength;
	
	
	@Column(name="vehicle_width")//数据库字段名
	private int vehicleWidth;
    
	@Column(name="vehicle_height")//数据库字段名
	private int vehicleHeight;
    
	@Column(name="vehicle_color")//数据库字段名
	private String vehicleColor;
    
	@Column(name="vehicle_color_depth")//数据库字段名
	private Integer vehicleColorDepth;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JSONField(name = "PassTime",format="yyyy-MM-dd HH:mm:ss")
	@Column(name="pass_time")//数据库字段名
	private Date passTime;
    
	@Column(name="lane_id")//数据库字段名
	private int laneId;
    
	@Column(name="place_code")//数据库字段名
	private String placeCode;
	
	@Column(name = "retries_number")
	private int retriesNumber;

	@Column(name = "status")
	private int status;
	
	@Column(name = "ins_time")
	private Date insTime;
	
	@Column(name = "success_time")
	private Date successTime;
	
	
	public Date getInsTime() {
		return insTime;
	}
	public void setInsTime(Date insTime) {
		this.insTime = insTime;
	}
	public Date getSuccessTime() {
		return successTime;
	}
	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	public int getLeftTopX() {
		return leftTopX;
	}
	public void setLeftTopX(int leftTopX) {
		this.leftTopX = leftTopX;
	}
	public int getLeftTopY() {
		return leftTopY;
	}
	public void setLeftTopY(int leftTopY) {
		this.leftTopY = leftTopY;
	}
	public int getRightBtmX() {
		return rightBtmX;
	}
	public void setRightBtmX(int rightBtmX) {
		this.rightBtmX = rightBtmX;
	}
	public int getRightBtmY() {
		return rightBtmY;
	}
	public void setRightBtmY(int rightBtmY) {
		this.rightBtmY = rightBtmY;
	}
	public String getVehicleBrand() {
		return vehicleBrand;
	}
	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}
	public String getMotorVehicleID() {
		return motorVehicleID;
	}
	public void setMotorVehicleID(String motorVehicleID) {
		this.motorVehicleID = motorVehicleID;
	}
	public String getSourceID() {
		return sourceID;
	}
	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}
	public String getTransportID() {
		return transportID;
	}
	public void setTransportID(String transportID) {
		this.transportID = transportID;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getStorageUrl1() {
		return storageUrl1;
	}
	public void setStorageUrl1(String storageUrl1) {
		this.storageUrl1 = storageUrl1;
	}
	public String getStorageUrl2() {
		return storageUrl2;
	}
	public void setStorageUrl2(String storageUrl2) {
		this.storageUrl2 = storageUrl2;
	}
	
	public String getPlateClass() {
		return plateClass;
	}
	public void setPlateClass(String plateClass) {
		this.plateClass = plateClass;
	}
	public String getPlateColor() {
		return plateColor;
	}
	public void setPlateColor(String plateColor) {
		this.plateColor = plateColor;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getVehicleClass() {
		return vehicleClass;
	}
	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}
	public int getVehicleLength() {
		return vehicleLength;
	}
	public void setVehicleLength(int vehicleLength) {
		this.vehicleLength = vehicleLength;
	}
	public int getVehicleWidth() {
		return vehicleWidth;
	}
	public void setVehicleWidth(int vehicleWidth) {
		this.vehicleWidth = vehicleWidth;
	}
	public int getVehicleHeight() {
		return vehicleHeight;
	}
	public void setVehicleHeight(int vehicleHeight) {
		this.vehicleHeight = vehicleHeight;
	}
	public String getVehicleColor() {
		return vehicleColor;
	}
	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}
	public Integer getVehicleColorDepth() {
		return vehicleColorDepth;
	}
	public void setVehicleColorDepth(Integer vehicleColorDepth) {
		this.vehicleColorDepth = vehicleColorDepth;
	}
	public Date getPassTime() {
		return passTime;
	}
	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}
	
	
	public int getLaneId() {
		return laneId;
	}
	public void setLaneId(int laneId) {
		this.laneId = laneId;
	}
	public String getPlaceCode() {
		return placeCode;
	}
	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}
	public String getStorageUrl3() {
		return storageUrl3;
	}
	public void setStorageUrl3(String storageUrl3) {
		this.storageUrl3 = storageUrl3;
	}
	public String getStorageUrl4() {
		return storageUrl4;
	}
	public void setStorageUrl4(String storageUrl4) {
		this.storageUrl4 = storageUrl4;
	}
	public String getStorageUrl5() {
		return storageUrl5;
	}
	public void setStorageUrl5(String storageUrl5) {
		this.storageUrl5 = storageUrl5;
	}
	public int getRetriesNumber() {
		return retriesNumber;
	}
	public void setRetriesNumber(int retriesNumber) {
		this.retriesNumber = retriesNumber;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
