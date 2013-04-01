package com.cloud.elastic.controler.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.cloud.elastic.commons.rabbbit.routing.ApplicationRoutingKey;



/**
 * Cloud Controller命令调用
 * */
public class Command{

	private Log log = LogFactory.getLog(Command.class);
	
	@Autowired
	RabbitAdmin amqpAdmin;
	
	@Value("#{config['exchange.application']}")
	private String exchange_application;
	
	public void deploy(String uuid){
		
		
		sendMessage(exchange_application, ApplicationRoutingKey.DEPLOY+"."+uuid,new Message(uuid.getBytes(), new MessageProperties()));
		
	}
	
	public void undeploy(String uuid){
		
		sendMessage(exchange_application,ApplicationRoutingKey.UNDEPLOY+"."+uuid,new Message(uuid.getBytes(), null));
	}
	
	public void start(String uuid){
		
		sendMessage(exchange_application,ApplicationRoutingKey.START+"."+uuid,new Message(uuid.getBytes(), null));
	}
	
	public void stop(String uuid){
		sendMessage(exchange_application,ApplicationRoutingKey.STOP+"."+uuid,new Message(uuid.getBytes(), null));
	}
	
	public void expand(String uuid){
		sendMessage(exchange_application,ApplicationRoutingKey.EXPAND+"."+uuid,new Message(uuid.getBytes(), null));
	}
	
	public void shrink(String uuid){
		sendMessage(exchange_application,ApplicationRoutingKey.SHRINK+"."+uuid,new Message(uuid.getBytes(), null));
	}
	
	public void sendMessage(String exchange,String routingKey,Message message){
		
		System.out.println("publish message to exchange["+exchange+"] with routingKey["+routingKey+"]");
		amqpAdmin.getRabbitTemplate().send(exchange, routingKey, message);
		
	}
	
}
