package com.cloud.elastic.controler.resources.impl;
/**
 * 演示注意事项
 * 1，由于单机原因，必须事先建好Runtimes实例，并添加到数据库
 * */
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.cloud.elastic.commons.bean.Application;
import com.cloud.elastic.commons.bean.User;


import com.cloud.elastic.commons.dao.ApplicationDao;
import com.cloud.elastic.commons.dao.RuntimeDao;
import com.cloud.elastic.commons.dao.UserDao;
import com.cloud.elastic.commons.util.NexusUtil;
import com.cloud.elastic.controler.command.Command;
import com.cloud.elastic.controler.resources.ApplicationResources;


@Path("/applications")
public class ApplicationResourcesImpl implements ApplicationResources{

	private Log log = LogFactory.getLog(ApplicationResources.class);
	
	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RuntimeDao runtimeDao;
	
	@Autowired
	private NexusUtil nexusUtil;
	
	@Autowired
	private Command command;
	
	@Value("#{config['app.domain']}")
	private String appDomain;

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response loadAll() {
		
		List<Application> list = applicationDao.loadAll();
		return Response.ok(list,MediaType.APPLICATION_JSON).build();
		
	}
	
	@GET
	@Path("/{id}")
	public Response get(@PathParam("id") String id) {
		
		
		Application application = null;
		application= applicationDao.get(id);
		return Response.ok(application, MediaType.APPLICATION_JSON).build();

	}

	@POST
	@Path("/form")
	public Response save(MultivaluedMap<String, Object> formParams,
			@Context HttpServletRequest request) {
		
		Application application = new Application();
		
		String name = (String) formParams.getFirst("name");
		if(name.equals("")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddss");
			name = "app_"+sdf.format(new Date());
		}
		application.setName(name);
		application.setUrl(formParams.getFirst("url").toString()+appDomain);
		application.setMaxMemory(Integer.parseInt(formParams.getFirst("maxMemory").toString()));
		application.setMinMemory(Integer.parseInt(formParams.getFirst("minMemory").toString()));
		User user = (User) request.getSession().getAttribute("User");
		
		if(user==null){
			
			log.info("Session->User null");
			
		}
		
		
		String uua = (String) request.getSession().getAttribute("runit");
		
		if(uua==null){
			
			return Response.ok("请先上传应用").build();
			
		}
		
		try {
			
			String repositoryUrl = nexusUtil.upload(uua);
			//To do Maven Upload
			
			application.setRepositoryUrl(repositoryUrl);
			application.setHealth(Application.Health.UNBINDED.getHealth());
			
			application.setUser(user);
			application.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			applicationDao.save(application);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			return Response.ok("应用构建失败").build();
			
		}
	
		return Response.ok(application,MediaType.APPLICATION_JSON).build();
	
	}
	
	@POST
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(Application entity) {
		
		try{
			
			applicationDao.save(entity);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();
		
	}
	

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(Application entity) {
		
		applicationDao.update(entity);
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();
		
	}
	
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") String id) {
		
		applicationDao.deleteByKey(id);  
		command.delete(id);
		return Response.ok().build();
		
	}

	@GET
	@Path("/{offset}/{length}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getListForPage(@PathParam("offset") int offset, @PathParam("length") int length) {
		
		DetachedCriteria criteria=null;
		List<Application> result = applicationDao.findByCriteria(criteria, offset, length);
		return Response.ok(result,MediaType.APPLICATION_JSON).build();
		
	}

	@GET
	@Path("rowcount/{offset}/{length}")
	public Response getPageCount(int offset, int length) {
		
		DetachedCriteria criteria=null;
		int rowcount = applicationDao.getRowCount(criteria);
		return Response.ok(rowcount,MediaType.APPLICATION_JSON).build();

	}

}
