package com.icp.monitor.displayer.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.icp.monitor.commons.bean.AlertMessage;


@Path("/alertmessages")
public interface AlertMessageResource {

	/**
	 * 查询所有报警信息
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AlertMessage> list(@Context HttpServletRequest request);

	/**
	 * 根据编号查询报警信息
	 * @param 编号
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public AlertMessage get(@PathParam("id") int id);

	/**
	 * 分页查询数据库
	 * @param offset 数据头指针
	 * @param length 数据长度
	 * @param eqs 查询全等条件 key:value 
	 * @param likes 查询相似条件 key:value
	 * 条件查询支持字段 {ip,message，type,memname,username,joinTime}
	 * */
	@GET
	@Path("/{offset}/{len}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AlertMessage> listByPage(
			@Context HttpServletRequest request,
			@PathParam("offset") int offset,
			@PathParam("len") int length,
			@QueryParam("eqs") String[] eqs,@QueryParam("likes") String[] likes);
	
	/**
	 * 根据服务器IP地址查询对应的所有报警信息
	 * @param 服务器IP地址
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/key1/{ip}")
	public List<AlertMessage> getByIp(@PathParam("ip") String ip);
	
	/**
	 * 根据用户名查询该用户下的所有同志
	 * @param username 用户名
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/key2/{username}")
	public List<AlertMessage> getByUserName(@PathParam("username") String username);
	
	/**
	 * 查询预警同志账户名称查询所有监控信息
	 * @param memname 预警账户名
	 * */
	@GET
	@Path("/key3/{memname}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AlertMessage> getByMemName(@PathParam("memname") String memname);

}
