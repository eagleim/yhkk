package com.zxelec.yhkk.entity.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VcAPERsp {
	@JsonProperty("APEListObject")
	private VcAPEListObject vcApeListObject;
	public VcAPERsp() {
		// TODO Auto-generated constructor stub
	}
	

	public VcAPERsp(VcAPEListObject vcApeListObject) {
		this.vcApeListObject = vcApeListObject;
	}

	public VcAPEListObject getVcApeListObject() {
		return vcApeListObject;
	}

	public void setVcApeListObject(VcAPEListObject vcApeListObject) {
		this.vcApeListObject = vcApeListObject;
	}
}
