package com.cloud.elastic.runtimes.rabbit.handler;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.cloud.elastic.runtimes.rabbit.message.ApplicationMessage;

public class RuntimeInstanceMessageConverter extends AbstractMessageConverter{

	@Override
	protected Message createMessage(Object object,
			MessageProperties messageProperties) {
		
		throw new UnsupportedOperationException(">>>");
		
	}

	@Override
	public Object fromMessage(Message message)
			throws MessageConversionException {
		
		ApplicationMessage applicationMessage = new ApplicationMessage();
		String uuid = new String(message.getBody());
		String routingKey = message.getMessageProperties().getReceivedRoutingKey();
		
		applicationMessage.setMessage(uuid);
		System.out.println(">>>"+uuid);
		applicationMessage.setUuid(uuid);
		applicationMessage.setRoutingKey(routingKey);
		
		return applicationMessage;
	}

}
