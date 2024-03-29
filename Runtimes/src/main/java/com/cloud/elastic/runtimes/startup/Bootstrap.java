package com.cloud.elastic.runtimes.startup;

import java.io.File;
import java.io.IOException;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cloud.elastic.commons.dao.RuntimeDao;
import com.cloud.elastic.commons.bean.Runtime;

import com.cloud.elastic.runtimes.Globals;
import com.cloud.elastic.runtimes.core.RunTimesCore;
import com.cloud.elastic.runtimes.info.SystemInfo;
import com.cloud.elastic.runtimes.rabbit.convert.RuntimeInstanceMessageConverter;
import com.cloud.elastic.runtimes.rabbit.listener.RuntimeInstanceMessageListener;


/**
 * Runtimes启动类
 * 参数一：服务器所在IP地址
 * 启动流程：检查该IP地址Runtimes是否已经存在，如果存在则退出
 * 保存该Runtimes信息到数据库
 * 
 * */
public class Bootstrap {
	
	private static Log log  = LogFactory.getLog(Bootstrap.class);
	
	private ApplicationContext context = null;
	

	public ApplicationContext getContext() {
		return context;
	}



	public void setContext(ApplicationContext context) {
		this.context = context;
	}



	private void setRouterRoot(){
		
		File routerJar = new File(System.getProperty("user.dir"),"Runtimes-0.0.1-SNAPSHOT.jar");
		if(routerJar.exists()){
			
			try {
				
				System.setProperty(Globals.ELASTIC_RUNTIMES_ROOT, (new File(System.getProperty("user.dir"),"..")).getCanonicalPath());
				
			} catch (IOException e) {
			
				System.setProperty(Globals.ELASTIC_RUNTIMES_ROOT,System.getProperty("user.dir"));
			}
			
			
		}else{
			
			System.setProperty(Globals.ELASTIC_RUNTIMES_ROOT,System.getProperty("user.dir"));
			
		}
		
		log.info("USE "+Globals.ELASTIC_RUNTIMES_ROOT+":"+System.getProperty(Globals.ELASTIC_RUNTIMES_ROOT));
		
	}
	
	
	
	public void startup(String ip){
		
		setRouterRoot();
		
		Runtime entity = new Runtime();
		
		entity.setIp(ip);
		
		context= new ClassPathXmlApplicationContext(new String[]{"beans.xml"}); 
		
		//注册Runtimes实例到数据库
		RuntimeDao runtimeDao = context.getBean(RuntimeDao.class);
		String[] propertyNames = {"ip"}; 
		List<Runtime> entitys = runtimeDao.findEqualByEntity(entity,propertyNames);
		
		if(entitys.size()!=0){
			
			log.info("Runtimes Instance already exist in paas. System exist!");
			entity = entitys.get(0);
			
			//测试时注释
			//System.exit(1);
			
		}
		
		log.info("Regist New Runtimes Instance....");
		runtimeDao.saveOrUpdate(entity);
		log.info("Regist Runtimes Instance Success... instance key["+entity.getUuid()+"]");
		SystemInfo.newInstance(entity.getUuid());
		
		ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);
	
		//启动消息监听通道
		RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
		Queue runtimeInstanceQueue = new Queue("runtimes-"+entity.getUuid());
		rabbitAdmin.declareQueue(runtimeInstanceQueue);
		TopicExchange runtimeTopicExchange = context.getBean("runtimeTopicExchange",TopicExchange.class);
		
		Binding runtimeInstanceBinding = BindingBuilder.bind(runtimeInstanceQueue).to(runtimeTopicExchange).with("*.runtimes."+entity.getUuid());
		rabbitAdmin.declareBinding(runtimeInstanceBinding);
		
		//Runtime 消息接收容器
		RuntimeInstanceMessageListener runtimeInstanceMessageListener = context.getBean("runtimeInstanceMessageListener",RuntimeInstanceMessageListener.class);
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		
		//创建一个自动删除的消息队列
		Queue runtimesQueue = new Queue("runtimes-"+entity.getUuid(),false,false,true);
		container.setQueues(runtimesQueue);
		MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(runtimeInstanceMessageListener,new RuntimeInstanceMessageConverter());
		listenerAdapter.setDefaultListenerMethod("handleLog");
		container.setMessageListener(listenerAdapter);
		container.start();
		
		log.info("runtimes instance listener container isRunning?"+container.isRunning());
		log.info("Started Runtimes Instance["+entity.getUuid()+"]");

		
		
	}
	
	public static void main(String[] args) {
		
		String ip = null;
		
		//验证参数
		if(args.length!=0){
			
			ip = args[0];
			
		}else{
			log.info("Arguments[ip] no avaliable");
			System.exit(1);
		}
		
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.startup(ip);
		//RunTimesCore core =  bootstrap.getContext().getBean("Core",RunTimesCore.class);
		
	}
	
}
