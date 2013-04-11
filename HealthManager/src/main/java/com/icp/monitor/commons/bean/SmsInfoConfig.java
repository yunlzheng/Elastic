package com.icp.monitor.commons.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 发送短信的相关配置信息记录表 
 */

@Entity
@Table(name = "t_sms_info_config")
@XmlRootElement(name = "smsnetconfig")
public class SmsInfoConfig implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	/**
	 * 编码方式(gbk,utf-8)
	 */
	@Column(name = "coding")
	private String encoding;
	
	/**
	 * 网建用户名
	 */
	@Column(name="username")
	private String username;
	
	/**
	 * 注册时填写的接口安全密码
	 */
	@Column(name="password")
	private String key;
	
	/**
	 * 发送方邮件
	 */
	@Column(name="email")
	private String email;
	
	/**
	 * 发送方邮件密码
	 */
	@Column(name="emailpass")
	private String emailPass;
	
	/**
	 * smtpHost
	 */
	@Column(name="smtphost")
	private String smtpHost;
	
	@OneToOne
	@JoinColumn(name="smsnetid",nullable=false)
	private SmsNetConstruction smsNetConstruction;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailPass() {
		return emailPass;
	}

	public void setEmailPass(String emailPass) {
		this.emailPass = emailPass;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public SmsNetConstruction getSmsNetConstruction() {
		return smsNetConstruction;
	}

	public void setSmsNetConstruction(SmsNetConstruction smsNetConstruction) {
		this.smsNetConstruction = smsNetConstruction;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	
}
