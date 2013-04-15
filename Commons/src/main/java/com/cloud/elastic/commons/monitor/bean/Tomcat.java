package com.cloud.elastic.commons.monitor.bean;

import java.io.Serializable;
import java.util.Date;

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
	private int userId;
	
	/**名称*/
	private String name;
	
	/**Tomcat状态页地址*/
	private String statusPageUrl;
	
	/**tomcat用户名*/
	private String tomcatUserName;
	
	/**tomcat密码*/
	private String tomcatPassword;
	
	/**监控服务的名称*/
	private String servicesName;
	
	/***监控频率*/
	private int intervalTime;
	
	/**创建时间*/
	private Date createDate;
	
	
	/**报警组编号*/
	private String groupUuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}



	public int getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}

	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatusPageUrl() {
		return statusPageUrl;
	}

	public void setStatusPageUrl(String statusPageUrl) {
		this.statusPageUrl = statusPageUrl;
	}

	public String getTomcatUserName() {
		return tomcatUserName;
	}

	public void setTomcatUserName(String tomcatUserName) {
		this.tomcatUserName = tomcatUserName;
	}

	public String getTomcatPassword() {
		return tomcatPassword;
	}

	public void setTomcatPassword(String tomcatPassword) {
		this.tomcatPassword = tomcatPassword;
	}

	public String getServicesName() {
		return servicesName;
	}

	public void setServicesName(String servicesName) {
		this.servicesName = servicesName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
}
