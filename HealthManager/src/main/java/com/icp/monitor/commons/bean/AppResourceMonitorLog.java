package com.icp.monitor.commons.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 应用监控信息
 * 应用注册的资源信息
 * */
@Entity
@Table(name="t_log_appresource")
public class AppResourceMonitorLog implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="processingTime")
	private String processingTime;
	
	@Column(name="maxTime")
	private String maxTime;
	
	@Column(name="requestCount")
	private String requestCount;
	
	@Column(name="errorCount")
	private String errorCount;
	
	@Column(name="loadTime")
	private String loadTime;
	
	@Column(name="classLoadTime")
	private String classLoadTime;

	@OneToOne
	@JoinColumn(name="app_log_id",nullable=false)
	private AppMonitorLog appMonitorLog;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(String processingTime) {
		this.processingTime = processingTime;
	}

	public String getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	public String getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(String requestCount) {
		this.requestCount = requestCount;
	}

	public String getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(String errorCount) {
		this.errorCount = errorCount;
	}

	public String getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(String loadTime) {
		this.loadTime = loadTime;
	}

	public String getClassLoadTime() {
		return classLoadTime;
	}

	public void setClassLoadTime(String classLoadTime) {
		this.classLoadTime = classLoadTime;
	}

	public AppMonitorLog getAppMonitorLog() {
		return appMonitorLog;
	}

	public void setAppMonitorLog(AppMonitorLog appMonitorLog) {
		this.appMonitorLog = appMonitorLog;
	}
	
	
	
}
