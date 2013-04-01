package com.cloud.elastic.controler.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cloud.elastic.commons.bean.User;
import com.cloud.elastic.commons.dao.UserDao;

public class Login extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		UserDao userDao = applicationContext.getBean(UserDao.class);
		
		List<User> list = userDao.find("from User where email='"+email+"' and password='"+password+"'");
		if(list!=null&&list.size()!=0){
			User user = list.get(0);
			req.getSession().setAttribute("User", user);
		}
		
			
		
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		
		
		super.init(config);
		
	}
	
	
	
}
