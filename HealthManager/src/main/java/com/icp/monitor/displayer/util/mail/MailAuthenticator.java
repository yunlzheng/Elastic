package com.icp.monitor.displayer.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 用于记录用户名和密码
 */
public class MailAuthenticator extends Authenticator{

	 /**
	  * 定义发送方的邮件名称和密码
	  */
	private String MAIL_USER = null;
	private String MAIL_PASSWORD = null;
	
	public MailAuthenticator(String mailUser,String mailPassword){
		this.MAIL_USER = mailUser;
		this.MAIL_PASSWORD = mailPassword;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(MAIL_USER, MAIL_PASSWORD);
	}

	public String getMAIL_USER() {
		return MAIL_USER;
	}

	public String getMAIL_PASSWORD() {
		return MAIL_PASSWORD;
	}
	
	
	
	
}
