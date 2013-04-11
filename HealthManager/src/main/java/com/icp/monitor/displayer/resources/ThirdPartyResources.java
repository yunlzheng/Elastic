package com.icp.monitor.displayer.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.icp.monitor.commons.bean.LBStatus;
import com.icp.monitor.commons.otherbean.Cluster;

@Path("/thirdparty")
public interface ThirdPartyResources {

	/**
	 * 获取所有集群列表
	 * */
	@GET
	@Path("/clusters")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cluster> list();
	
	/**
	 * 获取特定IP的集群列表
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/clusters/{pk}")
	public Cluster get(@PathParam("pk") int pk);
	
	
	/**
	 * 获取制定用户下的集群列表
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/clusters/search/{username}")
	public List<Cluster> listByUser(@PathParam("username") String username);
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/vm/status/{id}")
	public Response getVMStatus(@PathParam("id") String id);
	
	/**
	 * 获取对应租户cluster下的与check相似的记录
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/clusters/check/{name}/{check}")
	public List<Cluster> checkList(@PathParam("name") String name,@PathParam("check") String check);
	
	
	/**
	 * 获取管理员cluster下的与check相似的记录
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/clusters/check/{check}")
	public List<Cluster> checkAdminList(@PathParam("check") String check);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/clusters/app/{name}/{check}")
	public List<Cluster> checkAppList(@PathParam("name") String name,@PathParam("check") String check);
	
	/**获取租户的私有集群*/
	
	/**
	 * 获取集群包含的虚拟机列表
	 * */
	@GET
	@Path("/vm/search/{clusterid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVMListByCluster(@PathParam("clusterid") String id);
	
	/**
	 * 获取所有服务器的运行状态
	 * */
	@GET
	@Path("/lb/status")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<LBStatus> getVMStatusList();
	
	
	
}
