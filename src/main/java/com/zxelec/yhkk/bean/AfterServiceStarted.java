package com.zxelec.yhkk.bean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zxelec.yhkk.utils.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxelec.yhkk.cache.BasicsCache;
import com.zxelec.yhkk.entity.CamPushEntity;
import com.zxelec.yhkk.entity.TollgatePushEntity;
import com.zxelec.yhkk.service.SubscribeService;
import com.zxelec.yhkk.utils.JsonUtils;
/**
 *  程序启动完成后发起订阅功能
 * @author liu.yongquan
 *
 */
@Component
public class AfterServiceStarted implements ApplicationRunner {

	private Logger logger = LogManager.getLogger(AfterServiceStarted.class);

	@Autowired
	private CustomProperties customProperties;

	@Autowired
	private CacheBean cacheBean;

	@Value("classpath:TollgateList.json")
	private Resource areaResTollgate;

	@Value("classpath:DeviceList.json")
	private Resource areaResDevice;

	@Autowired
	private BasicsCache basicsCache;

	@Autowired
	private SubscribeService subscribeService;

	@Value("${subscribe.serverUrl}")
	private String url;

	@Value("classpath:subscribe.json")
	private Resource jsonResource;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		subscribeService.register();
		subscribeService.keepAlive();
		//subscribeService.subscribeAll(url, jsonResource);
		initTollgateList();
		initDeviceList();
	}



	/**
	 * 启动服务时加载tollgate.json文件
	 */
	public void initTollgateList() {
		try {
			File file = areaResTollgate.getFile();
			String jsonData = JsonUtils.jsonRead(file);
			if (!StringUtils.isEmpty(jsonData)) {
				List<TollgatePushEntity> tollList = new ArrayList<>();
				JSONArray array = JSONArray.parseArray(jsonData);
				for (int i = 0; i < array.size(); i++) {
					JSONObject jsonObject2 = array.getJSONObject(i);
					TollgatePushEntity tollgatePushEntity = JSONObject.toJavaObject(jsonObject2, TollgatePushEntity.class);
					tollList.add(tollgatePushEntity);
				}
				//basicsCache.putTollgate(tollList);
				basicsCache.putTollgatePushEntityList(tollList);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 启动服务时加载DeviceList.json
	 */
	public void initDeviceList() {
		try {
			File file = areaResDevice.getFile();
			String jsonData = JsonUtils.jsonRead(file);
			if (!StringUtils.isEmpty(jsonData)) {
				List<CamPushEntity> camList = new ArrayList<>();
				JSONArray array = JSONArray.parseArray(jsonData);
				for (int i = 0; i < array.size(); i++) {
					JSONObject jsonObject2 = array.getJSONObject(i);
					CamPushEntity camEntity = JSONObject.toJavaObject(jsonObject2, CamPushEntity.class);
					camList.add(camEntity);
				}
				basicsCache.putCamPushEntityList(camList);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
