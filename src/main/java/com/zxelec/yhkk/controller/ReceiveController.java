package com.zxelec.yhkk.controller;

import java.io.BufferedReader;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.http.HttpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zxelec.yhkk.bean.CustomProperties;
import com.zxelec.yhkk.entity.SubscribeRsp;
import com.zxelec.yhkk.entity.rest.VcAPERsp;
import com.zxelec.yhkk.entity.rest.VcTollgateRsp;
import com.zxelec.yhkk.service.ReceiveService;
import com.zxelec.yhkk.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 接收订阅后的信息
 * 
 * @author liu.yongquan
 *
 */
@RestController
public class ReceiveController {

	@Autowired
	private ReceiveService receiveService;
	@Autowired
	private CustomProperties customProperties;


	private Logger logger = LogManager.getLogger(ReceiveController.class);


	/**
	 * 接收通知信息
	 * @param subscribeID
	 * @param request
	 * @return
	 */
	@PostMapping("/VIID/Triggers/Subscribes/{subscribeID}/SubscribeNotifications")
	public SubscribeRsp subscribesNotice(@PathVariable("subscribeID") String subscribeID,
										 HttpServletRequest request) {
		String contentType = request.getContentType();
		List<Map<String,?>> list = new ArrayList<>();
		list =	getBody(request);
		String Id = customProperties.getDeviceId()
				+ DateUtils.data2String(new Date(), DateUtils.dateTimeSecondPatternSSS);
		for (Map<String, ?> map : list) {
			String SubscribeID = (String) map.get("SubscribeID");
			if (!SubscribeID.equals(subscribeID)) {
				logger.info("SubscribeID有误");
				return new SubscribeRsp("-1", "失败【" + SubscribeID + "】有误");
			}
		}
		receiveService.writerJsonFile(list);
		return new SubscribeRsp("0", "成功");
	}

	private List<Map<String,?>> getBody(HttpServletRequest request){
		StringBuffer jb = new StringBuffer();
		String line = null;
		String message = new String();
		List<Map<String,?>> bodylist = new ArrayList<>();
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jb.append(line);
				message += line;
			}
			JSONArray bodyJsonArray = JSON.parseArray(message);
			for(int i=0;i<bodyJsonArray.size();i++){
				JSONObject bodyJson = bodyJsonArray.getJSONObject(i);
				Map<String, ?> bodyMap = JSONObject.parseObject(bodyJson.toJSONString(), new TypeReference<Map<String, ?>>(){});
				bodylist.add(bodyMap);
			}
		} catch (Exception e) { /*report an error*/ }
		return bodylist;
	}
	
	

	  /**
	   * 得到设备信息
	   * @return VcAPERsp
	   */
	  @GetMapping("VIID/APEs")
	  public VcAPERsp getDevices() {
		  VcAPERsp vcAPERsp = receiveService.readDevicesJsonFile();
		  return vcAPERsp;
	  }	  
	  
	  /**
	   * 得到卡口信息
	   * @return  VcTollgateRsp
	   */
	  @GetMapping("VIID/Tollgates") 
	  public VcTollgateRsp getTollgate() {
		  VcTollgateRsp vcTollgateRsp = receiveService.readTollgateJsonFile();
		  return vcTollgateRsp;
	  }		  
}
