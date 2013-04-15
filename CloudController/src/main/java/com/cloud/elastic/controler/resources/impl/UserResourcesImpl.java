package com.cloud.elastic.controler.resources.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.elastic.commons.bean.User;
import com.cloud.elastic.commons.dao.UserDao;
import com.cloud.elastic.controler.resources.UserResources;

@Path("/users")
public class UserResourcesImpl implements UserResources{

	@Autowired
	private UserDao userDao;
	
	@POST
	@Path("/login")
	public void login(@Context HttpServletRequest request,@Context HttpServletResponse response,MultivaluedMap<String, Object> formParams) throws IOException{
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		
		User templatesUser = new User();
		templatesUser.setEmail(email);
		templatesUser.setPassword(password);
		
		List<User> users = userDao.findEqualByEntity(templatesUser, new String[]{"email","password"});
		
		
		if(users.size()>0){
			
			request.getSession().setAttribute("User", users.get(0));
			response.sendRedirect(request.getContextPath()+"/jsp/home.jsp");
			return;
			
		}else{
			
			response.sendRedirect(request.getContextPath()+"/login.jsp#error");
			return;
			
		}
		
		
	}
	
	@GET
	@Path("/logout")
	public void logout(@Context HttpServletRequest request,@Context HttpServletResponse response) throws IOException{
		
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		return;
			
		
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Integer id) {
		
		return Response.ok(userDao.get(id),MediaType.APPLICATION_JSON).build();
		
	}

	@Path("/json")
	@POST
	public Response save(User entity) {
		
		userDao.save(entity);
		return Response.ok(entity,MediaType.APPLICATION_JSON).build();
	}
	
	

	@Path("/form")
	@POST
	public Response save(MultivaluedMap<String, Object> formParams,@Context HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@PUT
	public Response update(User entity) {
		
		return null;
	}

	@DELETE
	public Response delete(Integer entity) {
		
		return null;
	}

	@GET
	public Response getListForPage(int offset, int length) {
		
		
		return null;
	}

	@GET
	public Response getPageCount(int offset, int length) {
		
		return null;
	}

}
