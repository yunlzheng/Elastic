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
import javax.ws.rs.core.MediaType;


import com.icp.monitor.commons.bean.Member;
import com.icp.monitor.commons.bean.Response;

@Path("/members")
public interface MemberResources {

	/**
	 * 获取所有预警成员列表
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Member> list(@Context HttpServletRequest request);

	@GET
	@Path("/key1/{useraccount}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Member> list(@Context HttpServletRequest request,@PathParam("useraccount") String userAccount);
	
	/**
	 * 获取为监控该IP地址的预警人员列表
	 * */
	@GET
	@Path("/key2/{ip}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Member> listNoConfig(@Context HttpServletRequest request,@PathParam("ip") String ip);
	
	@GET
	@Path("/key3/{ip}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Member> listConfig(@Context HttpServletRequest request,@PathParam("ip") String ip);
	
	@POST
	@Path("/{check}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Member> checkList(@Context HttpServletRequest request,@PathParam("check") String check);
	
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Member get(@PathParam("id") int id);
	


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Member add(@Context HttpServletRequest request,@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("tell") String tell,
			@FormParam("creater") String creater);

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Member update(@FormParam("id") int id, @FormParam("name") String name,
			@FormParam("email") String email, @FormParam("tell") String tell);

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id);

}
