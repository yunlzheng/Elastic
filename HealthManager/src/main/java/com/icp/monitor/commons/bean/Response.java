package com.icp.monitor.commons.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class Response {

	private String status;
	private String code;
	private String key;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
