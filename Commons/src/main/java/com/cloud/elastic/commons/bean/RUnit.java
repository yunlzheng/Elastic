package com.cloud.elastic.commons.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 运行时单元对象
 * @author 云龙
 * 
 * */
@Entity
@Table(name="t_runit")
public class RUnit implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	/**RUnit编号*/
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(length=32)
	private int uuid;
	
	/**运行时单元暂用的端口*/
	@Column(name="port")
	private int port;


	/**运行时单元所属的运行时环境*/
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE})
	private Runtime runtime;
	
	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	
}
