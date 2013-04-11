package com.icp.monitor.commons.snmp.model;

import java.io.Serializable;

public class Command implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String targetIP;
	private String community;
	private int delying;
	private String order;//cup io disk memory cancel cupload
	public Command(String targetIP,String commuity,int delying,String order){
		this.targetIP=targetIP;
		this.community=commuity;
		this.delying=delying;
		this.order=order;
	}
	public Command(){
		
	}
	public String getTargetIP() {
		return targetIP;
	}
	public void setTargetIP(String targetIP) {
		this.targetIP = targetIP;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public int getDelying() {
		return delying;
	}
	public void setDelying(int delying) {
		this.delying = delying;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
}
