package com.zxelec.yhkk.service;

import java.io.IOException;
import java.util.List;

import com.zxelec.yhkk.bean.CustomProperties;
import com.zxelec.yhkk.utils.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zxelec.yhkk.bean.CacheBean;
import com.zxelec.yhkk.entity.SubscribeRsp;
import com.zxelec.yhkk.utils.JsonUtil;
import org.springframework.util.StringUtils;

/**
 * 订阅service【车辆、卡口、设备、车道】
 * @author liu.yongquan
 *
 */
@Service
public class SubscribeService {
	@Autowired
	private CacheBean cacheBean;
	@Autowired
	private CustomProperties customProperties;

	private Logger logger = LogManager.getLogger(SubscribeService.class);

	/**
	 * 根据JSON订阅
	 * @param jsonString
	 * @param url
	 * @return
	 */
	public SubscribeRsp subscribeByJSON(String url, String jsonString) {
		SubscribeRsp subscribeRsp = new SubscribeRsp();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-type", "application/json");
		try {
			StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			String result = EntityUtils.toString(httpEntity, "utf-8");
			if(httpResponse.getStatusLine().getStatusCode() == 200) {
				logger.info("post到" + url + "成功");
				logger.info(jsonString);
				logger.info(result);
			} else {
				logger.error("post到 " + url + "失败");
				logger.error(jsonString);
				logger.error(result);
			}
			subscribeRsp = JSON.parseObject(result, SubscribeRsp.class);
		} catch (Exception e) {
			logger.error("post到 " + url + "错误");
			logger.error(jsonString);
			logger.error(e);
			e.printStackTrace();
		}finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("关闭失败：e.getMessage:{}, e:{}",e.getMessage(),e);
			}
		}
		return subscribeRsp;
	}

	public SubscribeRsp subscribeDevice(String url, Resource resource) {
		SubscribeRsp subscribeRsp = new SubscribeRsp();
		String jsonString = JsonUtil.resourceToJsonString(resource);
		if (!StringUtils.isEmpty(jsonString)) {
			List<JSONObject> jsonObjects = JsonUtil.getDevice(JsonUtil.getSubscribe(jsonString));
			if (!jsonObjects.isEmpty()) {
				logger.info("订阅设备");
				subscribeRsp = subscribeByJSON(url, JsonUtil.afterSend(jsonObjects));
				if (subscribeRsp.getConfirmatStatus().equals("0")) {
					cacheBean.subscribePut(jsonObjects);
				}
			} else {
				logger.info("订阅设备失败(JSON无订阅设备信息)");
				return new SubscribeRsp("-1", "订阅设备失败(JSON无订阅设备信息)");
			}
		} else {
			logger.info("订阅设备失败(订阅JSON空)");
			return new SubscribeRsp("-1", "订阅设备失败(订阅JSON空)");
		}
		return subscribeRsp;
	}

	public SubscribeRsp subscribeTollgate(String url, Resource resource) {
		SubscribeRsp subscribeRsp = new SubscribeRsp();
		String jsonString = JsonUtil.resourceToJsonString(resource);
		if (!StringUtils.isEmpty(jsonString)) {
			List<JSONObject> jsonObjects = JsonUtil.getTollgate(JsonUtil.getSubscribe(jsonString));
			if (!jsonObjects.isEmpty()) {
				logger.info("订阅卡口");
				subscribeRsp = subscribeByJSON(url, JsonUtil.afterSend(jsonObjects));
				if (subscribeRsp.getConfirmatStatus().equals("0")) {
					cacheBean.subscribePut(jsonObjects);
				}
			} else {
				logger.info("订阅卡口失败(JSON无订阅卡口信息)");
				return new SubscribeRsp("-1", "订阅卡口失败(JSON无订阅设备信息)");
			}
		} else {
			logger.info("订阅卡口失败(订阅JSON空)");
			return new SubscribeRsp("-1", "订阅卡口失败(订阅JSON空)");
		}
		return subscribeRsp;
	}

	public SubscribeRsp subscribeMotorVehicle(String url, Resource resource) {
		SubscribeRsp subscribeRsp = new SubscribeRsp();
		String jsonString = JsonUtil.resourceToJsonString(resource);
		if (!StringUtils.isEmpty(jsonString)) {
			List<JSONObject> jsonObjects = JsonUtil.getMotorVehicle(JsonUtil.getSubscribe(jsonString));
			if (!jsonObjects.isEmpty()) {
				logger.info("订阅过车信息");
				subscribeRsp = subscribeByJSON(url, JsonUtil.afterSend(jsonObjects));
				if (subscribeRsp.getConfirmatStatus().equals("0")) {
					cacheBean.subscribePut(jsonObjects);
				}
			} else {
				logger.info("订阅过车信息失败(JSON无订阅过车信息)");
				return new SubscribeRsp("-1", "订阅过车信息失败(JSON无订阅过车信息)");
			}
		} else {
			logger.info("订阅过车信息失败(订阅JSON空)");
			return new SubscribeRsp("-1", "订阅过车信息失败(订阅JSON空)");
		}
		return subscribeRsp;
	}

	public JSONObject subscribeAll(String url, Resource resource) {
		SubscribeRsp tollgateRsp = new SubscribeRsp();
		SubscribeRsp motorVehicleRsp = new SubscribeRsp();
		SubscribeRsp deviceRsp = new SubscribeRsp();
		String jsonString = JsonUtil.resourceToJsonString(resource);
		JSONObject result = new JSONObject();
		if (!StringUtils.isEmpty(jsonString)) {
			tollgateRsp = subscribeTollgate(url, resource);
			motorVehicleRsp = subscribeMotorVehicle(url, resource);
			deviceRsp = subscribeDevice(url, resource);
		} else {
			logger.info("订阅全部失败(订阅JSON空)");
			return JSONObject.parseObject(JSON.toJSONString(new SubscribeRsp("-1", "订阅全部失败(订阅JSON空)")));
		}
		result.put("status", "订阅");
		result.put("tollgateRsp", tollgateRsp);
		result.put("motorVehicleRsp", motorVehicleRsp);
		result.put("deviceRsp", deviceRsp);
		return result;
	}

	public SubscribeRsp cancelDeviceFromFile(String url, Resource resource) {
		SubscribeRsp subscribeRsp = new SubscribeRsp();
		String jsonString = JsonUtil.resourceToJsonString(resource);
		if (!StringUtils.isEmpty(jsonString)) {
			if (!JsonUtil.getDevice(JsonUtil.getSubscribe(jsonString)).isEmpty()) {
				List<JSONObject> jsonObjects = JsonUtil.setCancelFlag(JsonUtil.getDevice(JsonUtil.getSubscribe(jsonString)));
				logger.info("从JSON取消订阅设备");
				subscribeRsp = subscribeByJSON(url, JsonUtil.afterSend(jsonObjects));
				if (subscribeRsp.getConfirmatStatus().equals("0")) {
					cacheBean.subscribeDelete(jsonObjects);
				}
			} else {
				logger.info("取消订阅设备失败(JSON无订阅设备信息)");
				return new SubscribeRsp("-1", "取消订阅设备失败(JSON无订阅设备信息)");
			}
		} else {
			logger.info("取消订阅设备失败(订阅JSON空)");
			return new SubscribeRsp("-1", "取消订阅设备失败(订阅JSON空)");
		}
		return subscribeRsp;
	}

	public SubscribeRsp cancelTollgateFromFile(String url, Resource resource) {
		SubscribeRsp subscribeRsp = new SubscribeRsp();
		String jsonString = JsonUtil.resourceToJsonString(resource);
		if (!StringUtils.isEmpty(jsonString)) {
			if (!JsonUtil.getTollgate(JsonUtil.getSubscribe(jsonString)).isEmpty()) {
				List<JSONObject> jsonObjects = JsonUtil.setCancelFlag(JsonUtil.getTollgate(JsonUtil.getSubscribe(jsonString)));
				logger.info("从JSON取消订阅卡口");
				subscribeRsp = subscribeByJSON(url, JsonUtil.afterSend(jsonObjects));
				if (subscribeRsp.getConfirmatStatus().equals("0")) {
					cacheBean.subscribeDelete(jsonObjects);
				}
			} else {
				logger.info("取消订阅卡口失败(JSON无订阅卡口信息)");
				return new SubscribeRsp("-1", "取消订阅卡口失败(JSON无订阅卡口信息)");
			}
		} else {
			logger.info("取消订阅卡口失败(订阅JSON空)");
			return new SubscribeRsp("-1", "取消订阅卡口失败(订阅JSON空)");
		}
		return subscribeRsp;
	}

	public SubscribeRsp cancelMotorVehicleFromFile(String url, Resource resource) {
		SubscribeRsp subscribeRsp = new SubscribeRsp();
		String jsonString = JsonUtil.resourceToJsonString(resource);
		if (!StringUtils.isEmpty(jsonString)) {
			if (!JsonUtil.getMotorVehicle(JsonUtil.getSubscribe(jsonString)).isEmpty()) {
				List<JSONObject> jsonObjects = JsonUtil.setCancelFlag(JsonUtil.getMotorVehicle(JsonUtil.getSubscribe(jsonString)));
				logger.info("从JSON取消订阅过车信息");
				subscribeRsp = subscribeByJSON(url, JsonUtil.afterSend(jsonObjects));
				if (subscribeRsp.getConfirmatStatus().equals("0")) {
					cacheBean.subscribeDelete(jsonObjects);
				}
			} else {
				logger.info("取消订阅过车信息失败(JSON无订阅过车信息)");
				return new SubscribeRsp("-1", "取消订阅过车信息失败(JSON无订阅过车信息)");
			}
		} else {
			logger.info("取消订阅过车信息失败(订阅JSON空)");
			return new SubscribeRsp("-1", "取消订阅过车信息失败(订阅JSON空)");
		}
		return subscribeRsp;
	}

	public JSONObject cancelAllFromFile(String url, Resource resource) {
		SubscribeRsp tollgateRsp = new SubscribeRsp();
		SubscribeRsp motorVehicleRsp = new SubscribeRsp();
		SubscribeRsp deviceRsp = new SubscribeRsp();
		String jsonString = JsonUtil.resourceToJsonString(resource);
		JSONObject result = new JSONObject();
		if (!StringUtils.isEmpty(jsonString)) {
			tollgateRsp = cancelTollgateFromFile(url, resource);
			motorVehicleRsp = cancelMotorVehicleFromFile(url, resource);
			deviceRsp = cancelDeviceFromFile(url, resource);
		} else {
			logger.info("订阅JSON空");
			return JSONObject.parseObject(JSON.toJSONString(new SubscribeRsp("-1", "取消订阅全部失败(订阅JSON空)")));
		}
		result.put("status", "从JSON取消订阅");
		result.put("tollgateRsp", tollgateRsp);
		result.put("motorVehicleRsp", motorVehicleRsp);
		result.put("deviceRsp", deviceRsp);
		return result;
	}

	public SubscribeRsp cancelDeviceFromCache(String url) {
		SubscribeRsp subscribeRsp = new SubscribeRsp();
		List<JSONObject> jsonObjects = JsonUtil.getDevice(cacheBean.getAll());
		if(!jsonObjects.isEmpty()) {
			logger.info("从缓存取消订阅设备");
			subscribeRsp = subscribeByJSON(url, JsonUtil.afterSend(JsonUtil.setCancelFlag(jsonObjects)));
			if (subscribeRsp.getConfirmatStatus().equals("0")) {
				cacheBean.subscribeDelete(jsonObjects);
			}
		}else {
			logger.info("取消订阅设备失败(缓存中无订阅设备信息)");
			return new SubscribeRsp("-1", "取消订阅设备失败(缓存中无订阅设备信息)");
		}
		return subscribeRsp;
	}

	public SubscribeRsp cancelTollgateFromCache(String url) {
		SubscribeRsp subscribeRsp = new SubscribeRsp();
		List<JSONObject> jsonObjects = JsonUtil.getTollgate(cacheBean.getAll());
		if(!jsonObjects.isEmpty()) {
			logger.info("从缓存取消订阅卡口");
			subscribeRsp = subscribeByJSON(url, JsonUtil.afterSend(JsonUtil.setCancelFlag(jsonObjects)));
			if (subscribeRsp.getConfirmatStatus().equals("0")) {
				cacheBean.subscribeDelete(jsonObjects);
			}
		}else {
			logger.info("取消订阅卡口失败(缓存中无订阅卡口信息)");
			return new SubscribeRsp("-1", "取消订阅卡口失败(缓存中无订阅卡口信息)");
		}
		return subscribeRsp;
	}

	public SubscribeRsp cancelMotorVehicleFromCache(String url) {
		SubscribeRsp subscribeRsp = new SubscribeRsp();
		List<JSONObject> jsonObjects = JsonUtil.getMotorVehicle(cacheBean.getAll());
		if(!jsonObjects.isEmpty()) {
			logger.info("从缓存取消订阅过车信息");
			subscribeRsp = subscribeByJSON(url, JsonUtil.afterSend(JsonUtil.setCancelFlag(jsonObjects)));
			if (subscribeRsp.getConfirmatStatus().equals("0")) {
				cacheBean.subscribeDelete(jsonObjects);
			}
		}else {
			logger.info("取消订阅过车信息失败(缓存中无订阅过车信息)");
			return new SubscribeRsp("-1", "取消订阅过车信息失败(缓存中无订阅过车信息)");
		}
		return subscribeRsp;
	}

	public JSONObject cancelAllFromCache(String url) {
		SubscribeRsp tollgateRsp = new SubscribeRsp();
		SubscribeRsp motorVehicleRsp = new SubscribeRsp();
		SubscribeRsp deviceRsp = new SubscribeRsp();
		JSONObject result = new JSONObject();
		List<JSONObject> jsonObjects = cacheBean.getAll();
		if (!jsonObjects.isEmpty()) {
			tollgateRsp = cancelTollgateFromCache(url);
			motorVehicleRsp = cancelMotorVehicleFromCache(url);
			deviceRsp = cancelDeviceFromCache(url);
		}
		result.put("status", "从缓存取消订阅");
		result.put("tollgateRsp", tollgateRsp);
		result.put("motorVehicleRsp", motorVehicleRsp);
		result.put("deviceRsp", deviceRsp);
		return result;
	}

	/**
	 * 根据UpSubscribeId删除订阅
	 * @param url
	 * @param upSubscribeId
	 * @return
	 */
	public SubscribeRsp deleteByUpSubscribeId(String url, String upSubscribeId) {
		SubscribeRsp subscribeRsp = new SubscribeRsp();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(url+"/id?upSubscribeId="+upSubscribeId);
		try {
			HttpResponse httpResponse = httpClient.execute(httpDelete);
			HttpEntity httpEntity = httpResponse.getEntity();
			String result = EntityUtils.toString(httpEntity, "utf-8");
			if(httpResponse.getStatusLine().getStatusCode() == 0) {
				logger.info("delete 成功 " + url + " upSubscribeId " + upSubscribeId);
				logger.info(result);
			} else {
				logger.error("delete 失败 " + url + " upSubscribeId " + upSubscribeId);
				logger.error(result);
			}
			subscribeRsp = JSON.parseObject(result, SubscribeRsp.class);
		} catch (Exception e) {
			logger.error("deletes 失败 " + url + " upSubscribeId " + upSubscribeId);
			logger.error(e);
			e.printStackTrace();
		}
		return subscribeRsp;
	}

	/**
	 * 注册
	 */
	public void register() {
		JSONObject register = new JSONObject();
		JSONObject registerObject = new JSONObject();
		registerObject.put("DeviceID", customProperties.getDeviceId());
		register.put("RegisterObject", registerObject);
		HttpUtil.postToVIID(customProperties.getRegisterUrl(), JSON.toJSONString(register), customProperties.getYhkkDigestUsername(), customProperties.getYhkkDigestPasswd());
	}

	/**
	 * 保活
	 */
	public void keepAlive() {
		JSONObject keepAlive = new JSONObject();
		JSONObject keepAliveObject = new JSONObject();
		keepAliveObject.put("DeviceID", customProperties.getDeviceId());
		keepAlive.put("KeepaliveObject", keepAliveObject);
		int time = 0;
		while (true) {
			time++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (time == customProperties.getYhkkKeepAlive()) {
				HttpUtil.postToVIID(customProperties.getKeepAliveUrl(), JSON.toJSONString(keepAlive),  customProperties.getYhkkDigestUsername(), customProperties.getYhkkDigestPasswd());
				time = 0;
			}
		}
	}

