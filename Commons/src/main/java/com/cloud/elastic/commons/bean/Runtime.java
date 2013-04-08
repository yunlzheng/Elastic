package com.cloud.elastic.commons.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	private String uuid;

	/**运行是环境绑定的应用编号*/
	@Column(length=32)
	private String application_uuid;
	
	/**运行时环境所在服务器IP地址*/
	@Column(name="ip")
	private String ip;
	
	/**运行是单元实例数*/
	@Column(name="instances_num")
	private int instancesNum=0;
	
	public String getApplication_uuid() {
		return application_uuid;
	}

	public void setApplication_uuid(String application_uuid) {
		this.application_uuid = application_uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getInstancesNum() {
		return instancesNum;
	}

	public void setInstancesNum(int instancesNum) {
		this.instancesNum = instancesNum;
	}
	
}
