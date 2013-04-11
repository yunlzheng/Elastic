package com.icp.monitor.displayer.resources.impl;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.Response;
import com.icp.monitor.commons.bean.SmsNetConstruction;
import com.icp.monitor.commons.dao.SmsNetConstructionDao;
import com.icp.monitor.commons.dao.impl.SmsNetConstructionDaoImpl;
import com.icp.monitor.displayer.resources.SmsResources;

public class SmsResourcesImpl implements SmsResources, ApplicationContextAware {

	private ApplicationContext content = null;
	private SmsNetConstructionDao sncd = null;

	public List<SmsNetConstruction> list() {
		// NetConstruction nc = new
		// NetConstruction("18384103317","用户你好,企业邮件提示你你的服务器出现异常信息,请及时处理",content);
		// nc.smsNetConSendMessage();
		// return "ok";
		sncd = (SmsNetConstructionDaoImpl) content
				.getBean("smsNetConstructionDao");

		return sncd.list("from SmsNetConstruction");
	}

	public SmsNetConstruction add() {
		return null;
	}

	public Response delete() {
		return null;
	}

	public SmsNetConstruction update() {
		return null;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.content = applicationContext;
	}
}
