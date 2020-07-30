package com.zxelec.yhkk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zxelec.yhkk.bean.CustomProperties;

@RestController
public class YhkkController {
	
	@Autowired
	private CustomProperties customProperties;
	
	@GetMapping("/properties")
	public CustomProperties yhkkStart() {
		return customProperties;
	}
}
