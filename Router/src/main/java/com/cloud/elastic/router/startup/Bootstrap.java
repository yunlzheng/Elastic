package com.cloud.elastic.router.startup;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.cloud.elastic.router.factory.HAProxyTemplateFactory;

public class Bootstrap {

	/**
	 * Spring 上下文环境
	 * */
	private static ApplicationContext applicationContext=null;
	
	
	public static void main(String[] args) {
		
		/**初始化Spring上下文*/
		applicationContext= new ClassPathXmlApplicationContext(new String[]{"beans.xml"}); 
		
		HAProxyTemplateFactory factory = applicationContext.getBean(HAProxyTemplateFactory.class);
		try {
			factory.createHaproxyConfigFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
