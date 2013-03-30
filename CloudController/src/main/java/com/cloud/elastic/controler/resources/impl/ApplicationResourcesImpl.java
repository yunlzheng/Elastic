package com.cloud.elastic.controler.resources.impl;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.elastic.commons.bean.Application;
import com.cloud.elastic.commons.dao.ApplicationDao;
import com.cloud.elastic.commons.dao.UserDao;
import com.cloud.elastic.controler.resources.ApplicationResources;

@Path("/applications")
public class ApplicationResourcesImpl implements ApplicationResources{

	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private UserDao userDao;
	
	@GET
	@PathParam("/{id}")
	public Response get(@PathParam("id") Integer id) {
		
		
		Application application = null;
		
		application= applicationDao.get(id);
		
		
		return Response.ok(application, MediaType.APPLICATION_JSON).build();

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(Application entity) {
		
		try{
			
			applicationDao.save(entity);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();
		
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(Application entity) {
		
		applicationDao.update(entity);
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();
		
	}
	
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") Integer id) {
		
		applicationDao.deleteByKey(id);  
		return Response.ok().build();
		
	}

	@GET
	@Path("/{offset}/{length}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getListForPage(@PathParam("offset") int offset, @PathParam("length") int length) {
		
		DetachedCriteria criteria=null;
		List<Application> result = applicationDao.findByCriteria(criteria, offset, length);
		return Response.ok(result,MediaType.APPLICATION_JSON).build();
		
	}

	public Response getPageCount(int offset, int length) {
		
		DetachedCriteria criteria=null;
		int rowcount = applicationDao.getRowCount(criteria);
		return Response.ok(rowcount,MediaType.APPLICATION_JSON).build();
	}

}
