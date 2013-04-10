package com.cloud.elastic.router;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Executer implements Runnable{

	private String command;
	private File dir;
	
	private Log log = LogFactory.getLog(Executer.class);
	
	public Executer(String command,File dir){
		
		this.command = command;
		this.dir = dir;
		
	}
	
	public void run() {
		
		log.info("command:"+command+"\t"+dir.getAbsolutePath());
		
		try {
			
			Process process = java.lang.Runtime.getRuntime().exec(command,null,dir);
			InputStream is = process.getInputStream();
			@SuppressWarnings("unused")
			int length = 0 ;  
			byte[] buffer = new byte[1024];
			
			while((length = is.read(buffer))!=-1){
				
				log.info(new String(buffer));
			}
			
			process.destroy();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}	
		
	}
	
}
