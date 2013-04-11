package com.icp.monitor.commons.jvminfo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MemoryInfo{

	private String name;
	private long init;
	private long used;
	private long max;
	private long comitted;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getInit() {
		return init;
	}
	public void setInit(long init) {
		this.init = init;
	}
	public long getUsed() {
		return used;
	}
	public void setUsed(long used) {
		this.used = used;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public long getComitted() {
		return comitted;
	}
	public void setComitted(long comitted) {
		this.comitted = comitted;
	}
	
}
