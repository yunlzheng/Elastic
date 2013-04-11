package com.icp.monitor.displayer.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.icp.monitor.commons.bean.ServerConfig;
import com.icp.monitor.commons.bean.Response;

@Path("/monitorconfig")
public interface ServerConfigResources {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ServerConfig> list(@Context HttpServletRequest request);
	
	@GET
	@Path("/{ip}")
	@Produces(MediaType.APPLICATION_JSON)
	public ServerConfig get(@Context HttpServletRequest request,@PathParam("ip") String ip);
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ServerConfig add(@Context HttpServletRequest request,@FormParam("ip") String ip, @FormParam("showCpu") boolean showCpu,
			@FormParam("showMem") boolean showMem, @FormParam("showIo") boolean showIo, 
			@FormParam("showDisk") boolean showDisk,@FormParam("deyling") int deyling);
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public ServerConfig update(@FormParam("ip") String ip, @FormParam("showCpu") boolean showCpu,
			@FormParam("showMem") boolean showMem, @FormParam("showIo") boolean showIo, 
			@FormParam("showDisk") boolean showDisk,@FormParam("deyling") int deyling);
	
	@DELETE
	@Path("/{ip}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("ip") String ip);
	
}
