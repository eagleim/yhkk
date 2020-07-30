package com.zxelec.yhkk.entity.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VcTollgateListObject {
	@JsonProperty("TollgateObject")
	private List<VcTollgateObject> tollgateObject;

	public List<VcTollgateObject> getTollgateObject() {
		return tollgateObject;
	}

	public void setTollgateObject(List<VcTollgateObject> tollgateObject) {
		this.tollgateObject = tollgateObject;
	} 
	
	
}
