package com.cloud.elastic.controler.resources.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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

import com.cloud.elastic.commons.bean.User;
import com.cloud.elastic.commons.dao.UserDao;
import com.cloud.elastic.controler.resources.UserResources;

@Path("/users")
public class UserResourcesImpl implements UserResources{

	@Autowired
	private UserDao userDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Integer id) {
		
		return Response.ok(userDao.get(id),MediaType.APPLICATION_JSON).build();
		
	}

	@Path("/json")
	@POST
	public Response save(User entity) {
		
		userDao.save(entity);
		return Response.ok(entity,MediaType.APPLICATION_JSON).build();
	}
	
	

	@Path("/form")
	@POST
	public Response save(MultivaluedMap<String, Object> formParams,@Context HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@PUT
	public Response update(User entity) {
		
		return null;
	}

	@DELETE
	public Response delete(Integer entity) {
		
		return null;
	}

	@GET
	public Response getListForPage(int offset, int length) {
		
		
		return null;
	}

	@GET
	public Response getPageCount(int offset, int length) {
		
		return null;
	}

}
