package com.zxelec.yhkk.controller;
/**
 *  手动触发订阅信息
 * @author liu.yongquan
 */

import com.alibaba.fastjson.JSONObject;
import com.zxelec.yhkk.bean.CacheBean;
import com.zxelec.yhkk.entity.SubscribeRsp;
import com.zxelec.yhkk.service.SubscribeService;
import com.zxelec.yhkk.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscribeController {


    @Autowired
    private CacheBean cacheBean;

    @Autowired
    private SubscribeService subscribeService;

    @Value("${subscribe.serverUrl}")
    private String url;

    @Value("classpath:subscribe.json")
    private Resource jsonResource;

    @RequestMapping("/subscribeAll")
    public JSONObject subscribeAll() {
        return subscribeService.subscribeAll(url, jsonResource);
    }

    @RequestMapping("/subscribeMotorVehicle")
    public SubscribeRsp subscribeMotorVehicle() {
        return subscribeService.subscribeMotorVehicle(url, jsonResource);
    }

    @RequestMapping("/subscribeDevice")
    public SubscribeRsp subscribeDevice() {
        return subscribeService.subscribeDevice(url, jsonResource);
    }

    @RequestMapping("/subscribeTollgate")
    public SubscribeRsp subscribeTollgate() {
        return subscribeService.subscribeTollgate(url, jsonResource);
    }

    @RequestMapping("/delete/{id}")
    public void deleteSubscribe(@PathVariable(required = false) Integer id) {
        subscribeService.deleteByUpSubscribeId(url, String.valueOf(id));
    }

    @RequestMapping("/subscribe")
    public SubscribeRsp subscribe(@RequestBody String jsonString) {
        return subscribeService.subscribeByJSON(url, jsonString);
    }

    @RequestMapping("/getSubscribe")
    public JSONObject getAll() {
        return JSONObject.parseObject(JsonUtil.afterSend(cacheBean.getAll()));
    }

    @RequestMapping("/cancelDevice")
    public SubscribeRsp cancelDevice() {
        return subscribeService.cancelDeviceFromFile(url, jsonResource);
    }

    @RequestMapping("/cancelTollgate")
    public SubscribeRsp cancelTollgate() {
        return subscribeService.cancelTollgateFromFile(url, jsonResource);
    }

    @RequestMapping("/cancelMotorVehicle")
    public SubscribeRsp cancelMotorVehicle() {
        return subscribeService.cancelMotorVehicleFromFile(url, jsonResource);
    }

    @RequestMapping("/cancelAll")
    public JSONObject cancelAll() {
        return subscribeService.cancelAllFromFile(url, jsonResource);
    }
    @RequestMapping("/cache/cancelDevice")
    public SubscribeRsp cancelDeviceFromCache() {
        return subscribeService.cancelDeviceFromCache(url);
    }

    @RequestMapping("/cache/cancelTollgate")
    public SubscribeRsp cancelTollgateFromCache() {
        return subscribeService.cancelTollgateFromCache(url);
    }

    @RequestMapping("/cache/cancelMotorVehicle")
    public SubscribeRsp cancelMotorVehicleFromCache() {
        return subscribeService.cancelMotorVehicleFromCache(url);
    }

    @RequestMapping("/cache/cancelAll")
    public JSONObject cancelAllFromCache() {
        return subscribeService.cancelAllFromCache(url);
    }

}