//	/**
//	 * 取消订阅
//	 * @param jsonString
//	 * @return
//	 */
//	public SubscribeRsp cancelSubscribe(String url, String jsonString) {
//		JSONObject subscribe = JSON.parseObject(jsonString);
//		subscribe.put("cancelFlag", "1");
//		logger.info("取消订阅");
//		return subscribeByJSON(url, JsonUtil.subscribeToJsonString(subscribe.toJSONString()));
//	}
//
//	/**
//	 * 获取订阅类别
//	 * @return
//	 */
//	public String getSubscribeCategory(String subscribe) {
//		String test = JSON.parseObject(subscribe).getString("SubscribeCategory");
//		return test;
//	}
//
//	/**
//	 * 订阅过车记录并存缓存
//	 * @param url
//	 * @param jsonResource
//	 * @return
//	 */
//	public void subscribeMotorVehicle(String url, Resource jsonResource) {
//		String jsonString = JsonUtil.resourceToJsonString(jsonResource);
//		if (!StringUtils.isEmpty(jsonString)) {
//			String motorVehicle = JsonUtil.getMotorVehicle(jsonString);
//			JSONArray jsonArray = JsonUtil.getJOSNArray(motorVehicle);
//			boolean flag = true;
//			for(int i = 0; i < jsonArray.size(); i++) {
//				String subscribe = JSON.toJSONString(jsonArray.get(i));
//				if (!getSubscribeCategory(subscribe).equals("3")) {
//					logger.error("订阅过车记录失败,订阅过车记录状态码错误");
//					flag = false;
//				} else {
//					flag &= true;
//				}
//			}
//			if (flag) {
//				logger.info("订阅过车记录");
////				subscribeByJSON(url, motorVehicle);
//				cacheBean.subscribePut(JsonUtil.getListFromJson(motorVehicle));
//			}
//		}
//	}
//
//	/**
//	 * 订阅卡口并存缓存
//	 * @param url
//	 * @param jsonResource
//	 * @return
//	 */
//	public void subscribeTollgate(String url, Resource jsonResource) {
//		String jsonString = JsonUtil.resourceToJsonString(jsonResource);
//		if (!StringUtils.isEmpty(jsonString)) {
//			String tollgate = JsonUtil.getTollgate(jsonString);
//			JSONArray jsonArray = JsonUtil.getJOSNArray(tollgate);
//			boolean flag = true;
//			for(int i = 0; i < jsonArray.size(); i++) {
//				String subscribe = JSON.toJSONString(jsonArray.get(i));
//				if (!getSubscribeCategory(subscribe).equals("5")) {
//					logger.error("订阅卡口状态码错误");
//					flag = false;
//				} else {
//					flag &= true;
//				}
//			}
//			if (flag) {
//				logger.info("订阅卡口");
////				subscribeByJSON(url, tollgate);
//				cacheBean.subscribePut(JsonUtil.getListFromJson(tollgate));
//			}
//		} else {
//			logger.info("订阅卡口失败,订阅JSON为空");
//		}
//
//	}
//
//	/**
//	 * 订阅设备并存缓存
//	 * @param url
//	 * @param jsonResource
//	 * @return
//	 */
//	public void subscribeDevice(String url, Resource jsonResource) {
//		String jsonString = JsonUtil.resourceToJsonString(jsonResource);
//		if (!StringUtils.isEmpty(jsonString)) {
//			String device = JsonUtil.getDevice(jsonString);
//			JSONArray jsonArray = JsonUtil.getJOSNArray(device);
//			boolean flag = true;
//			for(int i = 0; i < jsonArray.size(); i++) {
//				String subscribe = JSON.toJSONString(jsonArray.get(i));
//				if (!getSubscribeCategory(subscribe).equals("2")) {
//					logger.error("订阅设备状态码错误");
//					flag = false;
//				} else {
//					flag &= true;
//				}
//			}
//			if(flag) {
//				logger.info("订阅设备");
////				subscribeByJSON(url, device);
//				cacheBean.subscribePut(JsonUtil.getListFromJson(device));
//			}
//		} else {
//			logger.info("订阅设备失败,订阅JSON为空");
//		}
//	}
//
//	/**
//	 * 订阅全部并存缓存
//	 */
//	public void subscribeAll(String url, Resource jsonResource) {
//		String jsonString = JsonUtil.resourceToJsonString(jsonResource);
//		if (!StringUtils.isEmpty(jsonString)) {
//			subscribeMotorVehicle(url, jsonResource);
//			subscribeDevice(url, jsonResource);
//			subscribeTollgate(url, jsonResource);
//		} else {
//			logger.info("订阅全部失败,订阅JSON为空");
//		}
//	}
//
//	public void cancelAll(List<String> jsonList) {
//		logger.info("取消订阅全部");
//		for (String jsonString : jsonList) {
//			JSONObject subscribeList = JSON.parseObject(jsonString).getJSONObject("SubscribeList");
//			System.out.println(jsonString);
//			JSONArray subscribe = subscribeList.getJSONArray("Subscribe");
//			for (int i = 0; i < subscribe.size() ; i++) {
//				JSONObject jsonObject = subscribe.getJSONObject(i);
//				jsonObject.put("CancelFlag", 1);
//			}
//			System.out.println(jsonString);
//		}
//	}

