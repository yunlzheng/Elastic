package com.icp.monitor.displayer.resources;



import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/combin")
public interface CombinationResources {

	/**
	 * 获取特定虚拟机的正常异常比例
	 * */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/exception/scale")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVMExceptionScale(@QueryParam("ip") String ip);
	
	/**
	 * 查询租户下异常接口比例
	 * */
	@GET
	@Path("/exception/globalscale")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExceptionScale(@Context HttpServletRequest request);
	
	/**
	 * 获取租户下监控项目的统计信息
	 * */
	@GET
	@Path("/tenant/exception/cate/scale")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTenantExceptionScale(@Context HttpServletRequest request);
	
	/**
	 * 获取用户下监控项目的统计信息
	 * */
	@GET
	@Path("/user/exception/cate/scale")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserExceptionScale(@Context HttpServletRequest request);
	
	/**
	 * 统计租户下各个预警人员的报警统计信息
	 * */
	@GET
	@Path("/alertstatistics")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTenantAlertStatistics(@Context HttpServletRequest request);
	
	@GET
	@Path("/alertscale")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAlertCountStatistics(@Context HttpServletRequest request);
	
	/**
	 * 获取base页面的正常与异常比例
	 */
	@GET
	@Path("/base/scale")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBaseScale(@Context HttpServletRequest request);
	
	
	
}
