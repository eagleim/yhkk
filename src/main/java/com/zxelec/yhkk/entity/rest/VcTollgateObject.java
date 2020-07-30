package com.zxelec.yhkk.entity.rest;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VcTollgateObject {
	@JsonProperty("TollgateID")
	private String tollgateId;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Latitude")
	private double latitude;
	@JsonProperty("Longitude")
	private double longitude;
	@JsonProperty("PlaceCode")
	private String placeCode;
	@JsonProperty("Place")
	private String place;
	@JsonProperty("Status")
	private int status;
	@JsonProperty("TollgateCat")
	private String tollgateCat;
	@JsonProperty("TollgateUsage")
	private int tollgateUsage;
	@JsonProperty("LaneNum")
	private int laneNum;
	@JsonProperty("OrgCode")
	private String orgCode;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("ActiveTime")
	private Date activeTime;
	public String getTollgateId() {
		return tollgateId;
	}
	public void setTollgateId(String tollgateId) {
		this.tollgateId = tollgateId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTollgateCat() {
		return tollgateCat;
	}
	public void setTollgateCat(String tollgateCat) {
		this.tollgateCat = tollgateCat;
	}
	public int getTollgateUsage() {
		return tollgateUsage;
	}
	public void setTollgateUsage(int tollgateUsage) {
		this.tollgateUsage = tollgateUsage;
	}
	public int getLaneNum() {
		return laneNum;
	}
	public void setLaneNum(int laneNum) {
		this.laneNum = laneNum;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	
}
