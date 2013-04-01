package com.cloud.elastic.runtimes.rabbit.listener;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.elastic.commons.bean.Application;
import com.cloud.elastic.commons.dao.ApplicationDao;
import com.cloud.elastic.runtimes.rabbit.message.ApplicationMessage;

public class RuntimeInstanceMessageListener {

	@Autowired
	private ApplicationDao applicationDao;
	
	public void handleLog(ApplicationMessage message){
		
		System.out.println("recviced message with key:"+message.getRoutingKey()+"--->"+message.getUuid());
		
		String routingKey = message.getRoutingKey();
		String uuid = message.getUuid();
		
		if(routingKey.startsWith("DEPLOY")){
			
			deployApplication(uuid);
			
		}
		
	}
	
	public void deployApplication(String uuid){
		
		Application application = applicationDao.get(uuid);
		System.out.println("为应用["+application.getUrl()+"]部署实例");
	
	}
	
}
