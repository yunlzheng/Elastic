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

/**
 * 日志类
 * */
@Entity
@Table(name="t_tomcat_jvm_memory")
public class TomcatJvmMemory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**编号*/
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(length=32)
	private String uuid;
	
	/**JVM可用内存*/
	private long jvmFree;
	
	/**Jvm总内存*/
	private long jvmTotal;
	
	private Date logDate;
	
	/**Jvm最大内存*/
	private long jvmMax;
	
	@OneToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	private Tomcat tomcat;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getJvmFree() {
		return jvmFree;
	}

	public void setJvmFree(long jvmFree) {
		this.jvmFree = jvmFree;
	}

	public long getJvmTotal() {
		return jvmTotal;
	}

	public void setJvmTotal(long jvmTotal) {
		this.jvmTotal = jvmTotal;
	}

	public long getJvmMax() {
		return jvmMax;
	}

	public void setJvmMax(long jvmMax) {
		this.jvmMax = jvmMax;
	}

	public Tomcat getTomcat() {
		return tomcat;
	}

	public void setTomcat(Tomcat tomcat) {
		this.tomcat = tomcat;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	
	
	
}
