package com.icp.monitor.commons.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 异常信息
 * */
@Entity
@Table(name = "t_exception_message")
@XmlRootElement(name = "exceptionmessage")
public class ExceptionMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/**
	 * 服务器IP地址|应用名称
	 * */
	@Column(name="Identifiekey")
	private String Identifiekey;

	/**
	 * 监控项目 CPU监控 内存监控 HTTP监控
	 * */
	@Column(name = "name")
	private String name;

	/**
	 * 异常类型(应用异常|服务器异常) 1:服务器异常 2:应用异常
	 */
	@Column(name = "type")
	private String type;

	/**
	 * 通知信息
	 * */
	@Column(name = "message")
	private String message;

	/**
	 * 故障级别 1:严重故障 2:一般故障 3:预警
	 */
	@Column(name = "level")
	private String level;

	/**
	 * 异常账号名
	 * */
	@Column(name = "creater")
	private String creater;

	@Column(name="tenantName")
	private String tenantName;
	
	/**
	 * 异常产生时间
	 */
	@Column(name = "startTime")
	private String startTime;

	/**
	 * 异常结束时间
	 */
	@Column(name = "endTime")
	private String endTime;

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



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIdentifiekey() {
		return Identifiekey;
	}

	public void setIdentifiekey(String identifiekey) {
		Identifiekey = identifiekey;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	
	

}
