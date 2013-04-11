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
 * 报警信息
 * key ip 
 * */
@Entity
@Table(name="t_alert_message")
@XmlRootElement(name="alertmessage")
public class AlertMessage implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/**
	 * 服务器IP地址
	 * 或者应用名称
	 * */
	@Column(name = "Identifiekey")
	private String key;
	
	/**
	 * 通知信息
	 * */
	@Column(name = "message")
	private String message;
	
	/**
	 * 通知方式 短信/邮件
	 * */
	@Column(name = "type")
	private String type;
	
	/**
	 *预警账号名
	 * */
	@Column(name = "memname")
	private String memname;
	
	/**
	 * 用户名
	 * */
	@Column(name = "username")
	private String username;

	@Column(name="joinTime")
	private String joinTime;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMemname() {
		return memname;
	}

	public void setMemname(String memname) {
		this.memname = memname;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

}
