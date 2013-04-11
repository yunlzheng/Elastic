package com.icp.monitor.commons.statistics;

/**
 * 预警人员相关的报警信息统计
 * */
public class AlertStatistics {

	/**预警人员名称*/
	private String name;
	
	/**
	 * 短信数量
	 * */
	private Long messageNum;
	
	/**
	 * 邮件发送数量
	 * */
	private Long mailNum;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getMessageNum() {
		return messageNum;
	}

	public void setMessageNum(Long messageNum) {
		this.messageNum = messageNum;
	}

	public Long getMailNum() {
		return mailNum;
	}

	public void setMailNum(Long mailNum) {
		this.mailNum = mailNum;
	}

	
	
	
}
