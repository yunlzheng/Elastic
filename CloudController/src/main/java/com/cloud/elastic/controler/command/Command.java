package com.cloud.elastic.controler.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.cloud.elastic.commons.command.ApplicationCommand;
import com.cloud.elastic.commons.dao.RuntimeDao;
import com.cloud.elastic.commons.bean.Runtime;

/**
 * Cloud Controller命令调用
 * */
public class Command{

	private Log log = LogFactory.getLog(Command.class);
	
	@Autowired
	private RabbitAdmin amqpAdmin;
	
	@Autowired
	private RuntimeDao runtimeDao;
	
	@Value("#{config['exchange.application']}")
	private String exchange_application;
	
	public void deploy(String uuid){
		
		sendMessage(uuid,ApplicationCommand.DEPLOY);
		
	}
	
	public void undeploy(String uuid){
		
		sendMessage(uuid,ApplicationCommand.UNDEPLOY);
		
	}
	
	public void start(String uuid){
		
		sendMessage(uuid,ApplicationCommand.START);
		
	}
	
	public void stop(String uuid){

		sendMessage(uuid,ApplicationCommand.STOP);
		
	}
	
	public void expand(String uuid){

		sendMessage(uuid,ApplicationCommand.EXPAND);
		
	}
	
	public void shrink(String uuid){
	
		sendMessage(uuid,ApplicationCommand.SHRINK);
	}
	
	public void sendMessage(String uuid,String action){
		
		Runtime templateRuntime = new Runtime();
		templateRuntime.setApplication_uuid(uuid);
		List<Runtime> runtimes = runtimeDao.findEqualByEntity(templateRuntime, new String[]{"application_uuid"});
		for(Runtime runtime:runtimes){
		
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("action", action);
			data.put("uuid", uuid);
			
			try {
				this.sendMessage(exchange_application, "*.runtimes."+runtime.getUuid(), data);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public void sendMessage(String exchange,String routingKey,Map<String,Object> data) throws JsonGenerationException, JsonMappingException, IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		String body = mapper.writeValueAsString(data);
		
		Message message = new Message(body.getBytes(), new MessageProperties());
		log.info("publish message["+body+"] to exchange["+exchange+"] with routingKey["+routingKey+"]");
		amqpAdmin.getRabbitTemplate().send(exchange, routingKey, message);
		
	}
	
}
