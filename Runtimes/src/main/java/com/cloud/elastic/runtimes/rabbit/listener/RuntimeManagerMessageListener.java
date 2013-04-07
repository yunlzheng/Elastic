package com.cloud.elastic.runtimes.rabbit.listener;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.cloud.elastic.commons.dao.ApplicationDao;
import com.cloud.elastic.commons.dao.RuntimeDao;
import com.cloud.elastic.commons.bean.Application;
import com.cloud.elastic.commons.bean.Runtime;
import com.cloud.elastic.runtimes.info.SystemInfo;
import com.cloud.elastic.runtimes.rabbit.message.ManagementMessage;

public class RuntimeManagerMessageListener {

	@Autowired
	private RabbitAdmin rabbitAdmin;
	
	@Autowired
	private RuntimeDao runtimeDao; 
	
	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private ApplicationContext context;
	
	public void handleLog(ManagementMessage message){
		
		System.out.println("handled message:"+message.getCommand());
	
		
	}
	
	public void BindingApplication(){
		
		String instanceKey = SystemInfo.getInstanceKey();
		Runtime entity = runtimeDao.get(instanceKey);
		Application aplication = applicationDao.get(entity.getApplication_uuid());
		Queue runtimesQueue = new Queue("runtimes-"+entity.getUuid());
		TopicExchange runtimeTopicExchange = context.getBean("runtimeTopicExchange",TopicExchange.class);
		Binding runtimeBinding = BindingBuilder.bind(runtimesQueue).to(runtimeTopicExchange).with("*."+entity.getUuid()+"."+aplication.getUuid());
		rabbitAdmin.declareQueue(runtimesQueue);
		rabbitAdmin.declareBinding(runtimeBinding);
		System.out.println("Binding queue["+runtimesQueue.getName()+"] to exchenge["+runtimeTopicExchange.getName()+"] with key["+"*."+entity.getUuid()+"."+aplication.getUuid()+"]");
		
		
	}
	
	public void unBindingApplication(){
		
		String instanceKey = SystemInfo.getInstanceKey();
		Runtime entity = runtimeDao.get(instanceKey);
		Queue runtimesQueue = new Queue("runtimes-"+entity.getUuid());
		rabbitAdmin.declareQueue(runtimesQueue);
		
	}
	
}
