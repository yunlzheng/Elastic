package com.cloud.elastic.commons.monitor.bean;

/**
 * Tomcat内存报表对象
 * */
public class TomcatJvmMemoryReport {

	/**编号*/
	public String uuid;
	
	/**所属Tomcat*/
	public String tomcat_uuid;
	
	/**月*/
	public int month;
	
	/**天*/
	public int day;
	
	/**时*/
	public int hour;
	
	public int val;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}
	public String getTomcat_uuid() {
		return tomcat_uuid;
	}

	public void setTomcat_uuid(String tomcat_uuid) {
		this.tomcat_uuid = tomcat_uuid;
	}
	
}
