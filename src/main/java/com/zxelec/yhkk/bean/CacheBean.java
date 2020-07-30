package com.zxelec.yhkk.bean;

import com.alibaba.fastjson.JSONObject;
import com.zxelec.yhkk.utils.JsonUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 订阅的缓存
 */
@Component
public class CacheBean {

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Map<String, JSONObject> subscribeMap= new ConcurrentHashMap<>();

    public void subscribePut(List<JSONObject> subscribeList) {
        Lock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            for (JSONObject subscribe : subscribeList) {
                subscribeMap.put(subscribe.getString("SubscribeID"), subscribe);
            }
        }finally {
           writeLock.unlock();
        }
    }

    public void subscribeDelete(List<JSONObject> subscribeList) {
        Lock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            for (JSONObject subscribe : subscribeList) {
                subscribeMap.remove(subscribe.getString("SubscribeID"));
            }
        }finally {
            writeLock.unlock();
        }
    }

    public void deleteAll() {
        Lock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            subscribeMap.clear();
        }finally {
            writeLock.unlock();
        }
    }

    public List<JSONObject> getAll() {
        Lock readLock = readWriteLock.readLock();
        readLock.lock();
        try {
            Collection<JSONObject> collection = subscribeMap.values();
            List<JSONObject> list = new ArrayList<>(collection);
            return list;
        }finally {
            readLock.unlock();
        }
    }


//    public JSONObject subscribeGet(String key) {
//        Lock readLock = readWriteLock.readLock();
//        readLock.lock();
//        try {
//            return subscribeMap.get(key);
//        }finally {
//            readLock.unlock();
//        }
//    }
//
//    public void subscribeDelete(String key) {
//        Lock writeLock = readWriteLock.writeLock();
//        writeLock.lock();
//        try {
//            subscribeMap.remove(key);
//        }finally {
//            writeLock.unlock();
//        }
//    }

}
