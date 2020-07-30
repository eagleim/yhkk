package com.zxelec.yhkk.service;

import java.util.*;
import java.util.stream.Collectors;

import com.zxelec.yhkk.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zxelec.yhkk.cache.BasicsCache;
import com.zxelec.yhkk.entity.rest.VcAPEListObject;
import com.zxelec.yhkk.entity.rest.VcAPEObject;
import com.zxelec.yhkk.entity.rest.VcAPERsp;
import com.zxelec.yhkk.entity.rest.VcTollgateListObject;
import com.zxelec.yhkk.entity.rest.VcTollgateObject;
import com.zxelec.yhkk.entity.rest.VcTollgateRsp;
import com.zxelec.yhkk.utils.JsonUtils;

@Service
@EnableScheduling
public class ReceiveService {

	@Autowired
	private BasicsCache basicsCache;

	@Autowired
	private CarService carService;

	@Autowired
	private Queue vehiclePackageQueue;

	private Logger logger = LogManager.getLogger(ReceiveService.class);

	/**
	 * 将接收到的数据写进内存队列
	 * @param list
	 */
	public void InQueue(List<Map<String, ?>> list){
		this.vehiclePackageQueue.offer(list);
	}

	@Scheduled(cron = "${yhkk.outqueue.cron}")
	private void OutQueue(){
		//List<Map<String, ?>> list = this.vehiclePackageQueue.poll();
		//writerJsonFile(list);
	}


	/**
	 * 将接收到的数据写进Json文件里
	 * @param list
	 */
	public void writerJsonFile(List<Map<String, ?>> list) {
		List<Map<String,CamPushEntity>> cacheMapCamPushEntityList = basicsCache.getMapCamPushEntityList();
        Map<String,CamPushEntity> camPushEntityMap = new HashMap<>();
		List<Map<String,TollgatePushEntity>> cacheMapTollgatePushEntityList = basicsCache.getMapTollgatePushEntityList();
        Map<String, TollgatePushEntity> tollgatePushEntityMap = new HashMap<>();
        for (Map<String, ?> map : list) {
			if (map.containsKey("DeviceList")) {// 设备数据推送
				CamPushEntity camEntity = JSONObject.parseObject(JSONObject.toJSONString(map), CamPushEntity.class);
				Map<String, DeviceList> allDeviceMap = new HashMap<>();
				for(Map<String,CamPushEntity> cacheCamMap : cacheMapCamPushEntityList){
					if(cacheCamMap.containsKey(camEntity.getSubscribeID())){
						cacheCamMap.get(camEntity.getSubscribeID()).getDeviceList().stream().forEach(c -> {
							allDeviceMap.put(c.getDeviceID(), c);
						});
						camEntity.getDeviceList().forEach(e -> {
							allDeviceMap.put(e.getDeviceID(), e);
						});
					}
					camEntity.getDeviceList().clear();
				}
				for(DeviceList camList : allDeviceMap.values()){
					camEntity.getDeviceList().add(camList);
				}
				camPushEntityMap.put(camEntity.getSubscribeID(),camEntity);
				if(cacheMapCamPushEntityList.size()==0){
					cacheMapCamPushEntityList.add(camPushEntityMap);
				}
			} else if (map.containsKey("TollgateList")) {
				TollgatePushEntity tollgateEntity = JSONObject.parseObject(JSONObject.toJSONString(map),TollgatePushEntity.class);
				Map<String, TollgateList> allTollgateMap = new HashMap<>();
				for (Map<String, TollgatePushEntity> cacheTollgateMap : cacheMapTollgatePushEntityList) {
                        if(cacheTollgateMap.containsKey(tollgateEntity.getSubscribeID())) {
                            cacheTollgateMap.get(tollgateEntity.getSubscribeID()).getTollgateList().stream().forEach(c -> {
                                allTollgateMap.put(c.getTollgateID(), c);
                            });
                            tollgateEntity.getTollgateList().forEach(e -> {
                                allTollgateMap.put(e.getTollgateID(), e);
                            });
                        }
					tollgateEntity.getTollgateList().clear();
				}
				for(TollgateList tollgateList : allTollgateMap.values()){
						tollgateEntity.getTollgateList().add(tollgateList);
				}
				tollgatePushEntityMap.put(tollgateEntity.getSubscribeID(),tollgateEntity);
				if (cacheMapTollgatePushEntityList.size()==0){
					cacheMapTollgatePushEntityList.add(tollgatePushEntityMap);
				}
			} else if (map.containsKey("MotorVehicleObjectList")) {
				CarPushReq carEntity = JSONObject.parseObject(JSONObject.toJSONString(map), CarPushReq.class);
				logger.fatal(JSONObject.toJSONString(map));
				logger.info("过车记录开始传出，一共有"+carEntity.getMotorVehicleObjectList().size()+"条");
				carService.receiveMotionVehicleData(carEntity);// 调用其他人方法发送给kafka
				logger.info("过车记录传出完成");
			}
		}
		if (camPushEntityMap.size() > 0 ) {
			List<CamPushEntity> camList = camPushEntityMap.values().stream().collect(Collectors.toList());
			JsonUtils.writeReceiveJson("DeviceList", camList);
			logger.info("设备信息写入文件");
			basicsCache.putCamPushEntityList(camList);
		}
		if (tollgatePushEntityMap.size() > 0) {
            List<TollgatePushEntity> tollgateList  = tollgatePushEntityMap.values().stream().collect(Collectors.toList());
			JsonUtils.writeReceiveJson("TollgateList", tollgateList);
			logger.info("卡口信息写入文件");
			basicsCache.putTollgatePushEntityList(tollgateList);

		}
	}

