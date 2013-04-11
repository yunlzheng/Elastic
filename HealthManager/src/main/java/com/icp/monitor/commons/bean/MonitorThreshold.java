package com.icp.monitor.commons.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 监控阀值设置
 * 
 * @author zheng
 * @version 0.0.1
 * */
@Entity
@Table(name = "t_monitor_threshlod")
public class MonitorThreshold implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ip")
	private String ip;

	@Column(name = "cpuThreshlod")
	private int cpuThreshlod;

	@Column(name = "memThreshlod")
	private int memThreshlod;

	@Column(name = "ioThreshlod")
	private int ioThreshlod;

	@Column(name = "diskThreshlod")
	private int diskThreshlod;

	@Column(name = "jointime")
	private String joinTime;

	@Column(name = "admin")
	private boolean admin = false;
	
	@Column(name="creater")
	private String creater;
	
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
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

	public int getCpuThreshlod() {
		return cpuThreshlod;
	}

	public void setCpuThreshlod(int cpuThreshlod) {
		this.cpuThreshlod = cpuThreshlod;
	}

	public int getMemThreshlod() {
		return memThreshlod;
	}

	public void setMemThreshlod(int memThreshlod) {
		this.memThreshlod = memThreshlod;
	}

	public int getIoThreshlod() {
		return ioThreshlod;
	}

	public void setIoThreshlod(int ioThreshlod) {
		this.ioThreshlod = ioThreshlod;
	}

	public int getDiskThreshlod() {
		return diskThreshlod;
	}

	public void setDiskThreshlod(int diskThreshlod) {
		this.diskThreshlod = diskThreshlod;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

}
