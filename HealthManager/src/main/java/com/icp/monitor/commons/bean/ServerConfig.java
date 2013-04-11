package com.icp.monitor.commons.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 表示监控配置
 * 
 * @author zheng
 * @version 0.0.2
 * */
@Entity
@Table(name = "t_server_config")
public class ServerConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * IP地址
	 * */
	@Id
	@Column(name = "ip")
	private String ip;

	/**
	 * 是否显示CPU细腻
	 * */
	@Column(name = "showcpu")
	private boolean showCpu;

	/**
	 * 是否显示内存信息
	 */
	@Column(name = "showmem")
	private boolean showMem;

	/**
	 * 是否显示Io信息
	 * */
	@Column(name = "showio")
	private boolean showIo;

	/**
	 * 是否显示磁盘信息
	 * */
	@Column(name = "showdisk")
	private boolean showDisk;

	/**
	 * 配置时间
	 * */
	@Column(name = "jointime")
	private String joinTime;

	@Column(name = "deyling")
	private int deyling;

	@Column(name = "admin")
	private boolean admin;
	
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

	public boolean isShowCpu() {
		return showCpu;
	}

	public void setShowCpu(boolean showCpu) {
		this.showCpu = showCpu;
	}

	public boolean isShowMem() {
		return showMem;
	}

	public void setShowMem(boolean showMem) {
		this.showMem = showMem;
	}

	public boolean isShowIo() {
		return showIo;
	}

	public void setShowIo(boolean showIo) {
		this.showIo = showIo;
	}

	public boolean isShowDisk() {
		return showDisk;
	}

	public void setShowDisk(boolean showDisk) {
		this.showDisk = showDisk;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public int getDeyling() {
		return deyling;
	}

	public void setDeyling(int deyling) {
		this.deyling = deyling;
	}

}
