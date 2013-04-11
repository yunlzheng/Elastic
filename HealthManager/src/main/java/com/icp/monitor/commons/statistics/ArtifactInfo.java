package com.icp.monitor.commons.statistics;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="appinfo")
public class ArtifactInfo {

	/**应用编号*/
	private int appid;
	
	/**应用名称*/
	private String appname;
	
	/**1为jar 2为app*/
	private String appCategory;

	/**部署状态*/
	private String appStatus;
	
	/**
	 * 所有人
	 * */
	private String createrName;
	
	
	/**描述信息*/
	private String desc;
	
	/**
	 * 所属集群编号
	 * */
	private int clusterId;
	
	private String clusterName;
	/**
	 * 所属域
	 * */
	private String domain;
	
	/**域类型 公有 私有*/
	private String domainType;
	
	/**访问地址*/
	private String url;

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getAppCategory() {
		return appCategory;
	}

	public void setAppCategory(String appCategory) {
		this.appCategory = appCategory;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public int getClusterId() {
		return clusterId;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDomainType() {
		return domainType;
	}

	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
