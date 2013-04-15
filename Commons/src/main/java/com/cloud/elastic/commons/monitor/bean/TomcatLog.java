package com.cloud.elastic.commons.monitor.bean;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.Cascade;  
import org.hibernate.annotations.CascadeType; 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**Tomcat异常信息*/
@Entity
@Table(name="t_tomcat_log")
public class TomcatLog implements Serializable{

	
	private static final long serialVersionUID = 1L;

	/**编号*/
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(length=32)
	private String uuid;
	
	/**起始时间*/
	private Date beginTime;
	
	/**结束时间*/
	private Date endTime;
	
	/**检测结果*/
	private String result;
	
	/**所属Tomcat*/
	@OneToOne(fetch=FetchType.EAGER)
	private Tomcat tomcat;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Tomcat getTomcat() {
		return tomcat;
	}

	public void setTomcat(Tomcat tomcat) {
		this.tomcat = tomcat;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
}
