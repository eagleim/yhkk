package com.zxelec.yhkk.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 订阅用到的JSON处理类
 */
public class JsonUtil {

    /**
     * 文件转JSONString
     * @param jsonFile
     * @return
     */
    public static String fileToJsonString(File jsonFile) {
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(jsonFile, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }

    /**
     * Resource转JSONString
     * @param resource
     * @return
     */
    public static String resourceToJsonString(Resource resource) {
        String result = "";
        try {
            File file = resource.getFile();
            result = fileToJsonString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<JSONObject> getSubscribe(String jsonString) {
        List<JSONObject> result = new ArrayList<>();
        JSONObject subscribeList = JSON.parseObject(jsonString).getJSONObject("SubscribeList");
        JSONArray subscribe = subscribeList.getJSONArray("Subscribe");
        for (int i = 0; i <subscribe.size(); i++) {
            result.add(subscribe.getJSONObject(i));
        }
        return result;
    }

    public static List<JSONObject> setCancelFlag(List<JSONObject> jsonObjectList) {
        List<JSONObject> result =new ArrayList<>();
        for (JSONObject jsonObject : jsonObjectList) {
            jsonObject.put("cancelFlag", 1);
            result.add(jsonObject);
        }
        return result;
    }

    public static String afterSend(List<JSONObject> jsonObjects) {
        JSONArray jsonArray = new JSONArray();
        for (JSONObject jsonObject : jsonObjects) {
            jsonArray.add(jsonObject);
        }
        JSONObject subscribe = new JSONObject();
        subscribe.put("Subscribe", jsonArray);
        JSONObject subscribeList = new JSONObject();
        subscribeList.put("SubscribeList", subscribe);
        return JSON.toJSONString(subscribeList);
    }

    public static List<JSONObject> getMotorVehicle(List<JSONObject> jsonObjects) {
        List<JSONObject> result = new ArrayList<>();
        for (JSONObject jsonObject : jsonObjects) {
            if (jsonObject.get("SubscribeDetail").equals("8")) {
                result.add(jsonObject);
            }
        }
        return result;
    }

    public static List<JSONObject> getDevice(List<JSONObject> jsonObjects) {
        List<JSONObject> result = new ArrayList<>();
        for (JSONObject jsonObject : jsonObjects) {
            if (jsonObject.get("SubscribeDetail").equals("3")) {
                result.add(jsonObject);
            }
        }
        return result;
    }

    public static List<JSONObject> getTollgate(List<JSONObject> jsonObjects) {
        List<JSONObject> result = new ArrayList<>();
        for (JSONObject jsonObject : jsonObjects) {
            if (jsonObject.get("SubscribeDetail").equals("51")) {
                result.add(jsonObject);
            }
        }
        return result;
    }



//    /**
//     * 获得JSON中的卡口订阅消息
//     * @param jsonString
//     * @return
//     */
//    public static String getTollgate(String jsonString) {
//        return JSON.parseObject(jsonString).getJSONObject("Tollgate").toJSONString();
//    }
//
//    /**
//     * 获得JSON中的设备订阅消息
//     * @param jsonString
//     * @return
//     */
//    public static String getDevice(String jsonString) {
//        return JSON.parseObject(jsonString).getJSONObject("Device").toJSONString();
//    }
//
//    /**
//     * 获得JSON中的过车信息订阅消息
//     * @param jsonString
//     * @return
//     */
//    public static String getMotorVehicle(String jsonString) {
//        return JSON.parseObject(jsonString).getJSONObject("MotorVehicle").toJSONString();
//    }
//
//    /**
//     * 组合JSON中的所有信息
//     * @param jsonString
//     * @return
//     */
//    public static String getAll(String jsonString) {
//        JSONArray device = JSON.parseObject(getDevice(jsonString)).getJSONObject("SubscribeList").getJSONArray("Subscribe");
//        JSONArray motorVehicle = JSON.parseObject(getMotorVehicle(jsonString)).getJSONObject("SubscribeList").getJSONArray("Subscribe");
//        JSONArray tollgate = JSON.parseObject(getTollgate(jsonString)).getJSONObject("SubscribeList").getJSONArray("Subscribe");
//        JSONArray all = new JSONArray();
//        all.addAll(device);
//        all.addAll(motorVehicle);
//        all.addAll(tollgate);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("Subscribe", all);
//        JSONObject result = new JSONObject();
//        result.put("SubscribeList", jsonObject);
//        return result.toJSONString();
//    }
//
//    public static JSONArray getJOSNArray(String subscribeList) {
//        JSONObject jsonObject = JSON.parseObject(subscribeList);
//        return JSON.parseObject(subscribeList).getJSONObject("SubscribeList").getJSONArray("Subscribe");
//    }
//
//
//    public static List<String> getListFromJson(String jsonString) {
//        List<String> result = new ArrayList<>();
//        JSONArray jsonArray = JSON.parseObject(jsonString).getJSONObject("SubscribeList").getJSONArray("Subscribe");
//
//        for (int i=0; i<jsonArray.size(); i++) {
//            String subscribe = jsonArray.getString(i);
//            result.add(subscribe);
//        }
//        return result;
//    }
//
//    public static String getJsonFromList(List<String> stringList) {
//        JSONArray jsonArray = new JSONArray();
//        for (String subscribe : stringList) {
//            jsonArray.add(JSON.parse(subscribe));
//        }
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("Subscribe", jsonArray);
//        JSONObject result = new JSONObject();
//        result.put("SubscribeList", jsonObject);
//        return result.toJSONString();
//    }
//
//    public static String subscribeToJsonString(String subscribe) {
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.add(JSON.parse(subscribe));
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("Subscribe", jsonArray);
//        JSONObject result = new JSONObject();
//        result.put("SubscribeList", jsonObject);
//        return result.toJSONString();
//    }
//
//    public static String getSubscribeID (String subscribe) {
//        return JSON.parseObject(subscribe).getString("SubscribeID");
//    }
}
