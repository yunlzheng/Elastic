package com.cloud.elastic.health.resources.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.elastic.commons.bean.User;
import com.cloud.elastic.commons.dao.TomcatDao;
import com.cloud.elastic.commons.dao.TomcatFlowDao;
import com.cloud.elastic.commons.dao.TomcatJvmMemoryDao;
import com.cloud.elastic.commons.dao.TomcatThreadDao;
import com.cloud.elastic.commons.monitor.bean.Tomcat;
import com.cloud.elastic.commons.monitor.bean.TomcatFlow;
import com.cloud.elastic.commons.monitor.bean.TomcatJvmMemory;
import com.cloud.elastic.commons.monitor.bean.TomcatThread;
import com.cloud.elastic.health.resources.TomcatResources;

@Path("/tomcat")
public class TomcatResourcesImpl implements TomcatResources{

	@Autowired
	private TomcatDao tomcatDao;
	
	@Autowired
	private TomcatJvmMemoryDao tomcatJvmMemoryDao;
	
	@Autowired
	private TomcatFlowDao tomcatFlowDao;
	
	@Autowired
	private TomcatThreadDao tomcatThreadDao;
	
	@GET
	@Path("/{pk}")
	public Response get(@PathParam("pk") String pk) {
		
		return Response.ok(tomcatDao.get(pk),MediaType.APPLICATION_JSON).build();
	}

	@GET
	public Response loadAll() {
		// TODO Auto-generated method stub
		return Response.ok(tomcatDao.loadAll(),MediaType.APPLICATION_JSON).build();
	}

	@POST
	public Response save(Tomcat entity) {
		tomcatDao.save(entity);
		return Response.ok(entity,MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/form")
	public Response save(@Context HttpServletRequest request,MultivaluedMap<String, Object> formParams) {
		
		User user = (User) request.getSession().getAttribute("User");
		Tomcat tomcat = new Tomcat();
		tomcat.setUserId(user.getId());
		tomcat.setName((String)formParams.getFirst("name"));
		tomcat.setStatusPageUrl((String)formParams.getFirst("statusPageUrl"));
		tomcat.setTomcatUserName((String)formParams.getFirst("tomcatUserName"));
		tomcat.setTomcatPassword((String)formParams.getFirst("tomcatPassword"));
		tomcat.setServicesName((String)formParams.getFirst("servicesName"));
		tomcat.setIntervalTime(Integer.parseInt((String) formParams.getFirst("intervalTime")));
		tomcat.setCreateDate(new Date());
		tomcatDao.save(tomcat);
		return Response.ok(tomcat,MediaType.APPLICATION_JSON).build();
		
	}

	@PUT
	public Response update(Tomcat entity) {
		tomcatDao.update(entity);
		return Response.ok(entity,MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") String pk) {
		System.out.println(pk);
		
		
		tomcatDao.deleteByKey(pk);
		return Response.ok("success",MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/data/memory/1/{id}")
	public Response tomcatJvmMemory(@PathParam("id") String uuid){
		
		Tomcat tomcat = tomcatDao.get(uuid);
		
		TomcatJvmMemory templateMemory = new TomcatJvmMemory();
		templateMemory.setTomcat(tomcat);
		
		List<TomcatJvmMemory> list  = tomcatJvmMemoryDao.findEqualByEntity(templateMemory, new String[]{"tomcat"});
		
		List<TomcatJvmMemory> list2 = new ArrayList<TomcatJvmMemory>();
		for(int i=0;i<list.size();i++){
			
			if(uuid.equals(list.get(i).getTomcat().getUuid())){
				list2.add(list.get(i));
			}
			
			
		}
		
		return Response.ok(list2, MediaType.APPLICATION_JSON).build();
		
	}
	
	@GET
	@Path("/data/flow/1/{id}")
	public Response tomcatFlow(@PathParam("id") String uuid){
		
		
		Tomcat tomcat = tomcatDao.get(uuid);
		
		System.out.println(tomcat==null);
		
		TomcatFlow templateFlow = new TomcatFlow();
		templateFlow.setTomcat(tomcat);
		List<TomcatFlow> list  = tomcatFlowDao.findEqualByEntity(templateFlow, new String[]{"tomcat"});
		List<TomcatFlow> list2 = new ArrayList<TomcatFlow>();
		for(int i=0;i<list.size();i++){
			
			if(uuid.equals(list.get(i).getTomcat().getUuid())){
				list2.add(list.get(i));
			}
			
			
		}
		
		return Response.ok(list2, MediaType.APPLICATION_JSON).build();
		
	}
	
	@GET
	@Path("/data/thread/1/{id}")
	public Response tomcatThread(@PathParam("id") String uuid){
		
		Tomcat tomcat = tomcatDao.get(uuid);
		TomcatThread threadTemplate = new TomcatThread();
		threadTemplate.setTomcat(tomcat);
		List<TomcatThread> list = tomcatThreadDao.findEqualByEntity(threadTemplate, new String[]{"tomcat"});

		List<TomcatThread> list2 = new ArrayList<TomcatThread>();
		for(int i=0;i<list.size();i++){
			
			if(uuid.equals(list.get(i).getTomcat().getUuid())){
				list2.add(list.get(i));
			}
			
			
		}
		
		
		return Response.ok(list2, MediaType.APPLICATION_JSON).build();
		
	}


}
