package com.cloud.elastic.runtimes.rabbit.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.elastic.commons.bean.Application;
import com.cloud.elastic.commons.bean.Runtime;
import com.cloud.elastic.commons.dao.RuntimeDao;
import com.cloud.elastic.commons.rabbbit.config.RabbitServerConfiguration;
import com.cloud.elastic.runtimes.info.Info;
import com.cloud.elastic.runtimes.rabbit.handler.RuntimeInstanceMessageConverter;
import com.cloud.elastic.runtimes.rabbit.listener.RuntimeInstanceMessageListener;

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
	
	@Autowired
	private RuntimeDao runtimeDao;
	
	@Bean 
	public Info info(){
		
		Runtime runtimes = runtimeDao.get(systemKey);
		Application application = runtimes.getApplication();
		
		boolean ready = true;
		if(application==null){
			
			System.out.println("application no exist...");
			ready = false;
			
		}
		
		return new Info(application.getUuid(),ready);
		
	}
	
	@Bean
	public ConnectionFactory connectionFactory(){
		
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
		connectionFactory.setPort(port);
		log.info("Rabbit Server:"+host+":"+port);
		return connectionFactory;
		
	}
	
	@Bean
	public RabbitAdmin amqpAdmin(){
		
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
		rabbitAdmin.setAutoStartup(true);
		return rabbitAdmin;
	}
	
	@Bean
	public TopicExchange runtimeTopicExchange(){
		
		return new TopicExchange(exchange_application,true,false);
	}
	
	
	@Bean
	public Queue runtimeInstanceQueue() {
	
		return new Queue("runtimes_instance_"+systemKey);
		
	}
	
	@Bean
	public Binding runtimeInstanceBinding(){
		
		String uuid = info().getUuid();
		log.info("binding queue"+runtimeInstanceQueue().getName()+" to exchange "+runtimeTopicExchange().getName()+" with key"+" *."+uuid);
		return BindingBuilder.bind(runtimeInstanceQueue()).to(runtimeTopicExchange()).with("*."+uuid);
		
		
	}
	
	@Bean
	public RuntimeInstanceMessageListener runtimeInstanceMessageListener(){
		
		return new RuntimeInstanceMessageListener();
	}
	
	@Bean 
	public SimpleMessageListenerContainer exampleMessageListenerContainer(){
		
		System.out.println(connectionFactory().getHost()+":"+connectionFactory().getPort());
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		
		container.setQueues(runtimeInstanceQueue());
		
		MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(runtimeInstanceMessageListener(), new RuntimeInstanceMessageConverter());
		listenerAdapter.setDefaultListenerMethod("handleLog");
		container.setMessageListener(listenerAdapter);
		
		return container;
		
		
	}
	
}
