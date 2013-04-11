package com.icp.monitor.commons.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_log_app_monitor")
public class AppMonitorLog implements Serializable{

	
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 * */
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/** 应用上下文名称 */
	@Column(name="contextName")
	private String contextName;

	/**
	 * 应用所属域名称
	 * */
	@Column(name="hostName")
	private String hostName;

	/**
	 * 应用启动时间
	 */
	@Column(name="startTime")
	private String startTime;

	/**
	 * 启动持续时间
	 * */
	@Column(name="startUpTime")
	private String startUpTime;

	/***/
	@Column(name="tldScanTime")
	private String tldScanTime;

	/**
	 * 当前活跃session数
	 * */
	@Column(name="activeSessions")
	private String activeSessions;

	/**
	 * 
	 * */
	@Column(name="sessionCounter")
	private String sessionCounter;

	
	//private Set<AppResourceMonitorLog> appResources = new HashSet<AppResourceMonitorLog>();

	@Column(name="joinTime")
	private String joinTime;
	
	
//	@OneToMany(cascade={CascadeType.ALL})
//	public Set<AppResourceMonitorLog> getAppResources() {
//		return appResources;
//	}
//
//	public void setAppResources(Set<AppResourceMonitorLog> appResources) {
//		this.appResources = appResources;
//	}

	/**
	 * 最大活动session数
	 * */
	@Column(name="")
	private String maxActive;

	@Column(name="")
	private String rejectedSessions;

	@Column(name="")
	private String expiredSessions;

	@Column(name="")
	private String sessionMaxAliveTime;

	@Column(name="")
	private String sessionAverageAliveTime;

	@Column(name="")
	private String processingTime;

	public String getContextName() {
		return contextName;
	}

	public void setContextName(String contextName) {
		this.contextName = contextName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartUpTime() {
		return startUpTime;
	}

	public void setStartUpTime(String startUpTime) {
		this.startUpTime = startUpTime;
	}

	public String getTldScanTime() {
		return tldScanTime;
	}

	public void setTldScanTime(String tldScanTime) {
		this.tldScanTime = tldScanTime;
	}

	public String getActiveSessions() {
		return activeSessions;
	}

	public void setActiveSessions(String activeSessions) {
		this.activeSessions = activeSessions;
	}

	public String getSessionCounter() {
		return sessionCounter;
	}

	public void setSessionCounter(String sessionCounter) {
		this.sessionCounter = sessionCounter;
	}

	public String getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(String maxActive) {
		this.maxActive = maxActive;
	}

	public String getRejectedSessions() {
		return rejectedSessions;
	}

	public void setRejectedSessions(String rejectedSessions) {
		this.rejectedSessions = rejectedSessions;
	}

	public String getExpiredSessions() {
		return expiredSessions;
	}

	public void setExpiredSessions(String expiredSessions) {
		this.expiredSessions = expiredSessions;
	}

	public String getSessionMaxAliveTime() {
		return sessionMaxAliveTime;
	}

	public void setSessionMaxAliveTime(String sessionMaxAliveTime) {
		this.sessionMaxAliveTime = sessionMaxAliveTime;
	}

	public String getSessionAverageAliveTime() {
		return sessionAverageAliveTime;
	}

	public void setSessionAverageAliveTime(String sessionAverageAliveTime) {
		this.sessionAverageAliveTime = sessionAverageAliveTime;
	}

	public String getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(String processingTime) {
		this.processingTime = processingTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}
	
	

}
