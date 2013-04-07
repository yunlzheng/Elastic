package com.cloud.elastic.runtimes.rabbit.convert;

import java.util.Date;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

public class ExampleMessageConverter extends AbstractMessageConverter{

	@Override
	protected Message createMessage(Object object,
			MessageProperties messageProperties) {
		
		throw new UnsupportedOperationException("not supported");
		
	}

	@Override
	public Object fromMessage(Message message)
			throws MessageConversionException {
		
		System.out.println(new Date()+message.getBody().toString());
		return null;
	}

}
