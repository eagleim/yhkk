package com.zxelec.yhkk.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_img_error_msg")
public class TImgErrorMsgPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7044141605639385705L;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;
	
	@Column(name = "motor_vehicle_id")
	private String motorVehicleId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "img_error_message")
	private String imgErrorMessage;
	
	@Column(name="img_url")
	private String imgUrl;
	
	@Column(name = "ins_time")
	private Date insTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMotorVehicleId() {
		return motorVehicleId;
	}

	public void setMotorVehicleId(String motorVehicleId) {
		this.motorVehicleId = motorVehicleId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImgErrorMessage() {
		return imgErrorMessage;
	}

	public void setImgErrorMessage(String imgErrorMessage) {
		this.imgErrorMessage = imgErrorMessage;
	}

	public Date getInsTime() {
		return insTime;
	}

	public void setInsTime(Date insTime) {
		this.insTime = insTime;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}
