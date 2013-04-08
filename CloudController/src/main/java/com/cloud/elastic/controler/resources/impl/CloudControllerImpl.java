package com.cloud.elastic.controler.resources.impl;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;


import com.cloud.elastic.commons.bean.Application;
import com.cloud.elastic.commons.bean.Runtime;
import com.cloud.elastic.commons.dao.ApplicationDao;
import com.cloud.elastic.commons.dao.RuntimeDao;
import com.cloud.elastic.controler.command.Command;
import com.cloud.elastic.controller.CloudController;

@Path("/cloudcontroller")
public class CloudControllerImpl implements CloudController{

	
	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private RuntimeDao runtimeDao;
	
	@Autowired
	private Command command;
	
	@GET
	@Path("/deploy/{id}")
	public String deploy(@PathParam("id") String applicationId) {
		
		try{
			
			Application application = applicationDao.get(applicationId);
			
			if(Application.Health.UNBINDED.getHealth()==application.getHealth()){
				
				//binding 应用和runtimes
				List<Runtime> runtimes = runtimeDao.find("from Runtime where application_uuid=null");
				if(runtimes.size()!=0){
					
					Runtime runtime = runtimes.get(0);
					runtime.setApplication_uuid(application.getUuid());
					runtimeDao.update(runtime);
					application.setHealth(Application.Health.BINDED.getHealth());
					applicationDao.update(application);
					
					
				}else{
					
					return "没有可用的运行环境资源,请稍候再试...";
					
				}
				
			}
			
			command.deploy(applicationId);

			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return "操作处理中...";
		
		//调用部署接口
		
	}

	@GET
	@Path("/undeploy/{id}")
	public String undeploy(@PathParam("id") String applicationId) {
		
		try {
			command.undeploy(applicationId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "操作处理中...";
		
	}

	@GET
	@Path("/start/{id}")
	public String start(@PathParam("id") String applicationId) {
		
		try {
			command.start(applicationId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "操作处理中...";
	}

	@GET
	@Path("/stop/{id}")
	public String stop(@PathParam("id") String applicationId) {
		
		try {
			command.stop(applicationId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "操作处理中...";
		
	}

	@GET
	@Path("/expand/{id}")
	public String expand(@PathParam("id") String applicationId) {
		
		try {
			command.expand(applicationId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "操作处理中...";
		
	}

	@GET
	@Path("/shrink/{id}")
	public String shrink(@PathParam("id") String applicationId) {
		
		
		try {
			command.shrink(applicationId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "操作处理中...";
	}


}
