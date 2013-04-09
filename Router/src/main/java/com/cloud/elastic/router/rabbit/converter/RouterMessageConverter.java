package com.cloud.elastic.router.rabbit.converter;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.cloud.elastic.router.rabbit.message.RouterMessage;

public class RouterMessageConverter extends AbstractMessageConverter{

	@Override
	protected Message createMessage(Object object,
			MessageProperties messageProperties) {
		
		return null;
	}

	@Override
	public Object fromMessage(Message message)
			throws MessageConversionException {
		
		return new RouterMessage();
	}

}
