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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.icp.monitor.commons.bean.MonitorLog;
import com.icp.monitor.commons.bean.Response;

@Path("/monitorlog")
public interface MonitorLogResources {

	@GET
	@Produces("text/json")
	public List<MonitorLog> list(@Context HttpServletRequest request);
	
	@GET
	@Path("/search/{ip}")
	@Produces("text/json")
	public List<MonitorLog> getByIp(@Context HttpServletRequest request,@PathParam("ip") String ip);
	
	@GET
	@Path("/search/{offset}/{len}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MonitorLog> searchByProperties(@Context HttpServletRequest request,@PathParam("offset") int offset,
			@PathParam("len") int len,@QueryParam("eqs") String[] eqs,@QueryParam("likes") String[] likes);
	
	@GET
	@Path("/{id}")
	@Produces("text/json")
	public MonitorLog get(@Context HttpServletRequest request,@PathParam("id") int id);
	
	/**
	 * 获去从 begin 到 end时间段的日志记录
	 * @param begin 起始时间
	 * @param end 终止时间
	 * */
	@GET
	@Produces("text/json")
	@Path("/searchdate/{ip}/{begin}/{end}")
	public List<MonitorLog> getByDate(@Context HttpServletRequest request,@PathParam("ip") String ip,@PathParam("begin") String begin,@PathParam("end") String end);
	
	/**
	 * 根据日期模式获取数据  1 最近一天  2 最近一周 3 最近一月
	 * */
	@GET
	@Produces("text/json")
	@Path("/searchdate/{ip}/{mode}")
	public List<MonitorLog> getByMode(@Context HttpServletRequest request,@PathParam("ip") String ip, @PathParam("mode") int mode);
	
	@POST
	@Produces("text/json")
	public MonitorLog add(@Context HttpServletRequest request,@FormParam("ip") String ip, @FormParam("cpu") String cpu,
			@FormParam("mem") String mem, @FormParam("io") String io, @FormParam("disk") String disk);
	
	@PUT
	@Produces("text/json")
	public MonitorLog update(@Context HttpServletRequest request,@FormParam("id") String id, @FormParam("ip") String ip, @FormParam("cpu") String cpu,
			@FormParam("mem") String mem, @FormParam("io") String io, @FormParam("disk") String disk);
	
	@DELETE
	@Path("/{id}")
	@Produces("text/json")
	public Response delete(@Context HttpServletRequest request,@PathParam("id") int id);
	
	
	
}
