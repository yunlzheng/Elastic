package com.icp.monitor.commons.snmp.model;

import java.io.Serializable;

public class MemoryInfo implements Serializable{
	private String totalSwapSize;
	private String availableSwapSpace;
	private String totalRamInMachine;
	private String totalRamUsed;
	private String totalRamFree;
	private String totalRamShared;
	private String totalRamBuffered;
	private String totalCachedMemory;
	private String time;
	public MemoryInfo(){
	
	}
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTotalSwapSize() {
		return totalSwapSize;
	}
	public void setTotalSwapSize(String totalSwapSize) {
		this.totalSwapSize = totalSwapSize;
	}
	public String getAvailableSwapSpace() {
		return availableSwapSpace;
	}
	public void setAvailableSwapSpace(String availableSwapSpace) {
		this.availableSwapSpace = availableSwapSpace;
	}
	public String getTotalRamInMachine() {
		return totalRamInMachine;
	}
	public void setTotalRamInMachine(String totalRamInMachine) {
		this.totalRamInMachine = totalRamInMachine;
	}
	public String getTotalRamUsed() {
		return totalRamUsed;
	}
	public void setTotalRamUsed(String totalRamUsed) {
		this.totalRamUsed = totalRamUsed;
	}
	public String getTotalRamFree() {
		return totalRamFree;
	}
	public void setTotalRamFree(String totalRamFree) {
		this.totalRamFree = totalRamFree;
	}
	public String getTotalRamShared() {
		return totalRamShared;
	}
	public void setTotalRamShared(String totalRamShared) {
		this.totalRamShared = totalRamShared;
	}
	public String getTotalRamBuffered() {
		return totalRamBuffered;
	}
	public void setTotalRamBuffered(String totalRamBuffered) {
		this.totalRamBuffered = totalRamBuffered;
	}
	public String getTotalCachedMemory() {
		return totalCachedMemory;
	}
	public void setTotalCachedMemory(String totalCachedMemory) {
		this.totalCachedMemory = totalCachedMemory;
	}
	
}
