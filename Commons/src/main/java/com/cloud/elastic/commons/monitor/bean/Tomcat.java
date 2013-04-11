package com.cloud.elastic.commons.monitor.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Tomcat监控主体对象
 * 
 * */
@Entity
@Table(name="t_tomcat")
public class Tomcat implements Serializable{

	
	private static final long serialVersionUID = 1L;

	/**Tomcat编号*/
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(length=32)
	private String uuid;
	
	/**创建者编号*/
	private String userId;
	
	/***监控频率*/
	private String intervalTime;
	
	/**JVM内存占用率报警阀值*/
	private int jvmMemoryThreshold;
	
	/**报警组编号*/
	private String groupUuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	

	public String getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(String intervalTime) {
		this.intervalTime = intervalTime;
	}

	public int getJvmMemoryThreshold() {
		return jvmMemoryThreshold;
	}

	public void setJvmMemoryThreshold(int jvmMemoryThreshold) {
		this.jvmMemoryThreshold = jvmMemoryThreshold;
	}

	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}
	
	
	
}
