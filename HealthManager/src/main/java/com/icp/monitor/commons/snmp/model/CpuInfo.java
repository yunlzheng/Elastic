package com.icp.monitor.commons.snmp.model;

import java.io.Serializable;

public class CpuInfo implements Serializable{
	private String serverId;
	private String time;
	private String percentageOfUserTime;
	private String percentageOfIdleTime;
	private String percentageOfSystemTime;
	private String rawUserCpuTime;
	private String rawSystemCpuTime;
	private String rawIdleCpuTime;
	private String rawNiceCpuTime;

	public CpuInfo(){}
	

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPercentageOfUserTime() {
		return percentageOfUserTime;
	}
	public void setPercentageOfUserTime(String percentageOfUserTime) {
		this.percentageOfUserTime = percentageOfUserTime;
	}
	public String getPercentageOfIdleTime() {
		return percentageOfIdleTime;
	}
	public void setPercentageOfIdleTime(String percentageOfIdleTime) {
		this.percentageOfIdleTime = percentageOfIdleTime;
	}
	public String getPercentageOfSystemTime() {
		return percentageOfSystemTime;
	}
	public void setPercentageOfSystemTime(String percentageOfSystemTime) {
		this.percentageOfSystemTime = percentageOfSystemTime;
	}
	public String getRawUserCpuTime() {
		return rawUserCpuTime;
	}
	public void setRawUserCpuTime(String rawUserCpuTime) {
		this.rawUserCpuTime = rawUserCpuTime;
	}
	public String getRawSystemCpuTime() {
		return rawSystemCpuTime;
	}
	public void setRawSystemCpuTime(String rawSystemCpuTime) {
		this.rawSystemCpuTime = rawSystemCpuTime;
	}
	public String getRawIdleCpuTime() {
		return rawIdleCpuTime;
	}
	public void setRawIdleCpuTime(String rawIdleCpuTime) {
		this.rawIdleCpuTime = rawIdleCpuTime;
	}
	public String getRawNiceCpuTime() {
		return rawNiceCpuTime;
	}
	public void setRawNiceCpuTime(String rawNiceCpuTime) {
		this.rawNiceCpuTime = rawNiceCpuTime;
	}


	
}
