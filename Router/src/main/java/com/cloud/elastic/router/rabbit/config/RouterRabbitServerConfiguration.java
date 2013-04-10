package com.cloud.elastic.router.rabbit.config;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.elastic.router.rabbit.converter.RouterMessageConverter;
import com.cloud.elastic.router.rabbit.listener.RouterMessageListener;

@Configuration
public class RouterRabbitServerConfiguration {
	
	private Log log = LogFactory.getLog(RouterRabbitServerConfiguration.class);

	
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

	@Value("#{config['exchange.router']}")
	private String exchange_router;
	
	
	
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
	public TopicExchange routerTopicExchange(){
		
		return new TopicExchange(exchange_router,true,false);
	}
	
	@Bean
	public Queue routerQueue(){
		return new Queue("Router",false,false,true);
	}
	
	@Bean
	public Binding routerBinding(){
		
		return BindingBuilder.bind(routerQueue()).to(routerTopicExchange()).with("*.update");
		
	} 
	
	@Bean 
	public RouterMessageListener routerMessageListener(){
		return new RouterMessageListener();
	}

	@Bean 
	public SimpleMessageListenerContainer routerListenerContainer(){
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		
		container.setQueues(routerQueue());
		
		MessageListenerAdapter adapter = new MessageListenerAdapter(routerMessageListener(),new RouterMessageConverter());
		adapter.setDefaultListenerMethod("handleLog");
		container.setMessageListener(adapter);
		
		return container;
		
	}
	
}
