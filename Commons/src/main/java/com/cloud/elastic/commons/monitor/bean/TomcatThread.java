package com.cloud.elastic.commons.monitor.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_tomcat_thread")
public class TomcatThread implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 编号 */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(length=32)
	private String uuid;

	/** 最大线程数 */
	private int maxThreadCount;

	/** 当前线程数 */
	private int currentThreadCount;

	/** 当前忙碌线程数 */
	private int currentThreadsBusy;

	/** 记录时间 */
	private Date logDate;

	/** 所属Tomcat */
	@OneToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	private Tomcat tomcat;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getMaxThreadCount() {
		return maxThreadCount;
	}

	public void setMaxThreadCount(int maxThreadCount) {
		this.maxThreadCount = maxThreadCount;
	}

	public int getCurrentThreadCount() {
		return currentThreadCount;
	}

	public void setCurrentThreadCount(int currentThreadCount) {
		this.currentThreadCount = currentThreadCount;
	}

	public int getCurrentThreadsBusy() {
		return currentThreadsBusy;
	}

	public void setCurrentThreadsBusy(int currentThreadsBusy) {
		this.currentThreadsBusy = currentThreadsBusy;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public Tomcat getTomcat() {
		return tomcat;
	}

	public void setTomcat(Tomcat tomcat) {
		this.tomcat = tomcat;
	}
	
}
