package com.zxelec.yhkk.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TollgateList {
	
	@JsonProperty("TollgateID" )
	private String tollgateID;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Longtidude")
	private double longtidude;
	@JsonProperty("Latitude")
	private double latitude;
	@JsonProperty("PlaceCode")
	private String placeCode;
	@JsonProperty("Place")
	private String place;
	@JsonProperty("Status")
	private int status;
	@JsonProperty("TollgateType")
	private int tollgateType;
	@JsonProperty("LaneNum")
	private int laneNum;
	@JsonProperty("OrgCode")
	private String orgCode;
	@JsonProperty("ActiveTime")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date activeTime;
	public String getTollgateID() {
		return tollgateID;
	}
	public void setTollgateID(String tollgateID) {
		this.tollgateID = tollgateID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTollgateType() {
		return tollgateType;
	}
	public void setTollgateType(int tollgateType) {
		this.tollgateType = tollgateType;
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
