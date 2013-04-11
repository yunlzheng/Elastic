package com.icp.monitor.displayer.servlet;

import java.util.Timer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class LBStatusCollectorServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
	
		super.init(config);
		
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());	
		
		int delay = Integer.parseInt(config.getInitParameter("delay"));
		int period = Integer.parseInt(config.getInitParameter("period"));
		Timer timer = new Timer();
		LBStatusCollectTimeTask lBStatusCollectTimeTask = (LBStatusCollectTimeTask) applicationContext.getBean("LBStatusCollectTimeTask");
		timer.schedule(lBStatusCollectTimeTask, delay,period);
		
		
	}
	
}
