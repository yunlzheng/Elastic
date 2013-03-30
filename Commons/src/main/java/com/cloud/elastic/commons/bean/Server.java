package com.cloud.elastic.commons.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 虚拟机资源对象
 * @author 云龙
 * */
@Entity
@Table(name="t_server")
public class Server implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	/**服务器编号*/
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(length=32)
	private int uuid;
	
	/**服务器ip地址*/
	@Column(name="ip")
	private String ip;
	
	/**服务器所属用户*/
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private User user;

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
