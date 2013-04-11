package com.icp.monitor.displayer.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApplicationStatusListener implements ServletContextListener{
	
	private Log log = LogFactory.getLog(ApplicationStatusListener.class);
	
	public void contextInitialized(ServletContextEvent sce) {
		
		log.info("icp.monitor Initialized");
		
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
		log.info("icp.monitor Destroyed");
		
	}

}
