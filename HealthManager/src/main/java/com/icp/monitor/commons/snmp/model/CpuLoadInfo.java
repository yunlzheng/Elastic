package com.icp.monitor.commons.snmp.model;

import java.io.Serializable;

/**
 * SNMP协议CPU信息封装类
 * @author zheng
 * */
public class CpuLoadInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String index;
	/**over the last minute, of the percentageof 
	 * time that this processor was not idle
	**/
	private String processorLoad;
	
	/**
	 * A textual description of this device
	 * */
	private String descr;
	private String time;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getProcessorLoad() {
		return processorLoad;
	}
	public void setProcessorLoad(String processorLoad) {
		this.processorLoad = processorLoad;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
}
