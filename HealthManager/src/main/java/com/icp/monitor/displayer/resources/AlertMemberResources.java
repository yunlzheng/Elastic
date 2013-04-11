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

import com.icp.monitor.commons.bean.AlertMember;
import com.icp.monitor.commons.bean.Response;

@Path("/alertmembers")
public interface AlertMemberResources {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AlertMember> list();
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public AlertMember get(@PathParam("id") int id);
	

	/**
	 * 批量添加或者修改服务器监控人员信息
	 * */
	@POST
	@Path("/addBatch")
	public List<AlertMember> addAlertMemberBatch(@Context HttpServletRequest request,@QueryParam("ip") String ip,@QueryParam("data") String[] datas);
	
	/**
	 * 根据通知方式保存监控人员信息
	 * */
	@POST
	@Path("/way")
	@Produces(MediaType.APPLICATION_JSON)
	public AlertMember addByWay(@Context HttpServletRequest request,@FormParam("ip") String ip,@FormParam("mid") int mid,@FormParam("way") int way);
	
	/**
	 * 添加服务器预警人员配置
	 * @param ip 预警的服务器ip地址
	 * @param emial 报警时是否通知邮箱
	 * @param tell 报警时是否通知断行
	 * @param mid 对应的成员编号
	 * */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public AlertMember add(
			@FormParam("ip") String ip, @FormParam("email") boolean email,
			@FormParam("tell") boolean tell,@FormParam("mid") int mid);
	
	/**
	 * 修改服务器预警人员配置
	 * @param id 预警通知设置编号
	 * @param ip 预警的服务器ip地址
	 * @param emial 报警时是否通知邮箱
	 * @param tell 报警时是否通知断行
	 * @param mid 对应的成员编号
	 * */
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public AlertMember update(@FormParam("id") int id,
			@FormParam("ip") String ip, @FormParam("email") boolean email,
			@FormParam("tell") boolean tell,@FormParam("mid") int mid);
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id);
	
}
