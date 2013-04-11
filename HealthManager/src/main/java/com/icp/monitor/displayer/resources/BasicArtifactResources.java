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


import com.icp.monitor.commons.otherbean.BasicArtifact;
import com.icp.monitor.commons.statistics.ArtifactInfo;

@Path("/artifacts")
public interface BasicArtifactResources {

	/**
	 * 获取特定用户下所有的应用
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BasicArtifact> listAll(@Context HttpServletRequest request);
	
	/**获取运行中的所有应用信息*/
	@GET
	@Path("/runing")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ArtifactInfo> listDeploySuccess(@Context HttpServletRequest request);
	
	/**获取测试中的所有应用信息*/
	@GET
	@Path("/testing")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ArtifactInfo> listTestingSuccess(@Context HttpServletRequest request);
	
	/**
	 * 获取用户私有环境中测试和运行的程序列表
	 * */
	@GET
	@Path("/pripub/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ArtifactInfo> listPublicOrPrivate(@Context HttpServletRequest request,@PathParam("key") int type);
	
	/**
	 * 查询租户下的应用信息
	 * */
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ArtifactInfo> searchArtifact(@Context HttpServletRequest request,@QueryParam("appname") String appname);
	
	@GET
	@Path("/clusters/test/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BasicArtifact> listTestByCluster(@Context HttpServletRequest request,@PathParam("id") int clusterid);
	
	@GET
	@Path("/clusters/deploy/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BasicArtifact> listDeployByCluster(@Context HttpServletRequest request,@PathParam("id") int clusterid);
	
	
	
}
