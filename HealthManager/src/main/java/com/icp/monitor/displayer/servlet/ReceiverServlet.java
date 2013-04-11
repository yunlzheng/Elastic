package com.icp.monitor.displayer.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.icp.monitor.displayer.thread.manager.ThreadManager;

public class ReceiverServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());	
		ThreadManager tm = (ThreadManager)applicationContext.getBean("TenantVMStatusThreadManager");
		tm.start();
		
	}
	
}
