package com.cloud.elastic.commons.rabbbit.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitServerConfiguration {

	private Log log = LogFactory.getLog(RabbitServerConfiguration.class);

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
	
	@Bean
	public ConnectionFactory connectionFactory(){
		
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
		//connectionFactory.setUsername(username);
		//connectionFactory.setPassword(password);
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

	
}
