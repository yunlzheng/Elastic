package com.cloud.elastic.runtimes.startup;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cloud.elastic.runtimes.info.Info;

public class Bootstrap {

	public static void main(String[] args) {
		
		ApplicationContext context= new ClassPathXmlApplicationContext(new String[]{"beans.xml"});  
		
		Info info = context.getBean(Info.class);
		if(!info.isReady()){
			
			return;
			
		}else{
			
			System.out.println("Runtimes Instance-->"+info.getUuid());
			System.out.println("Runtimes Version-->"+info.version);
			System.out.println("Runtimes author-->"+info.author);
			
		}
		
	}
	
}
