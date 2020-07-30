package com.zxelec.yhkk.entity.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VcTollgateRsp {
	@JsonProperty("TollgateListObject")
	private VcTollgateListObject tollgateListObject;

	public VcTollgateListObject getTollgateListObject() {
		return tollgateListObject;
	}

	public void setTollgateListObject(VcTollgateListObject tollgateListObject) {
		this.tollgateListObject = tollgateListObject;
	}
	
}
