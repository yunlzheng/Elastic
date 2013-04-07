package com.cloud.elastic.runtimes.rabbit.convert;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.cloud.elastic.runtimes.rabbit.message.ManagementMessage;

public class RuntimeManagerMessageConverter extends AbstractMessageConverter{

	@Override
	protected Message createMessage(Object object,
			MessageProperties messageProperties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object fromMessage(Message message)
			throws MessageConversionException {
		
		String action = new String(message.getBody());
		
		ManagementMessage iMessage = new ManagementMessage();
		
		iMessage.setCommand(action);
		
		return iMessage;
	}

}
