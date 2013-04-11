package com.icp.monitor.commons.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "t_log_server_monitor")
@XmlRootElement(name = "log")
public class MonitorLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 监控信息编号
	 * */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/**
	 * 虚拟机IP地址
	 * */
	@Column(name = "ip")
	private String ip;

	/**
	 * cpu暂用率
	 * */
	@Column(name = "cpu")
	private String cpu;

	/**
	 * CPU暂用率
	 * */
	@Column(name = "mem")
	private String mem;

	/**
	 * IO暂用率
	 * */
	@Column(name = "io")
	private String io;

	/**
	 * Disk暂用率
	 * */
	@Column(name = "disk")
	private String disk;

	@Column(name = "jointime")
	private String joinTime;

	@Column(name = "detailTime")
	private String detailTime;

	@Column(name = "admin")
	private boolean admin = false;

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}

	public String getIo() {
		return io;
	}

	public void setIo(String io) {
		this.io = io;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public String getDetailTime() {
		return detailTime;
	}

	public void setDetailTime(String detailTime) {
		this.detailTime = detailTime;
	}

}
