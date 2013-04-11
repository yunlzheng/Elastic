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

import com.icp.monitor.commons.bean.ExceptionMessage;
import com.icp.monitor.commons.bean.Response;

@Path("/exception")
public interface ExceptionResources {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ExceptionMessage> list(@Context HttpServletRequest request);
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ExceptionMessage get(@PathParam("id") int id);
	
	/**
	 * 分页查询数据库
	 * @param offset 数据头指针
	 * @param length 数据长度
	 * @param eqs 查询全等条件 key:value 
	 * @param likes 查询相似条件 key:value
	 * 条件查询支持字段 {name,message，creater}
	 * 
	 * */
	
	@GET
	@Path("/{offset}/{len}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ExceptionMessage> listByPage(
			@Context HttpServletRequest request,
			@PathParam("offset") int offset,
			@PathParam("len") int length,
			@QueryParam("eqs") String[] eqs,@QueryParam("likes") String[] likes);
	
	
	@GET
	@Path("/count")
	@Produces(MediaType.TEXT_HTML)
	public Long count(@QueryParam("eqs") String[] eqs,@QueryParam("likes") String[] likes);
	
	/**
	 * 添加异常配置
	 * 
	 * */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ExceptionMessage add(
			@FormParam("type") String type,
			@FormParam("name") String name,
			@FormParam("memname") String memname,
			@FormParam("level") String level, @FormParam("message") String message,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime);
	
	/**
	 * 修改异常信息
	 * */
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ExceptionMessage update(@FormParam("type") String type,
			@FormParam("name") String name,
			@FormParam("memname") String memname,
			@FormParam("level") String level, @FormParam("message") String message,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime);
	

	/**
	 * 删除异常信息
	 */
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id);
	
}
