package com.cloud.elastic.controler.resources.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;


import com.cloud.elastic.commons.dao.ApplicationDao;
import com.cloud.elastic.controler.command.Command;
import com.cloud.elastic.controller.CloudController;

@Path("/cloudcontroller")
public class CloudControllerImpl implements CloudController{

	
	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private Command command;
	
	@GET
	@Path("/deploy/{id}")
	public String deploy(@PathParam("id") String applicationId) {
		

		
		command.deploy(applicationId);
		
		return "操作处理中...";
		
		//调用部署接口
		
	}

	@GET
	@Path("/undeploy/{id}")
	public String undeploy(@PathParam("id") String applicationId) {
		
		
		command.undeploy(applicationId);
		
		return "操作处理中...";
	}

	@GET
	@Path("/start/{id}")
	public String start(@PathParam("id") String applicationId) {
		
		
		command.start(applicationId);
		
		return "操作处理中...";
	}

	@GET
	@Path("/stop/{id}")
	public String stop(@PathParam("id") String applicationId) {
		
		
		command.stop(applicationId);
		
		return "操作处理中...";
		
	}

	@GET
	@Path("/expand/{id}")
	public String expand(@PathParam("id") String applicationId) {
		
		
		command.expand(applicationId);
		
		return "操作处理中...";
		
	}

	@GET
	@Path("/shrink/{id}")
	public String shrink(@PathParam("id") String applicationId) {
		
		
		command.shrink(applicationId);
		return "操作处理中...";
	}


}
