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
 *中国网建API接口参数
 */

@Entity
@Table(name = "t_sms_net_construction")
@XmlRootElement(name = "smsnetconstruction")
public class SmsNetConstruction implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	
	
	@Column(name="name")
	private String name;

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
