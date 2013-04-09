package com.cloud.elastic.router.velocity.template;

import java.util.List;

/**
 * Haproxy配置模板对象
 * */
public class HaproxyTemplate {

	private String uuid;
	
	private String host;
	
	private List<BackendTemplate> backendTemplates;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<BackendTemplate> getBackendTemplates() {
		return backendTemplates;
	}

	public void setBackendTemplates(List<BackendTemplate> backendTemplates) {
		this.backendTemplates = backendTemplates;
	}
	
}
