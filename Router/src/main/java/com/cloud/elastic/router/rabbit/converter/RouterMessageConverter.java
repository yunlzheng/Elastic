package com.cloud.elastic.router.rabbit.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.cloud.elastic.router.rabbit.message.RouterMessage;

public class RouterMessageConverter extends AbstractMessageConverter{

	private Log log = LogFactory.getLog(RouterMessageConverter.class);
	
	@Override
	protected Message createMessage(Object object,
			MessageProperties messageProperties) {
		
		return null;
	}

	@Override
	public Object fromMessage(Message message)
			throws MessageConversionException {
		
		log.info("convert message:"+new String(message.getBody()));
		
		RouterMessage imessage = new RouterMessage();
		imessage.setAction("update");
		
		return imessage;
	}

}
