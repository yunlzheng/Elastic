package com.icp.monitor.displayer.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/products")
public interface ProductsResources {

	/**
	 * 开通监控服务
	 * */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response openServices(@Context HttpServletRequest request,@FormParam("orderid") String orderid,@FormParam("username") String username);
	
}
