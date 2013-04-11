package com.icp.monitor.displayer.resources.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.Configuration;
import com.icp.monitor.displayer.resources.ConfigurationResources;

public class ConfigurationResourcesImpl implements ConfigurationResources,ApplicationContextAware{

	private ApplicationContext context = null;
	
	public Configuration getSystemConfiguration() {
		
		return (Configuration) context.getBean("Configuration");
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		
		this.context = arg0;
	}

}
