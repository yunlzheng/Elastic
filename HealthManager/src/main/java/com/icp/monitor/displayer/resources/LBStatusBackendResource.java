package com.icp.monitor.displayer.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.icp.monitor.commons.bean.LBStatusBackend;

@Path("/lbstatusbackend")
public interface LBStatusBackendResource {

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public LBStatusBackend get(@PathParam("id") int id);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<LBStatusBackend> list();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/key1/{pxname}")
	public List<LBStatusBackend> listByPxname(@PathParam("pxname") String pxname);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/key2/{pxname}/{appname}")
	public List<LBStatusBackend> getLastStatusBypxname(@PathParam("pxname") String pxname,@PathParam("appname") String appname);
	
	
}
