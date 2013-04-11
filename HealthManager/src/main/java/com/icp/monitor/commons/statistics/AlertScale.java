package com.icp.monitor.commons.statistics;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 系统发送短信和邮件的比例
 * */
@XmlRootElement(name="scale")
public class AlertScale {

	private long mailNum;
	private long smsNum;
	
	public long getMailNum() {
		return mailNum;
	}
	public void setMailNum(long mailNum) {
		this.mailNum = mailNum;
	}
	public long getSmsNum() {
		return smsNum;
	}
	public void setSmsNum(long smsNum) {
		this.smsNum = smsNum;
	}
	
	
	
}