	/**
	 * 读出Json文件中的设备信息
	 * @return List<CamPushEntity>
	 */
	public VcAPERsp  readDevicesJsonFile() {
		List<CamPushEntity> getCamPushEntityList = basicsCache.getCamPushEntityList();
		VcAPEListObject vcAPEListObject = new VcAPEListObject();
		VcAPERsp vcAPERsp = new VcAPERsp();
		List<VcAPEObject> vcAPEObjectlist = new ArrayList<VcAPEObject>();
		if(getCamPushEntityList.size()>0) {
		    for(CamPushEntity camPushEntity : getCamPushEntityList) {
                if (camPushEntity != null) {
                    List<DeviceList> deviceList = camPushEntity.getDeviceList();
                    if (deviceList.size() > 0) {
                        for (DeviceList device : deviceList) {
                            VcAPEObject vcAPEObject = new VcAPEObject();
                            vcAPEObject.setApeID(device.getDeviceID());
                            vcAPEObject.setName(device.getName());
                            vcAPEObject.setModel(device.getModel());
                            vcAPEObject.setiPAddr(device.getiPAddr());
                            vcAPEObject.setiPV6Addr("0.0.0.0");
                            vcAPEObject.setPort(1234);
                            vcAPEObject.setLongitude(device.getLongtidude());
                            vcAPEObject.setLatitude(device.getLatitude());
                            vcAPEObject.setPlaceCode(device.getPlaceCode());
                            vcAPEObject.setPlace(device.getPlace());
                            vcAPEObject.setOrgCode(device.getOrgCode());
                            vcAPEObject.setCapDirection(device.getCapdirection());
                            vcAPEObject.setMonitorDirection("无");
                            vcAPEObject.setMonitorAreaDesc("无");
                            vcAPEObject.setIsOnline("无");
                            vcAPEObject.setOwnerApsID(device.getDeviceID());
                            vcAPEObject.setUserId("无");
                            vcAPEObject.setPassword("无");
                            vcAPEObjectlist.add(vcAPEObject);
                        }
                    }
                }
            }
			vcAPEListObject.setApeObject(vcAPEObjectlist);
			vcAPERsp.setVcApeListObject(vcAPEListObject);
		}else {
			logger.info("缓存中设备信息为空");
		}
		return vcAPERsp;
	}

	/**
	 * 将接收到的List<TollgatePushEntity>转成VcTollgateRsp对象
	 * @return VcTollgateRsp
	 */
	public VcTollgateRsp readTollgateJsonFile() {
        List<TollgatePushEntity> getTollgatePushEntityList = basicsCache.getTollgatePushEntityList();
		VcTollgateRsp vcTollgateRsp = new VcTollgateRsp();
		VcTollgateListObject vcTollgateListObject = new VcTollgateListObject();
		List<VcTollgateObject> vcTollgateObjectlist = new ArrayList<VcTollgateObject>();
		if(getTollgatePushEntityList.size()>0) {
		    for (TollgatePushEntity tollgatePushEntity : getTollgatePushEntityList) {
                if (tollgatePushEntity != null) {
                    List<TollgateList> tollgateList = tollgatePushEntity.getTollgateList();
                    if (tollgateList.size() > 0) {
                        for (TollgateList tollgate : tollgateList) {
                            VcTollgateObject vcTollgateObject = new VcTollgateObject();
                            vcTollgateObject.setTollgateId(tollgate.getTollgateID());
                            vcTollgateObject.setName(tollgate.getName());
                            vcTollgateObject.setLongitude(tollgate.getLongtidude());
                            vcTollgateObject.setLatitude(tollgate.getLatitude());
                            vcTollgateObject.setPlaceCode(tollgate.getPlaceCode());
                            vcTollgateObject.setPlace(tollgate.getPlace());
                            vcTollgateObject.setStatus(tollgate.getStatus());
                            vcTollgateObject.setTollgateCat("99");
                            vcTollgateObject.setTollgateUsage(tollgate.getTollgateType());
                            vcTollgateObject.setLaneNum(tollgate.getLaneNum());
                            vcTollgateObject.setOrgCode(tollgate.getOrgCode());
                            vcTollgateObject.setActiveTime(tollgate.getActiveTime());
                            vcTollgateObjectlist.add(vcTollgateObject);
                        }
                    }
                }
            }
                 vcTollgateListObject.setTollgateObject(vcTollgateObjectlist);
		         vcTollgateRsp.setTollgateListObject(vcTollgateListObject);
			}else {
				logger.info("Json文件内卡口信息为空");
			}
			return vcTollgateRsp;
		}
	}
