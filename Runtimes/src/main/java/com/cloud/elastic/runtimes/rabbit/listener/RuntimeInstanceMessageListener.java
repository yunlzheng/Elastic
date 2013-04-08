package com.cloud.elastic.runtimes.rabbit.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.elastic.commons.bean.Runtime;
import com.cloud.elastic.commons.command.ApplicationCommand;
import com.cloud.elastic.commons.dao.ApplicationDao;
import com.cloud.elastic.commons.dao.RuntimeDao;
import com.cloud.elastic.runtimes.core.RunTimesCore;
import com.cloud.elastic.runtimes.info.SystemInfo;
import com.cloud.elastic.runtimes.rabbit.message.ApplicationMessage;

public class RuntimeInstanceMessageListener {

	private Log log = LogFactory.getLog(RuntimeInstanceMessageListener.class);
	
	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private RuntimeDao runtimeDao;
	
	@Autowired
	private RunTimesCore core;
	
	public void handleLog(ApplicationMessage message){
		
		//判断消息是否为空
		if(message==null){
			return;
		}
		
		String runtimeId = SystemInfo.getInstanceKey();
		
		Runtime instance = runtimeDao.get(runtimeId);
		
		if(instance==null){
			return;
		}
		
		if(instance.getApplication_uuid()==null){
			return;
		}
		
		//判断CloudController 发送的应用控制命令是否为与Runtimes 相Binding
		if(!message.getUuid().equals(instance.getApplication_uuid())){
			return;
		}
		
		execute(message.getAction(),message.getUuid());
		
	}
	
	public void execute(String action,String applicationId){
		
		log.info("execute:"+action);
		if(action.equals(ApplicationCommand.DEPLOY)){
			try {
				core.createRunit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(action.equals(ApplicationCommand.UNDEPLOY)){
			
			try {
				core.destoryRunit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(action.equals(ApplicationCommand.START)){
			
			try {
				core.startRunit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(action.equals(ApplicationCommand.STOP)){
			
			try {
				core.stopRunit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(action.equals(ApplicationCommand.EXPAND)){
			
			try {
				core.cloneRunit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(action.equals(ApplicationCommand.SHRINK)){
			
			try{
				
				core.shrinkRunit();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(action.equals(ApplicationCommand.DELETE)){
			
			try{
				
				core.free();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		
		}
		
		
	}
	
	
	
}
