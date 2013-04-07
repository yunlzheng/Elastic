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
	private String uuid;
	
	
	@Column(name="port_shutdown")
	private int portShutDown;
	
	@Column(name="port_http")
	private int portHttp;
	
	@Column(name="port_ajp")
	private int portAjp;
	
	@Column(name="confuguration")
	private String tomcatAvailableConfiguration;
	
	/**
	 * 运行时单元，标准状态
	 * @see Status
	 * */
	@Column(name="status")
	private int status;
	
	/**
	 * 运行时单元，实际状态
	 *  * @see Status
	 * */
	@Column(name="healthStatus")
	private int healthStatus;

	/**运行时单元所属的运行时环境*/
	@ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE})
	private Runtime runtime;
	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getPortShutDown() {
		return portShutDown;
	}

	public void setPortShutDown(int portShutDown) {
		this.portShutDown = portShutDown;
	}

	public int getPortHttp() {
		return portHttp;
	}

	public void setPortHttp(int portHttp) {
		this.portHttp = portHttp;
	}

	public int getPortAjp() {
		return portAjp;
	}

	public void setPortAjp(int portAjp) {
		this.portAjp = portAjp;
	}

	public String getTomcatAvailableConfiguration() {
		return tomcatAvailableConfiguration;
	}

	public void setTomcatAvailableConfiguration(String tomcatAvailableConfiguration) {
		this.tomcatAvailableConfiguration = tomcatAvailableConfiguration;
	}

	public Runtime getRuntime() {
		return runtime;
	}

	public void setRuntime(Runtime runtime) {
		this.runtime = runtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getHealthStatus() {
		return healthStatus;
	}

	public void setHealthStatus(int healthStatus) {
		this.healthStatus = healthStatus;
	}

	public enum Status{
		
		RUNNIG(0,"运行"),
		STOPING(1,"停止");
		
		private int status;
		private String desc;
		
		Status(int status,String desc){
			this.status = status;
			this.desc = desc;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}
	
}
