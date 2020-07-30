package com.zxelec.yhkk.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.zxelec.yhkk.entity.CamPushEntity;
import com.zxelec.yhkk.entity.TollgatePushEntity;

@Component
public class BasicsCache {

	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private List<CamPushEntity> putCamPushEntityList = new ArrayList<>();
	private List<TollgatePushEntity> putTollgatePushEntityList = new ArrayList<>();

	/**
	 * 将卡口信息写入缓存
	 * @param tollgatePushEntityList
	 */
	public void putTollgatePushEntityList(List<TollgatePushEntity> tollgatePushEntityList) {
		Lock writeLock = readWriteLock.writeLock();
		try {
			writeLock.lock();
			putTollgatePushEntityList.clear();
			putTollgatePushEntityList.addAll(tollgatePushEntityList);
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * 得到缓存中的数据
	 * @return List<TollgatePushEntity>
	 */
	public List<TollgatePushEntity> getTollgatePushEntityList() {
		Lock readLock = readWriteLock.readLock();
		try {
			readLock.lock();
			return putTollgatePushEntityList;
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * 将List<TollgatePushEntity>转为List<Map<String, TollgatePushEntity>>
	 * @return List<Map<String, TollgatePushEntity>>
	 */
	public List<Map<String, TollgatePushEntity>> getMapTollgatePushEntityList() {
		Lock readLock = readWriteLock.readLock();
		List<Map<String, TollgatePushEntity>> mapTollgatePushEntityList = new ArrayList<>();
		try {
			readLock.lock();
			for (TollgatePushEntity tollgatePushEntity : putTollgatePushEntityList) {
				Map<String, TollgatePushEntity> tollgatepushEntityMap = new HashMap<>();
				tollgatepushEntityMap.put(tollgatePushEntity.getSubscribeID(), tollgatePushEntity);
				mapTollgatePushEntityList.add(tollgatepushEntityMap);
			}
			return mapTollgatePushEntityList;
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * 将设备信息写入缓存
	 * @param camPushEntityList
	 */
	public void putCamPushEntityList(List<CamPushEntity> camPushEntityList){
		Lock writeLock = readWriteLock.writeLock();
		try{
			writeLock.lock();
			putCamPushEntityList.clear();
			putCamPushEntityList.addAll(camPushEntityList);
		}finally {
			writeLock.unlock();
		}
	}

	/**
	 * 得到缓存中的数据
	 * @return List<CamPushEntity>
	 */
	public List<CamPushEntity> getCamPushEntityList(){
		Lock readLock = readWriteLock.readLock();
		try {
			readLock.lock();
			return putCamPushEntityList;
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * 将List<CamPushEntity>转成List<Map<String,CamPushEntity>>
	 * @return List<Map<String,CamPushEntity>>
	 */
	public List<Map<String,CamPushEntity>> getMapCamPushEntityList(){
		Lock readLock = readWriteLock.readLock();
		List<Map<String, CamPushEntity>> mapCamPushEntityList = new ArrayList<>();
		try {
			readLock.lock();
			for (CamPushEntity camPushEntity : putCamPushEntityList) {
				Map<String, CamPushEntity> camPushEntityMap = new HashMap<>();
				camPushEntityMap.put(camPushEntity.getSubscribeID(), camPushEntity);
				mapCamPushEntityList.add(camPushEntityMap);
			}
			return mapCamPushEntityList;
		} finally {
			readLock.unlock();
		}
	}
}
