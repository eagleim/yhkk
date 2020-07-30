package com.zxelec.yhkk.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class SubscribeRsp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4509029476226938161L;
	
//	@JSONField(name = "StatusCode")
//	private String statusCode;
	
	@JSONField(name = "ConfirmatStatus")
	private String confirmatStatus;

	@JSONField(name = "msg")
	private String msg;
	
//	@JSONField(name = "StatusString")
//	private String statusString;
//
//	@JSONField(name = "Id")
//	private String id;
//
//	@JSONField(name = "RequestURL")
//	private String requestURL;
	

	public SubscribeRsp() {
	}

	public SubscribeRsp(String confirmatStatus, String msg) {
		this.confirmatStatus = confirmatStatus;
		this.msg = msg;
	}

	public String getConfirmatStatus() {
		return confirmatStatus;
	}

	public void setConfirmatStatus(String confirmatStatus) {
		this.confirmatStatus = confirmatStatus;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
