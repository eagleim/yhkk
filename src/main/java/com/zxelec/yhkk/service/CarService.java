package com.zxelec.yhkk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxelec.yhkk.entity.*;
import com.zxelec.yhkk.entity.vc.MotorVehicle;
import com.zxelec.yhkk.entity.vc.MotorVehicleListObject;
import com.zxelec.yhkk.entity.vc.MotorVehicleObject;
import com.zxelec.yhkk.utils.CarpassPush2;
import com.zxelec.yhkk.utils.DateUtils;
import com.zxelec.yhkk.utils.HttpUtil;
import com.zxelec.yhkk.utils.VissMessageConvertUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author: dily
 * @create: 2019-07-12 20:32
 * 过车记录发送
 **/
@PropertySource(value = {"classpath:server.properties"}, encoding = "UTF-8")
@Service
public class CarService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private Logger logger = LogManager.getLogger(CarService.class);
    @Value("${kafka.topic}")
    private String topic;
    @Value("${message.sender}")
    private String sender;
    @Value("${message.source}")
    private String source;
    @Value("${VIID.Vc.Url}")
    private String VcUrl;
    @Value("${VIID.Vc.username}")
    private String username;
    @Value("${VIID.Vc.password}")
    private String password;
    @Value("${kafka.status}")
    private Integer kafkaStatus;

    /**
     * 异步多线程逐条处理过车记录
     * @param
     */
    @Async
    public void DealWithVehicleRecord(CarpassPushEntity carpassPushEntity){
        //logger.fatal(carpassPushEntity.getDeviceID());
        /** 发送给kafka **/
        if (kafkaStatus.equals(0)) {
            MotionVehicleType motionVehicleKafka = CarpassPush2.carpassPush2MotionVehicle(carpassPushEntity);
            analysisMotionVehicleData(motionVehicleKafka);
        }
        /** 发送到视图库 **/
        List<MotorVehicleObject> motorVehicleObjectList = new ArrayList<>();
        MotorVehicleObject motorVehicleVc = CarpassPush2.carpass2Vc(carpassPushEntity);
        motorVehicleObjectList.add(motorVehicleVc);
        MotorVehicleListObject motorVehicleListObject = new MotorVehicleListObject(motorVehicleObjectList);
        MotorVehicle motorVehicle = new MotorVehicle(motorVehicleListObject);
        sendMotionVehicle2Vc(motorVehicle);
    }

    /**
     * 接收平台数据
     *
     * @param carPushReq 数据对象
     */
    public void receiveMotionVehicleData(CarPushReq carPushReq) {
        List<CarpassPushEntity> carpassPush = new ArrayList<>();
        if (carPushReq == null) {
            logger.error("没有接收到过车记录carPushReq");
        } else
            carpassPush = carPushReq.getMotorVehicleObjectList();
        if (carpassPush.isEmpty()) {
            logger.error("carPushReq中没有过车记录carpassPush");
        } else {
            for (CarpassPushEntity carpassPushEntity : carpassPush) {
                DealWithVehicleRecord(carpassPushEntity);
            }
        }
    }

    /**
     * 解析实体对象转换为二进制
     *
     * @param motionVehicleType
     */
    private void analysisMotionVehicleData(MotionVehicleType motionVehicleType) {

        /** 消息头 **/
        MessageMetaDataType messageMetaDataType = new MessageMetaDataType();
        messageMetaDataType.setSendTime(DateUtils.data2String());
        VissMessage<MotionVehicleType> vissMessage = new VissMessage();
        messageMetaDataType.setSender(sender);
        messageMetaDataType.setSouce(source);
        vissMessage.setType(messageMetaDataType);
        vissMessage.setBody(motionVehicleType);
        try {
            byte[] by = VissMessageConvertUtils.vissMessage2Byte(vissMessage);
            sendMotionVehicleKafka(by);
        } catch (UnsupportedEncodingException e) {
            logger.error("过车记录转二进制失败！！！");
            e.printStackTrace();
        }
    }

    /**
     * 把转码后的二进制数组发给kafka
     *
     * @param by
     */
    private void sendMotionVehicleKafka(byte[] by) {
        ListenableFuture listenableFuture = kafkaTemplate.send(topic, by);
        String receive = "";
        try {
            receive = JSON.toJSONString(listenableFuture.get());
            if (receive.isEmpty())
                logger.error("数据发送失败");
            else
                logger.info("生产成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把过车记录发给视图库
     *
     * @param motorVehicle
     */
    private void sendMotionVehicle2Vc(MotorVehicle motorVehicle) {
        String motorVehcle = JSON.toJSONString(motorVehicle);
        ViidResult viidResult = HttpUtil.postToVIID(VcUrl, motorVehcle, username, password);
        if (Integer.parseInt(viidResult.getCode()) >= 500) {
            for (int i = 0; i < 3; i++) {
                viidResult = HttpUtil.postToVIID(VcUrl, motorVehcle, username, password);
                if (Integer.parseInt(viidResult.getCode()) < 400) {
                    logger.info("超时重传发送成功");
                    break;
                }
            }
        }
        if (viidResult.getResult().startsWith("{")) {
            JSONObject resultJson = JSON.parseObject(viidResult.getResult());
            JSONObject responseStatusListObject = resultJson.getJSONObject("ResponseStatusListObject");
            if (resultJson.get("ResponseStatusListObject") != null && resultJson.get("ResponseStatusListObject").toString().startsWith("{")) {
                if (responseStatusListObject.get("ResponseStatusObject") != null && responseStatusListObject.get("ResponseStatusObject").toString().startsWith("["))
                    ;
                {
                    JSONArray responseStatusObject = responseStatusListObject.getJSONArray("ResponseStatusObject");
                    for (int i = 0; i < responseStatusObject.size(); i++) {
                        JSONObject jsonObject = responseStatusObject.getJSONObject(i);
                        String statusCode = jsonObject.getString("StatusCode");
                        if (Integer.parseInt(statusCode) != 0) {
                            logger.error("视图库发送失败：" + viidResult.getResult());
                        }
                    }
                }
            }
        } else if (viidResult.getResult().startsWith("<")) {
            logger.error("返回结果");
            logger.error(viidResult.getResult());
        }

    }

}
