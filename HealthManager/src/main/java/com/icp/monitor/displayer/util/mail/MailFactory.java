package com.icp.monitor.displayer.util.mail;

import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MailFactory {

	//打印日志
	Log log = null;
	/**
	  * 定义发送方的邮件名称和密码
	  */
	private String MAIL_USER = null;
	private String MAIL_PASSWORD = null;
	//要发送Mail地址
    private String mailTo = null;
    //Mail发送的起始地址
    private String mailFrom = null;
    //SMTP主机地址
    private String smtpHost = null;
    //是否采用调试方式
    private boolean debug = false;
    
    //MAIL主题
    private String subject;
    //MAIL内容
    private String msgContent;
    
    private String messageBasePath = null;
    
	@SuppressWarnings("rawtypes")
	private Vector attachedFileList;
    private String messageContentMimeType ="text/html; charset=gb2312";
   
    private String mailbccTo = null;
    private String mailccTo = null;
    
    public static MailFactory newInstance(String MAIL_USER,String MAIL_PASSWORD,String SMTPHost){
    	
    	return new MailFactory(MAIL_USER, MAIL_PASSWORD,SMTPHost);
    }
    
    private MailFactory(String MAIL_USER,String MAIL_PASSWORD,String SMTPHost){
    	
    	log = LogFactory.getLog(MailFactory.class);
    	this.MAIL_USER = MAIL_USER;
    	this.MAIL_PASSWORD = MAIL_PASSWORD;
    	this.mailFrom = MAIL_USER;
    	this.smtpHost = SMTPHost;
    	
    }
    
    /**
     * 发送e_mail，返回类型为int
     * 当返回值为0时，说明邮件发送成功
     * 当返回值为3时，说明邮件发送失败
     */
    
    public int SendMails(String mailto,String subject,String content){
    	
    	this.mailTo = mailto;
    	this.subject = subject;
    	this.msgContent = content;
    	return this.SendMails();
    	
    }
    
    /**
     * 发送e_mail，返回类型为int
     * 当返回值为0时，说明邮件发送成功
     * 当返回值为3时，说明邮件发送失败
     */
    public int SendMails(){
    	
    	 Properties props = System.getProperties();
         props.put("mail.smtp.host", smtpHost);
         props.put("mail.smtp.auth", "true");
         
         MailAuthenticator auth = new MailAuthenticator(MAIL_USER,MAIL_PASSWORD);
         
         Session session = Session.getInstance(props, auth);
         session.setDebug(debug);
         MimeMessage msg = new MimeMessage(session);
         Transport trans = null;
         
         try {
        	   
             fillMail(session,msg);
             // send the message
             trans = session.getTransport("smtp");
             try {
            	 
                 trans.connect(smtpHost, MAIL_USER,MAIL_PASSWORD);
             } catch (AuthenticationFailedException e) {
                 e.printStackTrace();
                 System.out.println("连接邮件服务器错误：");
                 return 3;
             } catch (MessagingException e) {
                 System.out.println("连接邮件服务器错误：");
                 return 3;
             }
    
             Transport.send(msg);
             trans.close();
    
         } catch (MessagingException mex) {
             System.out.println("发送邮件失败：");
             mex.printStackTrace();
             Exception ex = null;
             if ((ex = mex.getNextException()) != null) {
                 System.out.println(ex.toString());
                 ex.printStackTrace();
             }
             return 3;
         } finally {
             try {
                 if (trans != null && trans.isConnected())
                     trans.close();
             } catch (Exception e) {
                 System.out.println(e.toString());
             }
         }
         System.out.println("发送邮件成功！");
         return 0;
    }
    
	@SuppressWarnings("rawtypes")
	private void fillMail(Session session,MimeMessage msg){
    	try {
    		String fileName = null;
        	Multipart mPart = new MimeMultipart();
        	
        	if(mailFrom != null){
        		msg.setFrom(new InternetAddress(mailFrom));
        		log.info("发送人MAIL地址:" + mailFrom);
        	}else{
        		log.info("没有指定发送人邮件地址");
        		return ;
        	}
        	
        	if(mailTo != null){
        		InternetAddress []address = InternetAddress.parse(mailTo);
        		msg.setRecipients(Message.RecipientType.TO, address);
        		log.info("收件人MAIL地址:" + mailTo);
        		
        	}else{
        		log.info("没有指定收件人邮件地址!");
        		return;
        	}
        	
        	if(mailccTo != null){
        		InternetAddress []ccaddress = InternetAddress.parse(mailccTo);
        		log.info("CCMail地址:" + mailccTo);
        		msg.setRecipients(Message.RecipientType.CC, ccaddress);
        	}
        	
        	if(mailbccTo != null){
        		InternetAddress []bccaddress = InternetAddress.parse(mailbccTo);
        		log.info("BCCMail地址:" + mailbccTo);
        		msg.setRecipients(Message.RecipientType.BCC, bccaddress);
        	}
        	
        	msg.setSubject(subject); 
        	InternetAddress []replyAddress = {new InternetAddress(mailFrom)};
        	msg.setReplyTo(replyAddress);
        	
        	//创建并设置第一部分消息
        	MimeBodyPart  mBodyContent = new MimeBodyPart();
        	
        	if(msgContent != null){
        		mBodyContent.setContent(msgContent,messageContentMimeType);
        	}else{
        		mBodyContent.setContent("", messageContentMimeType); //发送空消息
        	}
        	
        	mPart.addBodyPart(mBodyContent);
        	
        	
        	if(attachedFileList != null){
        		for(Enumeration fileList = attachedFileList.elements();fileList.hasMoreElements();){
        			fileName = (String) fileList.nextElement();
                    MimeBodyPart mBodyPart = new MimeBodyPart();
       
                    //获取消息文件
                    FileDataSource fds = new FileDataSource(messageBasePath + fileName);
                    System.out.println("Mail发送的附件为："+messageBasePath + fileName);
                    mBodyPart.setDataHandler(new DataHandler(fds));
                    mBodyPart.setFileName(fileName);
                    mPart.addBodyPart(mBodyPart);
                    
        		}
        	}
        	
        	msg.setContent(mPart);
        	msg.setSentDate(new Date());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }
    
  
    
    @SuppressWarnings("rawtypes")
	public void setAttachedFileList(java.util.Vector filelist)
    {
        attachedFileList = filelist;
    }
    public void setDebug(boolean debugFlag)
    {
        debug=debugFlag;
    }
    public void setMailbccTo(String bccto) {
        mailbccTo = bccto;
    }
    public void setMailccTo(String ccto) {
        mailccTo = ccto;
    }
    public void setMailFrom(String from)
    {
        mailFrom=from;
    }
    public void setMailTo(String to)
    {
        mailTo=to;
    }
    public void setMessageBasePath(String basePath)
    {
        messageBasePath=basePath;
    }
    public void setMessageContentMimeType(String mimeType)
    {
        messageContentMimeType = mimeType;
    }
    public void setMsgContent(String content)
    {
        msgContent=content;
    }
    public void setSMTPHost(String host)
    {
        smtpHost=host;
    }
    public void setSubject(String sub)
    {
        subject=sub;
    }
}
