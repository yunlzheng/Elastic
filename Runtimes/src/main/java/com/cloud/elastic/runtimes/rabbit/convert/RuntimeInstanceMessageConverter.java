package com.cloud.elastic.runtimes.rabbit.convert;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.cloud.elastic.runtimes.rabbit.message.ApplicationMessage;


public class RuntimeInstanceMessageConverter extends AbstractMessageConverter{

	@Override
	protected Message createMessage(Object object,
			MessageProperties messageProperties) {
		
		throw new UnsupportedOperationException();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object fromMessage(Message message)
			throws MessageConversionException {
		
		ApplicationMessage applicationMessage = new ApplicationMessage();
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> msg = null;
		try {
			
			msg = mapper.readValue(message.getBody(), Map.class);
			String action = (String)msg.get("action");
			String applicationId = (String)msg.get("uuid");
			
			System.out.println("recive command:app["+applicationId+"]    action["+action+"]");
			
			//applicationMessage
			applicationMessage.setAction(action);
			applicationMessage.setUuid(applicationId);
			
		} catch (JsonParseException e) {
			
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return applicationMessage;
	}

}
