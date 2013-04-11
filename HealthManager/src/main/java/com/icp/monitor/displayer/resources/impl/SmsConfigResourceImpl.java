package com.icp.monitor.displayer.resources.impl;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.AlertMember;
import com.icp.monitor.commons.bean.Response;
import com.icp.monitor.commons.bean.SmsInfoConfig;
import com.icp.monitor.commons.bean.SmsNetConstruction;
import com.icp.monitor.commons.dao.SmsInfoConfigDao;
import com.icp.monitor.commons.dao.impl.SmsInfoConfigDaoImpl;
import com.icp.monitor.commons.dao.impl.SmsNetConstructionDaoImpl;
import com.icp.monitor.displayer.resources.SmsConfigResource;

public class SmsConfigResourceImpl implements SmsConfigResource,ApplicationContextAware{

	private ApplicationContext context = null;
	private SmsInfoConfigDao  smid = null;
	
	public SmsInfoConfig add(String sid,String username,String userpass,String smtphost,String mail,String mailpass,String encoding) {
		
		smid = (SmsInfoConfigDaoImpl)context.getBean("smsInfoConfigDao");
		int ssid;
		try {
			ssid = Integer.parseInt(sid);
		} catch (Exception e) {
			return null;
		}
		
		SmsNetConstruction  snc = ((SmsNetConstructionDaoImpl)context.getBean("smsNetConstructionDao")).get(ssid);
		SmsInfoConfig smsInfo = new SmsInfoConfig();
		
		smsInfo.setEmail(mail);
		smsInfo.setEmailPass(mailpass);
		smsInfo.setKey(userpass);
		smsInfo.setSmtpHost(smtphost);
		smsInfo.setUsername(username);
		smsInfo.setSmsNetConstruction(snc);
		smsInfo.setEncoding(encoding);
		
		smid.add(smsInfo);
		return smsInfo;
	}

	public List<SmsInfoConfig> list() {
		return null;
	}

	
	public AlertMember get(int id) {
		return null;
	}

	public Response delete(int id) {
		return null;
	}

	public SmsInfoConfig update(String username,String userpass,String smtphost,String mail,String mailpass) {
		return null;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
	}

	
}
