package com.icp.monitor.displayer.jobs;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {
	
	private static Logger log = Logger.getLogger(MainTest.class);
	public static void main(String[] args) {
		
		ApplicationContext springContext = new ClassPathXmlApplicationContext(new String[]{"classpath:beans.xml"}); 
		
	}
	
	
	
}
