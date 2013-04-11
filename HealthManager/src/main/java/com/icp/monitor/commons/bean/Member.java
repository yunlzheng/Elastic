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
 * 告警账户
 * 
 * @author zheng
 * */
@Entity
@Table(name = "t_member")
@XmlRootElement(name = "member")
public class Member implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 告警账户编号
	 * */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/**
	 * 告警账户姓名
	 * */
	@Column(name = "name")
	private String name;

	/**
	 * 告警账户电子邮箱地址
	 * */
	@Column(name = "email")
	private String email;

	/**
	 * 告警账户电话
	 * */
	@Column(name = "tell")
	private String tell;

	/**
	 * 告警账户创建者
	 * */
	@Column(name = "creater")
	private String creater;
	
	/**
	 * 租户
	 * */
	@Column(name="tenant")
	private String tenant;

	
	
	/**
	 * 告警账户创建时间
	 * */
	@Column(name = "jointime")
	private String joinTime;

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

}
