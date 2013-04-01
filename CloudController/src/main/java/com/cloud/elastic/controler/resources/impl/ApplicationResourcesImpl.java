package com.cloud.elastic.controler.resources.impl;

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
import com.cloud.elastic.commons.dao.UserDao;
import com.cloud.elastic.controler.resources.ApplicationResources;


@Path("/applications")
public class ApplicationResourcesImpl implements ApplicationResources{

	private Log log = LogFactory.getLog(ApplicationResources.class);
	
	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private UserDao userDao;
	
	@Value("#{config['app.domain']}")
	private String appDomain;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response loadAll() {
		
		return Response.ok(applicationDao.loadAll(),MediaType.APPLICATION_JSON).build();
		
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(MultivaluedMap<String, Object> formParams,
			@Context HttpServletRequest request) {
		
		Application application = new Application();

		application.setName(formParams.getFirst("name").toString());
		application.setUrl(formParams.getFirst("url").toString()+appDomain);
		application.setMaxMemory(Integer.parseInt(formParams.getFirst("maxMemory").toString()));
		application.setMinMemory(Integer.parseInt(formParams.getFirst("minMemory").toString()));
		User user = (User) request.getSession().getAttribute("User");
		
		if(user==null){
			
			log.info("Session->User null");
			
		}
		
		
		String uua = (String) request.getSession().getAttribute("runit");
		
		//To do Maven Upload
		
		application.setRepositoryUrl(uua);
		application.setHealth(Application.Health.UPLOADED.getHealth());
		
		application.setUser(user);
		
		applicationDao.save(application);
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
