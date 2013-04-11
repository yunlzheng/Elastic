package com.icp.monitor.displayer.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.icp.monitor.commons.bean.Configuration;

@Path("/configuration")
public interface ConfigurationResources {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Configuration getSystemConfiguration();
	
}
