package com.cloud.elastic.health.resources.impl;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.elastic.commons.dao.TomcatDao;
import com.cloud.elastic.commons.monitor.bean.Tomcat;
import com.cloud.elastic.health.resources.TomcatResources;

@Path("/tomcat")
public class TomcatResourcesImpl implements TomcatResources{

	@Autowired
	private TomcatDao tomcatDao;
	
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

	@PUT
	public Response update(Tomcat entity) {
		tomcatDao.update(entity);
		return Response.ok(entity,MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	public Response delete(String pk) {
		tomcatDao.deleteByKey(pk);
		return Response.ok("success",MediaType.APPLICATION_JSON).build();
	}

}
