package com.zxelec.yhkk.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CamPushEntity {
	@JsonProperty("NotificationID")
	private String notificationID;
	@JsonProperty("SubscribeID")
	private String subscribeID;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("TriggerTime")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date triggerTime;
	@JsonProperty("DeviceList")
	private List<DeviceList> deviceList;
	public String getNotificationID() {
		return notificationID;
	}
	public void setNotificationID(String notificationID) {
		this.notificationID = notificationID;
	}
	public String getSubscribeID() {
		return subscribeID;
	}
	public void setSubscribeID(String subscribeID) {
		this.subscribeID = subscribeID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getTriggerTime() {
		return triggerTime;
	}
	public void setTriggerTime(Date triggerTime) {
		this.triggerTime = triggerTime;
	}
	public List<DeviceList> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<DeviceList> deviceList) {
		this.deviceList = deviceList;
	}

}