//	/**
//	 * 订阅过车记录
//	 * @param url
//	 * @param jsonString
//	 * @return
//	 */
//	public SubscribeRsp subscribeMotorVehicle(String url, String jsonString) {
//		JSONObject jsonObject = JSON.parseObject(jsonString);
//		JSONObject motorVehicleList = jsonObject.getJSONObject("SubscribeList");
//		JSONArray motorVehicleArray = motorVehicleList.getJSONArray("Subscribe");
//		if (motorVehicleArray.size() == 0) {
//			logger.info("JSON没有需要订阅的过车记录");
//			return new SubscribeRsp();
//		}
//		String motorVehicleString = JSON.toJSONString(jsonObject);
//		logger.info("订阅过车记录 ");
//		return subscribeByJSON(motorVehicleString, url);
//	}
//
//	/**
//	 * 订阅卡口
//	 * @param url
//	 * @param jsonString
//	 * @return
//	 */
//	public SubscribeRsp subscribeTollGate(String url, String jsonString) {
//		JSONObject jsonObject = JSON.parseObject(jsonString);
//		JSONObject tollgateList = jsonObject.getJSONObject("SubscribeList");
//		JSONArray tollgateArray = tollgateList.getJSONArray("Subscribe");
//		if (tollgateArray.size() == 0) {
//			logger.info("JSON没有需要订阅的卡口");
//			return new SubscribeRsp();
//		}
//		String motorVehicleString = JSON.toJSONString(jsonObject);
//		logger.info("订阅卡口 ");
//		return subscribeByJSON(motorVehicleString, url);
//	}
//
//	/**
//	 * 订阅设备
//	 * @param url
//	 * @param jsonString
//	 * @return
//	 */
//	public SubscribeRsp subscribeDevice(String url, String jsonString) {
//		JSONObject jsonObject = JSON.parseObject(jsonString);
//		JSONObject devicesList = jsonObject.getJSONObject("SubscribeList");
//		JSONArray devicesArray = devicesList.getJSONArray("Subscribe");
//		if (devicesArray.size() == 0) {
//			logger.info("JSON没有需要订阅的设备");
//			return new SubscribeRsp();
//		}
//		String motorVehicleString = JSON.toJSONString(jsonObject);
//		logger.info("订阅设备 ");
//		//System.out.println(motorVehicleString);
//		return subscribeByJSON(motorVehicleString, url);
//	}
//
//	/**
//	 * 订阅全部(设备、卡口、过车记录)
//	 * @param url
//	 * @param jsonString
//	 * @return
//	 */
//	public SubscribeRsp subscribeAll(String url, String jsonString) {
//		JSONArray all = new JSONArray();
//		JSONObject jsonObject = JSON.parseObject(jsonString);
//		JSONObject subscribeList = jsonObject.getJSONObject("SubscribeList");
//		JSONArray subscribe = subscribeList.getJSONArray("Subscribe");
//		if (subscribe.size() == 0) {
//			logger.info("JSON没有需要订阅的设备");
//			return new SubscribeRsp();
//		}
//
//		logger.info("订阅全部 " + url);
//		return subscribeByJSON(jsonObject.toJSONString(), url);
//	}



