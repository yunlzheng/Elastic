package com.cloud.elastic.router.rabbit.listener;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.elastic.router.factory.HAProxyTemplateFactory;
import com.cloud.elastic.router.rabbit.message.RouterMessage;

public class RouterMessageListener {

	@Autowired
	private HAProxyTemplateFactory factory;
	
	private Log log = LogFactory.getLog(RouterMessageListener.class);
	
	public void handleLog(RouterMessage message){
		
		log.info("####");
		try {
			factory.createHaproxyConfigFile();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
