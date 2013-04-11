package com.icp.monitor.commons.statistics;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="kvobj")
public class KVObj {

	private String name;
	private String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
