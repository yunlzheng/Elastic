package com.cloud.elastic.controler.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cloud.elastic.commons.bean.User;
import com.cloud.elastic.commons.dao.UserDao;

public class LoginFilter implements Filter{

	private ApplicationContext applicationContext;
	private UserDao userDao;
	public void destroy() {
		
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		
		User tmp = (User) request.getSession().getAttribute("User");
		if(tmp==null){
			
			User user = userDao.get(1);
			request.getSession().setAttribute("User",user);
			
		}
		
		arg2.doFilter(request, response);
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		userDao = applicationContext.getBean(UserDao.class);
	}

}
