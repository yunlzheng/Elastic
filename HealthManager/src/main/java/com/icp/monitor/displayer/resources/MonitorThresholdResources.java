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

import com.icp.monitor.commons.bean.MonitorThreshold;
import com.icp.monitor.commons.bean.Response;

@Path("/monitorthreshold")
public interface MonitorThresholdResources {

	@GET
	@Produces("text/json")
	public List<MonitorThreshold> list();

	@GET
	@Path("/{ip}")
	@Produces("text/json")
	public MonitorThreshold get(@PathParam("ip") String ip);

	@POST
	@Produces("text/json")
	public MonitorThreshold add(@Context HttpServletRequest request,@FormParam("ip") String ip,
			@FormParam("cpuThreshlod") String cpuThreshlod,
			@FormParam("memThreshlod") String memThreshlod,
			@FormParam("ioThreshlod") String ioThreshlod,
			@FormParam("diskThreshlod") String diskThreshlod);

	@PUT
	@Path("/{ip}")
	@Produces("text/json")
	public MonitorThreshold update(@Context HttpServletRequest request,@FormParam("ip") String ip,
			@FormParam("cpuThreshlod") String cpuThreshlod,
			@FormParam("memThreshlod") String memThreshlod,
			@FormParam("ioThreshlod") String ioThreshlod,
			@FormParam("diskThreshlod") String diskThreshlod);

	@DELETE
	@Path("/{ip}")
	@Produces("text/json")
	public Response delete(@PathParam("ip") String  ip);
}
