package com.icp.monitor.displayer.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
public interface UserResource {

	@GET
	@Produces("text/html")
	public String getLoginAccount(@Context HttpServletRequest request);
	
	
}
