package com.icp.monitor.displayer.servlet;

import java.io.IOException;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class IdentityServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String callback = null;
	private String sessionKey = null;
	
	
	private String consumerUrl = null;
	private String IdpUrl = null;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String type = req.getParameter("type");
		
		HttpSession session = req.getSession();
		String userAccount = (String) session.getAttribute("user");
	
		String url = null;
		
		//退出登录
		if (type!=null&&type.equals("logout")) {
			
		
			resp.sendRedirect(url);
			
		}else{
		
			System.out.println("send is "+url);
			resp.sendRedirect(url);
		
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String responseMessage = req.getParameter("SAMLResponse");
		HttpSession session = req.getSession();
		
		
			
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		
		ServletContext servletContext = config.getServletContext();
		sessionKey = servletContext.getInitParameter("sessionKey");
		consumerUrl = servletContext.getInitParameter("redirectURL");
		IdpUrl = servletContext.getInitParameter("IdpUrl");
		callback = servletContext.getInitParameter("openPage");
		

	}

}
