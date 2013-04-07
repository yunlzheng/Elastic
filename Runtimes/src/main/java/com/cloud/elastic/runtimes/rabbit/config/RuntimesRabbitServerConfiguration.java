package com.cloud.elastic.runtimes.rabbit.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.cloud.elastic.commons.dao.ApplicationDao;
import com.cloud.elastic.commons.dao.RuntimeDao;
import com.cloud.elastic.commons.rabbbit.config.RabbitServerConfiguration;

import com.cloud.elastic.runtimes.rabbit.listener.RuntimeInstanceMessageListener;
import com.cloud.elastic.runtimes.rabbit.listener.RuntimeManagerMessageListener;

@Configuration
public class RuntimesRabbitServerConfiguration {

	private Log log = LogFactory.getLog(RabbitServerConfiguration.class);

	
	@Value("#{config['system.key']}")
	private String systemKey;
	
	
	@Value("#{config['rabbit.host']}")
	private String host="localhost";
	
	@Value("#{config['rabbit.port']}")
	private int port;
	
	@Value("#{config['rabbit.username']}")
	private String username;
	
	@Value("#{config['rabbit.password']}")
	private String password;

	@Value("#{config['exchange.application']}")
	private String exchange_application;
	
	@Value("#{config['exchange.cloud']}")
	private String exchange_cloud;
	
	@Autowired
	private RuntimeDao runtimeDao;
	
	@Autowired
	private ApplicationDao applicationDao;
	
	
	
	@Bean
	public ConnectionFactory connectionFactory(){
		
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
		connectionFactory.setPort(port);
//		connectionFactory.setUsername(username);
//		connectionFactory.setPassword(password);
		log.info("Rabbit Server:"+host+":"+port);
		return connectionFactory;
		
	}
	
	@Bean
	public RabbitAdmin amqpAdmin(){
		
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
		rabbitAdmin.setAutoStartup(true);
		return rabbitAdmin;
	}
	
	@Bean(name="cloudManagerTopicExchange")
	public TopicExchange cloudManagerTopicExchange(){
		
		return new TopicExchange(exchange_cloud,true,false);
		
	}

	/**
	 * Runtime Manager 消息监听器
	 * 负责接收1绑定应用，解除绑定应用的操作
	 * */
	@Bean(name="runtimeManagerMessageListener")
	public RuntimeManagerMessageListener runtimeManagerMessageListener(){
		
		return new RuntimeManagerMessageListener();
		
	}
	

	
	@Bean(name="runtimeTopicExchange")
	public TopicExchange runtimeTopicExchange(){
		
		return new TopicExchange(exchange_application,true,false);
	}
	
	
	/**
	 * Runtime Instance消息监听器
	 * 负责接收 部署应用，启动应用，停止应用，卸载应用的操作
	 * */
	@Bean(name="runtimeInstanceMessageListener")
	public RuntimeInstanceMessageListener runtimeInstanceMessageListener(){
		
		return new RuntimeInstanceMessageListener();
		
	} 

	
	
}
