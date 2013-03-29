package com.cloud.elastic.commons.bean;

/**
 * 虚拟机资源对象
 * @author 云龙
 * */
public class Server {

	/**服务器编号*/
	private int uuid;
	
	/**服务器ip地址*/
	private String ip;
	
	/**服务器所属用户*/
	private User user;

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
