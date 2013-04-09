package com.cloud.elastic.router.velocity.template;

import java.util.List;

/**
 * Haproxy Backend配置
 * */
public class BackendTemplate {

	private String ip;
	private List<Integer> ports;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public List<Integer> getPorts() {
		return ports;
	}
	public void setPorts(List<Integer> ports) {
		this.ports = ports;
	}
	
	
	
}
