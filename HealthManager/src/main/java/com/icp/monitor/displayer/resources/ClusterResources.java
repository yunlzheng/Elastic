package com.icp.monitor.displayer.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/clusters")
public interface ClusterResources {

	/**
	 * 获取用户私有的集群列表
	 * */
	@GET
	@Path("/private")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPrivateClusters(@Context HttpServletRequest request);
	
	/**
	 * 获取租户私有的集群列表,树结构
	 * */
	@GET
	@Path("/private/tree")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPrivateClustersTree(@Context HttpServletRequest request);
	
	@GET
	@Path("private/user/tree")
	public Response getPrivateClustersByUserTree(@Context HttpServletRequest request);
	
	/**获取系统共有环境集群列表里，树结构*/
	@GET
	@Path("/public/tree")
	public Response getPublicClustersTree(@Context HttpServletRequest request);
	
	/**
	 * 获取系统共有环境集群列表里
	 * 成功启动状态为9
	 * */
	@GET
	@Path("/public/")
	public Response getPublicClusters(@Context HttpServletRequest request);
	
	
	
	
	
	
}
