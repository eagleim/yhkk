package com.zxelec.yhkk.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceList {
	@JsonProperty("DeviceID")
	private String deviceID;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Model")
	private String model;
	@JsonProperty("IPAddr")
	private String iPAddr;
	@JsonProperty("Longtidude")
	private double longtidude;
	@JsonProperty("Latitude")
	private double latitude;
	@JsonProperty("PlaceCode")
	private String placeCode;
	@JsonProperty("Place")
	private String place;
	@JsonProperty("OrgCode")
	private String orgCode;
	@JsonProperty("Capdirection")
	private int capdirection;
	@JsonProperty("updateTime")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updateTime;

	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getiPAddr() {
		return iPAddr;
	}
	public void setiPAddr(String iPAddr) {
		this.iPAddr = iPAddr;
	}
	public double getLongtidude() {
		return longtidude;
	}
	public void setLongtidude(double longtidude) {
		this.longtidude = longtidude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getPlaceCode() {
		return placeCode;
	}
	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public int getCapdirection() {
		return capdirection;
	}
	public void setCapdirection(int capdirection) {
		this.capdirection = capdirection;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
