package com.cloud.elastic.health.resources;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public interface GenericResources <T,PK> {

	public Response get(PK pk);
	
	public Response loadAll();
	
	public Response save(HttpServletRequest request,MultivaluedMap<String, Object> formParams);
	
	public Response save(T entity);
	
	public Response update(T entity);
	
	public Response delete(PK pk);
	
}
