package com.cloud.elastic.controler.resources.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.elastic.commons.bean.Application;
import com.cloud.elastic.commons.dao.ApplicationDao;
import com.cloud.elastic.controler.command.Command;
import com.cloud.elastic.controller.CloudController;

@Path("/cloudcontroller")
public class CloudControllerImpl implements CloudController{

	private Log log = LogFactory.getLog(CloudController.class);
	
	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private Command command;
	
	@GET
	@Path("/deploy/{id}")
	public String deploy(@PathParam("id") String applicationId) {
		
		Application application = applicationDao.get(applicationId);
		
		//应用是否存在
		if(application==null){
			log.info("application not exist!");
			return "应用不存在";
		}
		
		//应用是否在停止状态
		if(application.getHealth()!=Application.Health.UPLOADED.getHealth()){
			log.info("application already deployed");
			return "应用已经部署";
		}
		
		command.deploy(applicationId);
		
		return "操作处理中...";
		
		//调用部署接口
		
	}

	public void undeploy(String applicationId) {
		
		Application application = applicationDao.get(applicationId);
		
		//应用是否存在
		if(application==null){
			log.info("application not exist!");
			return;
		}
		
		//状态判断
		if(application.getHealth()==Application.Health.UPLOADED.getHealth()){
			log.info("application already undeployed");
			return;
		}
		
		command.undeploy(applicationId);
		
	}

	public void start(String applicationId) {
		
		Application application = applicationDao.get(applicationId);
		
		//应用是否存在
		if(application==null){
			log.info("application not exist!");
			return;
		}
		
		//状态判断
		if(application.getHealth()!=Application.Health.STOPED.getHealth()){
			
			log.info("application status error current status:"+application.getHealth());
			return;
			
		}
		
		command.start(applicationId);
		
	}

	public void stop(String applicationId) {
		
		Application application = applicationDao.get(applicationId);
		
		//应用是否存在
		if(application==null){
			log.info("application not exist!");
			return;
		}
		
		//状态判断
		if(application.getHealth()!=Application.Health.RUNNING.getHealth()){
			
			log.info("application status error current status:"+application.getHealth());
			return;
			
		}
		
		command.stop(applicationId);
		
	}

	public void expand(String applicationId) {
		
		Application application = applicationDao.get(applicationId);
		
		//应用是否存在
		if(application==null){
			log.info("application not exist!");
			return;
		}
		
		//应用未在运行中
		if(application.getHealth()!=Application.Health.RUNNING.getHealth()){
			
			log.info("application status error current status:"+application.getHealth());
			return;
			
		}
		
		command.expand(applicationId);
		
	}

	public void shrink(String applicationId) {
		
		Application application = applicationDao.get(applicationId);
		
		//应用是否存在
		if(application==null){
			log.info("application not exist!");
			return;
		}
		
		//应用未在运行中
		if(application.getHealth()!=Application.Health.RUNNING.getHealth()){
			
			log.info("application status error current status:"+application.getHealth());
			return;
			
		}
		
		command.shrink(applicationId);
		
	}


}
