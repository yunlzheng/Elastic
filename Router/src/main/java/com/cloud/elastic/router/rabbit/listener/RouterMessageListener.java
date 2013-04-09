package com.cloud.elastic.router.rabbit.listener;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.elastic.router.factory.HAProxyTemplateFactory;
import com.cloud.elastic.router.rabbit.message.RouterMessage;

public class RouterMessageListener {

	@Autowired
	private HAProxyTemplateFactory factory;
	
	public void handleLog(RouterMessage message){
		
		File configFile;
		try {
			configFile = factory.createHaproxyConfigFile();
			System.out.println("./haproxy -f "+configFile.getAbsoluteFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
