package com.zxelec.yhkk.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscribeReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3556337664374755692L;
	@JsonProperty("SubscribeList")
	private SubscribeList subscribeList;

	public SubscribeList getSubscribeList() {
		return subscribeList;
	}

	public void setSubscribeList(SubscribeList subscribeList) {
		this.subscribeList = subscribeList;
	}


}
