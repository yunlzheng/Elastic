package com.icp.monitor.displayer.util.mail;

public class MailTest {

	public static void main(String[] args) {
		
		//MailFactory sm = new MailFactory("490929728@qq.com", "");
		//sm.setSMTPHost("smtp.qq.com");
		//sm.setMailFrom("490929728@qq.com");
		//sm.setMailTo("1054516185@qq.com");
		//sm.setMsgContent("内容");
		//sm.setSubject("标题");
		//sm.SendMails();
		
		MailFactory mailFactory = MailFactory.newInstance("cloud_icp@qq.com", "123456789", "smtp.qq.com");
		mailFactory.SendMails("1054516185@qq.com", "邮件主题", "邮件");
		
	}
}
