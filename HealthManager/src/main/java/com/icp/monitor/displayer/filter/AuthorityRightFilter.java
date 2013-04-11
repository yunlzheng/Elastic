package com.icp.monitor.displayer.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorityRightFilter implements Filter{

	private String sessionKey = null;
	private String tenantHome = null;
	private String adminHome = null;
	
	public void init(FilterConfig filterConfig) throws ServletException {
	
		ServletContext servletContext = filterConfig.getServletContext();
		sessionKey = servletContext.getInitParameter("sessionKey");
		tenantHome = servletContext.getInitParameter("tenantHome");
		adminHome = servletContext.getInitParameter("adminHome");
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getServletPath()+(req.getPathInfo()==null?"":req.getPathInfo());
		
		String account = (String) req.getSession().getAttribute(sessionKey);
		//权限验证
		if(account.indexOf("@")==-1){
			//管理员
			if(uri.indexOf("tenant")!=-1){
				resp.sendRedirect(adminHome);
				return;
			}
			
		}else{
			
			//租户
			if(uri.indexOf("admin")!=-1){
				resp.sendRedirect(tenantHome);
				return;
			}
			
		}
		
		chain.doFilter(request, response);
		
	}

	public void destroy() {
		
		
	}

	
	
}
