package com.cloud.elastic.controler.resources.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.elastic.commons.bean.Server;
import com.cloud.elastic.commons.dao.ServerDao;
import com.cloud.elastic.controler.resources.ServerResources;

@Path("/servers")
public class ServerResourcesImpl implements ServerResources{

	@Autowired
	private ServerDao serverDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response loadAll() {
		
		return Response.ok(serverDao.loadAll(),MediaType.APPLICATION_JSON).build();
		
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Integer id) {
		
		return Response.ok(serverDao.get(id),MediaType.APPLICATION_JSON).build();
	}

	@POST
	public Response save(Server entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@POST
	@Path("/form")
	public Response save(MultivaluedMap<String, Object> formParams,@Context HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@PUT
	public Response update(Server entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@DELETE
	public Response delete(Integer entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	public Response getListForPage(int offset, int length) {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	public Response getPageCount(int offset, int length) {
		// TODO Auto-generated method stub
		return null;
	}

}
