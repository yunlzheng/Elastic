package com.icp.monitor.displayer.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.icp.monitor.commons.bean.LBStatus;

@Path("/lbstatus")
public interface LBStatusResource {

	/**
	 * 根据编号获取LB状态信息
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public LBStatus get(@PathParam("id") int id);
	
	/**
	 * 获取LB所有的监控信息
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<LBStatus> list();
	
	/**
	 * 获取所有paxname ={paxname}的lb监控日志信息
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/pxnames/{paxname}")
	public List<LBStatus> listByPxname(@PathParam("paxname") String pxname);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/pxnames/{paxname}/{begin}/{end}")
	public List<LBStatus> listByPaxnameOrderByDate(@PathParam("paxname") String paxname,@PathParam("begin") String begin,@PathParam("end") String end);
	
	/**
	 * 返回特定集群下的所有服务器列表
	 * */
	@GET
	@Path("/key1/{pxname}")
	public List<LBStatus> listSvname(@PathParam("pxname") String pxname);
	
}
