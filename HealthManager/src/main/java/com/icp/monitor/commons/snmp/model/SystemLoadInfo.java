package com.icp.monitor.commons.snmp.model;

import java.io.Serializable;

public class SystemLoadInfo implements Serializable{
	private String oneMinuteLoad;
	private String fiveMinuteLoad;
	private String fifteenMinuteLoad;
	private String time;
	public SystemLoadInfo(){
		
	}
	public String getOneMinuteLoad() {
		return oneMinuteLoad;
	}
	public void setOneMinuteLoad(String oneMinuteLoad) {
		this.oneMinuteLoad = oneMinuteLoad;
	}
	public String getFiveMinuteLoad() {
		return fiveMinuteLoad;
	}
	public void setFiveMinuteLoad(String fiveMinuteLoad) {
		this.fiveMinuteLoad = fiveMinuteLoad;
	}
	public String getFifteenMinuteLoad() {
		return fifteenMinuteLoad;
	}
	public void setFifteenMinuteLoad(String fifteenMinuteLoad) {
		this.fifteenMinuteLoad = fifteenMinuteLoad;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
