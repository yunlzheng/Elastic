package com.icp.monitor.commons.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext context;
	private static Log log = LogFactory.getLog(ApplicationContextUtil.class);
		
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {

		
		this.context = context;

	}

	public static ApplicationContext getApplicationContext() {

		if(context==null){
			log.info("context=null");
		}else{
			log.info("context not null");
		}
		return context;

	}

}
