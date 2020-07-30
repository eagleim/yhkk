package com.zxelec.yhkk.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
@PropertySource(value= {"classpath:server.properties"},
		encoding="UTF-8")
@Component
public class CustomProperties {


	//线程池维护线程的最少数量
	@Value("${yhkk.core.poolSize}")
	private int yhkkCorePoolSize;
	//#线程池维护线程的最大数量
	@Value("${yhkk.max.poolSize}")
	private int yhkkMaxPoolSize;
	//#缓存队列
	@Value("${yhkk.queue.capacity}")
	private int yhkkQueueCapacity;

	//#允许的空闲时间
	@Value("${yhkk.keepAlive}")
	private int yhkkKeepAlive;

	@Value("${kafka.concurrency}")
	private int kafkaConcurrency;

	@Value("${yhkk.deviceId}")
	private String deviceId;

	@Value("${yhkk.tollgateId}")
	private String tollgateId;

	@Value("${yhkk.digest.username}")
	private String yhkkDigestUsername;

	@Value("${yhkk.digest.passwd}")
	private String yhkkDigestPasswd;
	@Value("${VIID.Vc.username}")
	private String viidUsername;
	@Value("${VIID.Vc.password}")
	private String viidPassword;
	@Value("${keepAlive.serverUrl}")
	private String keepAliveUrl;
	@Value("${register.serverUrl}")
	private String registerUrl;


	public String getYhkkDigestUsername() {
		return yhkkDigestUsername;
	}

	public String getYhkkDigestPasswd() {
		return yhkkDigestPasswd;
	}

	public String getTollgateId() {
		return tollgateId;
	}

	public void setTollgateId(String tollgateId) {
		this.tollgateId = tollgateId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getYhkkKeepAlive() {
		return yhkkKeepAlive;
	}

	public void setYhkkKeepAlive(int yhkkKeepAlive) {
		this.yhkkKeepAlive = yhkkKeepAlive;
	}

	public int getYhkkCorePoolSize() {
		return yhkkCorePoolSize;
	}

	public void setYhkkCorePoolSize(int yhkkCorePoolSize) {
		this.yhkkCorePoolSize = yhkkCorePoolSize;
	}

	public int getYhkkMaxPoolSize() {
		return yhkkMaxPoolSize;
	}

	public void setYhkkMaxPoolSize(int yhkkMaxPoolSize) {
		this.yhkkMaxPoolSize = yhkkMaxPoolSize;
	}

	public int getYhkkQueueCapacity() {
		return yhkkQueueCapacity;
	}

	public void setYhkkQueueCapacity(int yhkkQueueCapacity) {
		this.yhkkQueueCapacity = yhkkQueueCapacity;
	}

	public int getKafkaConcurrency() {
		return kafkaConcurrency;
	}

	public void setKafkaConcurrency(int kafkaConcurrency) {
		this.kafkaConcurrency = kafkaConcurrency;
	}

	public void setYhkkDigestUsername(String yhkkDigestUsername) {
		this.yhkkDigestUsername = yhkkDigestUsername;
	}

	public void setYhkkDigestPasswd(String yhkkDigestPasswd) {
		this.yhkkDigestPasswd = yhkkDigestPasswd;
	}

	public String getKeepAliveUrl() {
		return keepAliveUrl;
	}

	public void setKeepAliveUrl(String keepAliveUrl) {
		this.keepAliveUrl = keepAliveUrl;
	}

	public String getRegisterUrl() {
		return registerUrl;
	}

	public void setRegisterUrl(String registerUrl) {
		this.registerUrl = registerUrl;
	}

	public String getViidUsername() {
		return viidUsername;
	}

	public void setViidUsername(String viidUsername) {
		this.viidUsername = viidUsername;
	}

	public String getViidPassword() {
		return viidPassword;
	}

	public void setViidPassword(String viidPassword) {
		this.viidPassword = viidPassword;
	}
}
