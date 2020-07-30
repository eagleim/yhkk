package com.zxelec.yhkk.service;

import com.alibaba.fastjson.JSON;
import com.zxelec.yhkk.entity.Carpass;
import com.zxelec.yhkk.entity.MotionVehicleType;
import com.zxelec.yhkk.entity.VissMessage;
import com.zxelec.yhkk.utils.VissMessageConvertUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author: dily
 * @create: 2019-07-13 18:20
 **/
@Component
public class ConsumerService {
    @KafkaListener(topics = { "${kafka.topic}" })
    public void receive(List<ConsumerRecord<?, byte[]>> consumerRecord) {
        for(ConsumerRecord<?, byte[]> record:consumerRecord){
            String body = new String(record.value());
            System.out.println("这是消费者记录值："+body);
//            try {
//                VissMessage<Carpass> vissMessage = VissMessageConvertUtils.resolveVissMessage(record.value(),Carpass.class);
//                    Carpass body = vissMessage.getBody();
//                    String rebody= JSON.toJSONString(body);
//                    String retype=JSON.toJSONString(vissMessage.getType());
//                    System.out.println("消息头："+retype);
//                    System.out.println("消费者消息体："+rebody);
//                    /**
//                     * 大华专用配置
//                     */
//                    //如果近景照片为null，则获取远景照片放入近景中、已经和大华确认
//                    if(StringUtils.isEmpty(body.getStorageUrl1())) {
//                        body.setStorageUrl1(body.getStorageUrl3());
//                    }
//
//                System.out.println(vissMessage.getBody());
//            } catch (UnsupportedEncodingException e) {
//            }
        }
    }
}
