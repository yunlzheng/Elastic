package com.cloud.elastic.health.resources.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.Path;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.elastic.commons.bean.User;
import com.cloud.elastic.commons.dao.UserDao;
import com.cloud.elastic.health.resources.UserResources;


@Path("/users")
public class UserResourcesImpl implements UserResources{

	@Autowired
	private UserDao userDao;
	
	@POST
	@Path("/login")
	public void login(@Context HttpServletRequest request,@Context HttpServletResponse response,MultivaluedMap<String, Object> formParams) throws IOException{
		
		String email = formParams.getFirst("email").toString();
		String password = formParams.getFirst("password").toString();
		
	
		
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

	public Response get(Integer pk) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Response save(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response update(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response delete(Integer pk) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response save(HttpServletRequest request,
			MultivaluedMap<String, Object> formParams) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
