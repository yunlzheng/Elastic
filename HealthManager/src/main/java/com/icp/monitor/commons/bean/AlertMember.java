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
 * 报警成员信息
 * 
 * @author zheng
 * @version
 * */
@Entity
@Table(name = "t_alert_member")
@XmlRootElement(name = "alertmember")
public class AlertMember implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/**
	 * 虚拟机IP地址
	 * */
	@Column(name = "ip")
	private String ip;

	/**
	 * 监控成员编号
	 * */
	@Column(name = "mid")
	private int mid;

	@Column(name = "email")
	private boolean email;

	@Column(name = "tele")
	private boolean tele;

	@Column(name = "jointime")
	private String joinTime;
	
	@Column(name = "admin")
	private boolean admin;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isEmail() {
		return email;
	}

	public void setEmail(boolean email) {
		this.email = email;
	}

	public boolean isTele() {
		return tele;
	}

	public void setTele(boolean tele) {
		this.tele = tele;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

}
