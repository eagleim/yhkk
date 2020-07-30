package com.zxelec.yhkk.entity.vc;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 视图库数据结构
 * 图片信息
 */
public class VcSubImageInfoObject {
	@JSONField(name = "Data")
	private String data;
	@JSONField(name = "DeviceID")
	private String deviceID;
	@JSONField(name = "EventSort")
	private int eventSort;
	@JSONField(name = "FileFormat")
	private String fileFormat;
	@JSONField(name = "Height")
	private int height;
	@JSONField(name = "ImageID")
	private String imageID;
	@JSONField(name = "ShotTime")
	private Date shotTime;
	@JSONField(name = "StoragePath")
	private String storagePath;
	@JSONField(name = "Type")
	private String type;
	@JSONField(name = "Width")
	private int width;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public int getEventSort() {
		return eventSort;
	}
	public void setEventSort(int eventSort) {
		this.eventSort = eventSort;
	}
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getImageID() {
		return imageID;
	}
	public void setImageID(String imageID) {
		this.imageID = imageID;
	}
	public Date getShotTime() {
		return shotTime;
	}
	public void setShotTime(Date shotTime) {
		this.shotTime = shotTime;
	}
	public String getStoragePath() {
		return storagePath;
	}
	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
}
