package com.cloud.elastic.controler.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

/**
 * 资源接口
 * */
public interface AbstractResources<T,PK> {

	@GET
	public Response get(PK id);
	
	@POST
	public Response save(T entity);
	
	@PUT
	public Response update(T entity);
	
	@DELETE
	public Response delete(PK entity);
	
	@GET
	public Response getListForPage(int offset,int length);
	
	@GET
	public Response getPageCount(int offset,int length);
	
}
