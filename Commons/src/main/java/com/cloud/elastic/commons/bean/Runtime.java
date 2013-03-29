package com.cloud.elastic.commons.bean;

import java.io.Serializable;

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
 * 系统运行时环境组件对象
 * @author 云龙
 * */
@Entity
@Table(name="t_runtimes")
public class Runtime implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	/**运行时环境组件唯一标示*/
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(length=32)
	private int uuid;

	/**运行时环境所在服务器*/
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Server server;
	
	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
	
}
