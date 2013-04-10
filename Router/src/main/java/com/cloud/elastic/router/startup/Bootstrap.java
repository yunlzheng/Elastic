package com.cloud.elastic.router.startup;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cloud.elastic.router.Globals;
import com.cloud.elastic.router.factory.HAProxyTemplateFactory;

public class Bootstrap {

	/**
	 * Spring 上下文环境
	 * */
	private ApplicationContext applicationContext=null;
	
	private static Log log = LogFactory.getLog(Bootstrap.class);
	
	private void setRouterRoot(){
		
		File routerJar = new File(System.getProperty("user.dir"),"Router-0.0.1-SNAPSHOT.jar");
		if(routerJar.exists()){
			
			try {
				
				System.setProperty(Globals.ELASTIC_ROUTER_ROOT, (new File(System.getProperty("user.dir"),"..")).getCanonicalPath());
				
			} catch (IOException e) {
			
				System.setProperty(Globals.ELASTIC_ROUTER_ROOT,System.getProperty("user.dir"));
			}
			
			
		}else{
			
			System.setProperty(Globals.ELASTIC_ROUTER_ROOT,System.getProperty("user.dir"));
			
		}
		
		log.info("USE "+Globals.ELASTIC_ROUTER_ROOT+":"+System.getProperty(Globals.ELASTIC_ROUTER_ROOT));
		
	}
	
	private void initSpringApplicationContext(){
		
		setApplicationContext(new ClassPathXmlApplicationContext(new String[]{"beans.xml"})); 
		
	}
	
	private void start() {
		
		this.setRouterRoot();
		this.initSpringApplicationContext();
		
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public static void main(String[] args) {
		
		log.info("Init Spring Application Context");
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.start();
		
		log.info("Init Cloud Router information...");
		try {
			log.info("Update Haproxy inforation...");
			bootstrap.getApplicationContext().getBean(HAProxyTemplateFactory.class).createHaproxyConfigFile();
		
			
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Router Started...");

	}
	
}
