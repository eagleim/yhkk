package com.zxelec.yhkk.entity.vc;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 视图库数据结构
 */
public class MotorVehicleObject implements Serializable {
	@JsonFormat(pattern="yyyyMMddHHmmss",timezone="GMT+8")
	@JSONField(name = "AppearTime")
	private Date appearTime;
	@JSONField(name = "BrandReliability")
    private String brandReliability;
	@JSONField(name = "Calling")
    private int calling;
	@JSONField(name = "CarOfVehicle")
    private String carOfVehicle;
	@JSONField(name = "DescOfFrontItem")
    private String descOfFrontItem;
	@JSONField(name = "DescOfRearItem")
    private String descOfRearItem;
	@JSONField(name = "DeviceID")
    private String deviceID;
	@JSONField(name = "Direction")
    private String direction;
	@JsonFormat(pattern="yyyyMMddHHmmss",timezone="GMT+8")
	@JSONField(name = "DisappearTime")
    private Date disappearTime;
	@JSONField(name = "DrivingStatusCode")
    private String drivingStatusCode;
	@JSONField(name = "FilmColor")
    private String filmColor;
	@JSONField(name = "HasPlate")
    private int hasPlate;
	@JSONField(name = "HitMarkInfo")
    private String hitMarkInfo;
	@JSONField(name = "InfoKind")
    private int infoKind;
	@JSONField(name = "IsAltered")
    private int isAltered;
	@JSONField(name = "IsCovered")
    private boolean isCovered;
	@JSONField(name = "IsDecked")
    private boolean isDecked;
	@JSONField(name = "IsModified")
    private int isModified;
	@JSONField(name = "IsSuspicious")
    private int isSuspicious;
	@JSONField(name = "LaneNo")
    private int laneNo;
	@JSONField(name = "LeftTopX")
    private int leftTopX;
	@JSONField(name = "LeftTopY")
    private int leftTopY;
	@JsonFormat(pattern="yyyyMMddHHmmss",timezone="GMT+8")
	@JSONField(name = "MarkTime")
    private Date markTime;
	@JSONField(name = "MotorVehicleID")
    private String motorVehicleID;
	@JSONField(name = "NameOfPassedRoad")
    private String nameOfPassedRoad;
	@JSONField(name = "NumOfPassenger")
    private int numOfPassenger;
	@JsonFormat(pattern="yyyyMMddHHmmss",timezone="GMT+8")
	@JSONField(name = "PassTime")
    private String passTime;
	@JSONField(name = "PlateCharReliability")
    private String plateCharReliability;
	@JSONField(name = "PlateClass")
    private String plateClass;
	@JSONField(name = "PlateColor")
    private String plateColor;
	@JSONField(name = "PlateDescribe")
    private String plateDescribe;
	@JSONField(name = "PlateNo")
    private String plateNo;
	@JSONField(name = "PlateNoAttach")
    private String plateNoAttach;
	@JSONField(name = "PlateReliability")
    private String plateReliability;
	@JSONField(name = "RearviewMirror")
    private String rearviewMirror;
	@JSONField(name = "RightBtmX")
    private int rightBtmX;
	@JSONField(name = "RightBtmY")
    private int rightBtmY;
	@JSONField(name = "SafetyBelt")
    private int safetyBelt;
	@JSONField(name = "SideOfVehicle")
    private String sideOfVehicle;
	@JSONField(name = "SourceID")
    private String sourceID;
	@JSONField(name = "Speed")
    private double speed;
	@JSONField(name = "StorageUrl1")
    private String storageUrl1;
	@JSONField(name = "StorageUrl2")
    private String storageUrl2;
	@JSONField(name = "StorageUrl3")
    private String storageUrl3;
	@JSONField(name = "StorageUrl4")
    private String storageUrl4;
	@JSONField(name = "StorageUrl5")
    private String storageUrl5;
	@JSONField(name = "SubImageList")
    private VcSubImageList subImageList;
	@JSONField(name = "Sunvisor")
    private int sunvisor;
	@JSONField(name = "TollgateID")
    private String tollgateID;
	@JSONField(name = "UsingPropertiesCode")
    private String usingPropertiesCode;
	@JSONField(name = "VehicleBodyDesc")
    private String vehicleBodyDesc;
	@JSONField(name = "VehicleBrand")
    private String vehicleBrand;
	@JSONField(name = "VehicleChassis")
    private String vehicleChassis;
	@JSONField(name = "VehicleClass")
    private String vehicleClass;
	@JSONField(name = "VehicleColor")
    private String vehicleColor;
	@JSONField(name = "VehicleColorDepth")
    private String vehicleColorDepth;
	@JSONField(name = "VehicleDoor")
    private String vehicleDoor;
	@JSONField(name = "VehicleFrontItem")
    private String vehicleFrontItem;
	@JSONField(name = "VehicleHeight")
    private int vehicleHeight;
	@JSONField(name = "VehicleHood")
    private String vehicleHood;
	@JSONField(name = "VehicleLength")
    private int vehicleLength;
	@JSONField(name = "VehicleModel")
    private String vehicleModel;
	@JSONField(name = "VehicleRearItem")
    private String vehicleRearItem;
	@JSONField(name = "VehicleRoof")
    private String vehicleRoof;
	@JSONField(name = "VehicleShielding")
    private String vehicleShielding;
	@JSONField(name = "VehicleStyles")
    private String vehicleStyles;
	@JSONField(name = "VehicleTrunk")
    private String vehicleTrunk;
	@JSONField(name = "VehicleWheel")
    private String vehicleWheel;
	@JSONField(name = "VehicleWidth")
    private int vehicleWidth;
	@JSONField(name = "VehicleWindow")
    private String vehicleWindow;
	@JSONField(name = "WheelPrintedPattern")
    private String wheelPrintedPattern;
	public Date getAppearTime() {
		return appearTime;
	}
	public void setAppearTime(Date appearTime) {
		this.appearTime = appearTime;
	}
	public String getBrandReliability() {
		return brandReliability;
	}
	public void setBrandReliability(String brandReliability) {
		this.brandReliability = brandReliability;
	}
	public int getCalling() {
		return calling;
	}
	public void setCalling(int calling) {
		this.calling = calling;
	}
	public String getCarOfVehicle() {
		return carOfVehicle;
	}
	public void setCarOfVehicle(String carOfVehicle) {
		this.carOfVehicle = carOfVehicle;
	}
	public String getDescOfFrontItem() {
		return descOfFrontItem;
	}
	public void setDescOfFrontItem(String descOfFrontItem) {
		this.descOfFrontItem = descOfFrontItem;
	}
	public String getDescOfRearItem() {
		return descOfRearItem;
	}
	public void setDescOfRearItem(String descOfRearItem) {
		this.descOfRearItem = descOfRearItem;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Date getDisappearTime() {
		return disappearTime;
	}
	public void setDisappearTime(Date disappearTime) {
		this.disappearTime = disappearTime;
	}
	public String getDrivingStatusCode() {
		return drivingStatusCode;
	}
	public void setDrivingStatusCode(String drivingStatusCode) {
		this.drivingStatusCode = drivingStatusCode;
	}
	public String getFilmColor() {
		return filmColor;
	}
	public void setFilmColor(String filmColor) {
		this.filmColor = filmColor;
	}
	public int getHasPlate() {
		return hasPlate;
	}
	public void setHasPlate(int hasPlate) {
		this.hasPlate = hasPlate;
	}
	public String getHitMarkInfo() {
		return hitMarkInfo;
	}
	public void setHitMarkInfo(String hitMarkInfo) {
		this.hitMarkInfo = hitMarkInfo;
	}
	public int getInfoKind() {
		return infoKind;
	}
	public void setInfoKind(int infoKind) {
		this.infoKind = infoKind;
	}
	public int getIsAltered() {
		return isAltered;
	}
	public void setIsAltered(int isAltered) {
		this.isAltered = isAltered;
	}
	public boolean getIsCovered() {
		return isCovered;
	}
	public void setIsCovered(boolean isCovered) {
		this.isCovered = isCovered;
	}
	public boolean getIsDecked() {
		return isDecked;
	}
	public void setIsDecked(boolean isDecked) {
		this.isDecked = isDecked;
	}
	public int getIsModified() {
		return isModified;
	}
	public void setIsModified(int isModified) {
		this.isModified = isModified;
	}
	public int getIsSuspicious() {
		return isSuspicious;
	}
	public void setIsSuspicious(int isSuspicious) {
		this.isSuspicious = isSuspicious;
	}
	public int getLaneNo() {
		return laneNo;
	}
	public void setLaneNo(int laneNo) {
		this.laneNo = laneNo;
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
	public Date getMarkTime() {
		return markTime;
	}
	public void setMarkTime(Date markTime) {
		this.markTime = markTime;
	}
	public String getMotorVehicleID() {
		return motorVehicleID;
	}
	public void setMotorVehicleID(String motorVehicleID) {
		this.motorVehicleID = motorVehicleID;
	}
	public String getNameOfPassedRoad() {
		return nameOfPassedRoad;
	}
	public void setNameOfPassedRoad(String nameOfPassedRoad) {
		this.nameOfPassedRoad = nameOfPassedRoad;
	}
	public int getNumOfPassenger() {
		return numOfPassenger;
	}
	public void setNumOfPassenger(int numOfPassenger) {
		this.numOfPassenger = numOfPassenger;
	}
	public String getPassTime() {
		return passTime;
	}
	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}
	public String getPlateCharReliability() {
		return plateCharReliability;
	}
	public void setPlateCharReliability(String plateCharReliability) {
		this.plateCharReliability = plateCharReliability;
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
	public String getPlateDescribe() {
		return plateDescribe;
	}
	public void setPlateDescribe(String plateDescribe) {
		this.plateDescribe = plateDescribe;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public String getPlateNoAttach() {
		return plateNoAttach;
	}
	public void setPlateNoAttach(String plateNoAttach) {
		this.plateNoAttach = plateNoAttach;
	}
	public String getPlateReliability() {
		return plateReliability;
	}
	public void setPlateReliability(String plateReliability) {
		this.plateReliability = plateReliability;
	}
	public String getRearviewMirror() {
		return rearviewMirror;
	}
	public void setRearviewMirror(String rearviewMirror) {
		this.rearviewMirror = rearviewMirror;
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
	public int getSafetyBelt() {
		return safetyBelt;
	}
	public void setSafetyBelt(int safetyBelt) {
		this.safetyBelt = safetyBelt;
	}
	public String getSideOfVehicle() {
		return sideOfVehicle;
	}
	public void setSideOfVehicle(String sideOfVehicle) {
		this.sideOfVehicle = sideOfVehicle;
	}
	public String getSourceID() {
		return sourceID;
	}
	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
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
	public VcSubImageList getSubImageList() {
		return subImageList;
	}
	public void setSubImageList(VcSubImageList subImageList) {
		this.subImageList = subImageList;
	}
	public int getSunvisor() {
		return sunvisor;
	}
	public void setSunvisor(int sunvisor) {
		this.sunvisor = sunvisor;
	}
	public String getTollgateID() {
		return tollgateID;
	}
	public void setTollgateID(String tollgateID) {
		this.tollgateID = tollgateID;
	}
	public String getUsingPropertiesCode() {
		return usingPropertiesCode;
	}
	public void setUsingPropertiesCode(String usingPropertiesCode) {
		this.usingPropertiesCode = usingPropertiesCode;
	}
	public String getVehicleBodyDesc() {
		return vehicleBodyDesc;
	}
	public void setVehicleBodyDesc(String vehicleBodyDesc) {
		this.vehicleBodyDesc = vehicleBodyDesc;
	}
	public String getVehicleBrand() {
		return vehicleBrand;
	}
	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}
	public String getVehicleChassis() {
		return vehicleChassis;
	}
	public void setVehicleChassis(String vehicleChassis) {
		this.vehicleChassis = vehicleChassis;
	}
	public String getVehicleClass() {
		return vehicleClass;
	}
	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}
	public String getVehicleColor() {
		return vehicleColor;
	}
	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}
	public String getVehicleColorDepth() {
		return vehicleColorDepth;
	}
	public void setVehicleColorDepth(String vehicleColorDepth) {
		this.vehicleColorDepth = vehicleColorDepth;
	}
	public String getVehicleDoor() {
		return vehicleDoor;
	}
	public void setVehicleDoor(String vehicleDoor) {
		this.vehicleDoor = vehicleDoor;
	}
	public String getVehicleFrontItem() {
		return vehicleFrontItem;
	}
	public void setVehicleFrontItem(String vehicleFrontItem) {
		this.vehicleFrontItem = vehicleFrontItem;
	}
	public int getVehicleHeight() {
		return vehicleHeight;
	}
	public void setVehicleHeight(int vehicleHeight) {
		this.vehicleHeight = vehicleHeight;
	}
	public String getVehicleHood() {
		return vehicleHood;
	}
	public void setVehicleHood(String vehicleHood) {
		this.vehicleHood = vehicleHood;
	}
	public int getVehicleLength() {
		return vehicleLength;
	}
	public void setVehicleLength(int vehicleLength) {
		this.vehicleLength = vehicleLength;
	}
	public String getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	public String getVehicleRearItem() {
		return vehicleRearItem;
	}
	public void setVehicleRearItem(String vehicleRearItem) {
		this.vehicleRearItem = vehicleRearItem;
	}
	public String getVehicleRoof() {
		return vehicleRoof;
	}
	public void setVehicleRoof(String vehicleRoof) {
		this.vehicleRoof = vehicleRoof;
	}
	public String getVehicleShielding() {
		return vehicleShielding;
	}
	public void setVehicleShielding(String vehicleShielding) {
		this.vehicleShielding = vehicleShielding;
	}
	public String getVehicleStyles() {
		return vehicleStyles;
	}
	public void setVehicleStyles(String vehicleStyles) {
		this.vehicleStyles = vehicleStyles;
	}
	public String getVehicleTrunk() {
		return vehicleTrunk;
	}
	public void setVehicleTrunk(String vehicleTrunk) {
		this.vehicleTrunk = vehicleTrunk;
	}
	public String getVehicleWheel() {
		return vehicleWheel;
	}
	public void setVehicleWheel(String vehicleWheel) {
		this.vehicleWheel = vehicleWheel;
	}
	public int getVehicleWidth() {
		return vehicleWidth;
	}
	public void setVehicleWidth(int vehicleWidth) {
		this.vehicleWidth = vehicleWidth;
	}
	public String getVehicleWindow() {
		return vehicleWindow;
	}
	public void setVehicleWindow(String vehicleWindow) {
		this.vehicleWindow = vehicleWindow;
	}
	public String getWheelPrintedPattern() {
		return wheelPrintedPattern;
	}
	public void setWheelPrintedPattern(String wheelPrintedPattern) {
		this.wheelPrintedPattern = wheelPrintedPattern;
	}
	
	
}
