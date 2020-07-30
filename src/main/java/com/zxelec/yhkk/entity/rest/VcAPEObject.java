package com.zxelec.yhkk.entity.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VcAPEObject {
	@JsonProperty("ApeID")
    private String apeID;
	@JsonProperty("CapDirection")
    private int capDirection;
	@JsonProperty("IPAddr")
    private String iPAddr;
	@JsonProperty("IPV6Addr")
    private String iPV6Addr;
	@JsonProperty("IsOnline")
    private String isOnline;
	@JsonProperty("Latitude")
    private double latitude;
	@JsonProperty("Longitude")
    private double longitude;
	@JsonProperty("Model")
    private String model;
	@JsonProperty("MonitorAreaDesc")
    private String monitorAreaDesc;
	@JsonProperty("MonitorDirection")
    private String monitorDirection;
	@JsonProperty("Name")
    private String name;
	@JsonProperty("OrgCode")
    private String orgCode;
	@JsonProperty("OwnerApsID")
    private String ownerApsID;
	@JsonProperty("Password")
    private String password;
	@JsonProperty("Place")
    private String place;
	@JsonProperty("PlaceCode")
    private String placeCode;
	@JsonProperty("Port")
    private int port;
	@JsonProperty("UserId")
    private String userId;
	public String getApeID() {
		return apeID;
	}
	public void setApeID(String apeID) {
		this.apeID = apeID;
	}
	public int getCapDirection() {
		return capDirection;
	}
	public void setCapDirection(int capDirection) {
		this.capDirection = capDirection;
	}
	public String getiPAddr() {
		return iPAddr;
	}
	public void setiPAddr(String iPAddr) {
		this.iPAddr = iPAddr;
	}
	public String getiPV6Addr() {
		return iPV6Addr;
	}
	public void setiPV6Addr(String iPV6Addr) {
		this.iPV6Addr = iPV6Addr;
	}
	public String getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMonitorAreaDesc() {
		return monitorAreaDesc;
	}
	public void setMonitorAreaDesc(String monitorAreaDesc) {
		this.monitorAreaDesc = monitorAreaDesc;
	}
	public String getMonitorDirection() {
		return monitorDirection;
	}
	public void setMonitorDirection(String monitorDirection) {
		this.monitorDirection = monitorDirection;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOwnerApsID() {
		return ownerApsID;
	}
	public void setOwnerApsID(String ownerApsID) {
		this.ownerApsID = ownerApsID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getPlaceCode() {
		return placeCode;
	}
	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