//	/**
//	 * 筛选JSON中的过车信息订阅
//	 * @return
//	 */
//	public String getMotorVehicleSubscribe(String jsonString) {
//		JSONObject jsonObject = JSON.parseObject(jsonString);
//		JSONObject motorVehicle = jsonObject.getJSONObject("MotorVehicle");
//		JSONObject motorVehicleList = motorVehicle.getJSONObject("SubscribeList");
//		JSONArray motorVehicleArray = motorVehicleList.getJSONArray("Subscribe");
//		JSONArray all = new JSONArray();
//
//		for(int i = 0; i < motorVehicleArray.size(); i++) {
//			if (Integer.parseInt(String.valueOf(motorVehicleArray.getJSONObject(i).get("cancelFlag"))) == 0) {
//				all.add(motorVehicleArray.getJSONObject(i));
//			}
//		}
//
//		JSONObject subscribe = new JSONObject();
//		subscribe.put("Subscribe", all);
//		JSONObject subscribeList = new JSONObject();
//		subscribeList.put("SubscribeList", subscribe);
//		return subscribeList.toJSONString();
//	}
//
//	/**
//	 * 筛选JSON中的设备订阅
//	 * @return
//	 */
//	public String getDeviceSubscribe(String jsonString) {
//		JSONObject jsonObject = JSON.parseObject(jsonString);
//		JSONObject device = jsonObject.getJSONObject("Device");
//		JSONObject devicesList = device.getJSONObject("SubscribeList");
//		JSONArray devicesArray = devicesList.getJSONArray("Subscribe");
//		JSONArray all = new JSONArray();
//
//		for(int i = 0; i < devicesArray.size(); i++) {
//			if (Integer.parseInt(String.valueOf(devicesArray.getJSONObject(i).get("cancelFlag"))) == 0) {
//				all.add(devicesArray.getJSONObject(i));
//			}
//		}
//
//		JSONObject subscribe = new JSONObject();
//		subscribe.put("Subscribe", all);
//		JSONObject subscribeList = new JSONObject();
//		subscribeList.put("SubscribeList", subscribe);
//		return subscribeList.toJSONString();
//	}
//
//	/**
//	 * 筛选JSON中的卡口订阅
//	 * @return
//	 */
//	public String getTollgateSubscribe(String jsonString) {
//		JSONObject jsonObject = JSON.parseObject(jsonString);
//		JSONObject tollgate = jsonObject.getJSONObject("Tollgate");
//		JSONObject tollgateList = tollgate.getJSONObject("SubscribeList");
//		JSONArray tollgateArray = tollgateList.getJSONArray("Subscribe");
//		JSONArray all = new JSONArray();
//
//		for(int i = 0; i < tollgateArray.size(); i++) {
//			if (Integer.parseInt(String.valueOf(tollgateArray.getJSONObject(i).get("cancelFlag"))) == 0) {
//				all.add(tollgateArray.getJSONObject(i));
//			}
//		}
//
//		JSONObject subscribe = new JSONObject();
//		subscribe.put("Subscribe", all);
//		JSONObject subscribeList = new JSONObject();
//		subscribeList.put("SubscribeList", subscribe);
//		return subscribeList.toJSONString();
//	}
//
//	/**
//	 * 筛选JSON中所有订阅
//	 * @return
//	 */
//	public String getAllSubscribe(String jsonString) {
//		JSONObject jsonObject = JSON.parseObject(jsonString);
//		JSONObject motorVehicle = jsonObject.getJSONObject("MotorVehicle");
//		JSONObject device = jsonObject.getJSONObject("Device");
//		JSONObject tollgate = jsonObject.getJSONObject("Tollgate");
//
//		JSONObject motorVehicleList = motorVehicle.getJSONObject("SubscribeList");
//		JSONArray motorVehicleArray = motorVehicleList.getJSONArray("Subscribe");
//		JSONObject devicesList = device.getJSONObject("SubscribeList");
//		JSONArray devicesArray = devicesList.getJSONArray("Subscribe");
//		JSONObject tollgateList = tollgate.getJSONObject("SubscribeList");
//		JSONArray tollgateArray = tollgateList.getJSONArray("Subscribe");
//		JSONArray all = new JSONArray();
//
//		for(int i = 0; i < tollgateArray.size(); i++) {
//			if (Integer.parseInt(String.valueOf(tollgateArray.getJSONObject(i).get("cancelFlag"))) == 0) {
//				all.add(tollgateArray.getJSONObject(i));
//			}
//		}
//		for(int i = 0; i < devicesArray.size(); i++) {
//			if (Integer.parseInt(String.valueOf(devicesArray.getJSONObject(i).get("cancelFlag"))) == 0) {
//				all.add(devicesArray.getJSONObject(i));
//			}
//		}
//		for(int i = 0; i < motorVehicleArray.size(); i++) {
//			if (Integer.parseInt(String.valueOf(motorVehicleArray.getJSONObject(i).get("cancelFlag"))) == 0) {
//				all.add(motorVehicleArray.getJSONObject(i));
//			}
//		}
//		JSONObject subscribe = new JSONObject();
//		subscribe.put("Subscribe", all);
//		JSONObject subscribeList = new JSONObject();
//		subscribeList.put("SubscribeList", subscribe);
//		return subscribeList.toJSONString();
//	}





}
