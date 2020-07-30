package com.zxelec.yhkk.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *  kafka过车记录头信息
 *  消息元数据MessageMetaDataType.
 *  详见文档 《高并发消息总线接口规范.docx》附录：C.5
 *
 */
public class MessageMetaDataType{
    /*** 发送者标识 **/
    private String sender;
    /** 数据来源 **/
    private String souce;
    /**发送时间**/
    private String sendTime;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setSouce(String souce) {
        this.souce = souce;
    }

    public String getSouce() {
        return souce;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
