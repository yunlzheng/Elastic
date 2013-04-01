package com.cloud.elastic.controler.rabbit.config;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudControllrtRabbitConfiguration {

	private Log log = LogFactory.getLog(CloudControllrtRabbitConfiguration.class);
	
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
	
	@Bean TopicExchange applicationExchange(){
		
		return new TopicExchange(exchange_application);
	}
	
	@Bean
	public Queue cloudApplicationQueue(){
		
		String name = "cloudController-Log";
		return new Queue(name);
		
	}
	
	@Bean
	public Binding cloudApplicationBinding(){
		
		return BindingBuilder.bind(cloudApplicationQueue()).to(applicationExchange()).with("*.*");
		
	}
	
}
