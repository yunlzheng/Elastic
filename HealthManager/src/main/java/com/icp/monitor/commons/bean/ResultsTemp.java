package com.icp.monitor.commons.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * 存放定时任务分析结果
 * 	key                 value
 * 存放所有虚拟机地址	
 * 	vms				   ip1;ip2;ip3;ip4
 * 
 * */
@Entity(name="t_jobs_result_temp")
public class ResultsTemp implements Serializable{

	@Id
	@Column(name="_key",unique=true)
	private String key;
	
	@Column(name="_value")
	private String value;
	
	@Column(name="updateTime")
	private String updateTime;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
