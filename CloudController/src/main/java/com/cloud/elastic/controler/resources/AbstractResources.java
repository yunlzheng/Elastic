package com.cloud.elastic.controler.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * 资源接口
 * */
public interface AbstractResources<T,PK> {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response loadAll();
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(PK id);
	
	@Path("/json")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(T entity);
	
	@Path("/form")
	@POST
	public Response save(MultivaluedMap<String, Object> formParams,HttpServletRequest request);
	
	@PUT
	public Response update(T entity);
	
	@DELETE
	public Response delete(PK entity);
	
	@GET
	@Path("/{offset}/{length}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListForPage(int offset,int length);
	
	@GET
	@Path("rowcount/{offset}/{length}")
	public Response getPageCount(int offset,int length);
	
}
