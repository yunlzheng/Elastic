package com.cloud.elastic.commons.monitor.bean;

/**
 * SNMP 服务器监控主体对象
 *
 * */
public class SnmpServer {

	/**服务器编号*/
	private String uuid;
	
	/**服务器所属用户编号*/
	private String user_id;
	
	/**服务器昵称*/
	private String name;
	
	/**SNMP ip地址*/
	private String ip;
	
	/**SNMP 协议端口*/
	private int port;
	
	/**SNMP 社区名*/
	private String community;
	
	/**监控时间间隔*/
	private int interval;
	
	/***报警组编号*/
	private String groupUuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}
	
	
	
	
	
}
