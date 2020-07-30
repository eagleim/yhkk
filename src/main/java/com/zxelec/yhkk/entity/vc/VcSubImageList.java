package com.zxelec.yhkk.entity.vc;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 视图库数据结构
 * 图片列表
 */
public class VcSubImageList {
	@JSONField(name = "SubImageInfoObject")
	private List<VcSubImageInfoObject> subImageInfoObject;

	public List<VcSubImageInfoObject> getSubImageInfoObject() {
		return subImageInfoObject;
	}

	public void setSubImageInfoObject(List<VcSubImageInfoObject> subImageInfoObject) {
		this.subImageInfoObject = subImageInfoObject;
	}
	
	
}
