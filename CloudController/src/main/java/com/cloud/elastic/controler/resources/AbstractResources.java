package com.cloud.elastic.controler.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 资源接口
 * */
public interface AbstractResources<T,PK> {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(PK id);
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(T obj);
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(T obj);
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(PK id);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListForPage(int offset,int length);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPageCount(int offset,int length);
	
}
