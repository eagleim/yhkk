package com.zxelec.yhkk.entity.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VcAPEListObject {
	@JsonProperty("APEObject")
	private List<VcAPEObject> apeObject;

	public List<VcAPEObject> getApeObject() {
		return apeObject;
	}

	public void setApeObject(List<VcAPEObject> apeObject) {
		this.apeObject = apeObject;
	}


}
