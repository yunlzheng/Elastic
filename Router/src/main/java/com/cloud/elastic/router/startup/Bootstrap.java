package com.cloud.elastic.router.startup;

import java.io.File;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cloud.elastic.router.Globals;



public class Bootstrap {

	/**
	 * Spring 上下文环境
	 * */
	private ApplicationContext applicationContext=null;
	

	
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
		
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.start();

		
	}
	
}
