package com.icp.monitor.commons.bean;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Value;

@XmlRootElement(name="configuration")
public class Configuration {

	private String iaasUrl=null;

	private String lbserver = null;
	
	private String lbSvnameFilter;
	
	public String getLbSvnameFilter() {
		return lbSvnameFilter;
	}

	@Value("#{SystemConfig['lbSvnameFilter']}")
	public void setLbSvnameFilter(String lbSvnameFilter) {
		this.lbSvnameFilter = lbSvnameFilter;
	}

	public String getIaasUrl() {
		return iaasUrl;
	}

	@Value("#{SystemConfig['iaasurl']}")
	public void setIaasUrl(String iaasUrl) {
		this.iaasUrl = iaasUrl;
	}

	public String getLbserver() {
		return lbserver;
	}

	@Value("#{SystemConfig['lbserver']}")
	public void setLbserver(String lbserver) {
		this.lbserver = lbserver;
	}
	
	

}
