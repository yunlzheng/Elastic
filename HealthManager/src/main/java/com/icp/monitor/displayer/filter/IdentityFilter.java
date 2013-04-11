package com.icp.monitor.displayer.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IdentityFilter implements Filter{

	private Log log = LogFactory.getLog("IdentityFilter");
	
	private List<String> noCheckURLList = new ArrayList<String>();
	private String redirectURL = null;
	private String sessionKey = null;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		
		ServletContext servletContext = filterConfig.getServletContext();
		redirectURL = servletContext.getInitParameter("redirectURL");
		sessionKey = servletContext.getInitParameter("sessionKey");
		
		String noCheckURLListStr = filterConfig.getInitParameter("noCheckURLList");
		if(noCheckURLListStr!=null){
			
			StringTokenizer st = new StringTokenizer(noCheckURLListStr,";");
			noCheckURLList.clear();
			while(st.hasMoreTokens()){
				noCheckURLList.add(st.nextToken());
			}
		}
		
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		
		if(sessionKey==null){
		
			log.info("session key is null");
			//System.out.println("session key is null");
			chain.doFilter(request, response);
			return ;
		}
		
		if(!checkNotRequetFilter(request)){
			
			String account = (String) session.getAttribute(sessionKey);
			if(account==null){
				//用户为登陆跳转到登陆界面
				
				response.sendRedirect(redirectURL);
				return;
			}
		}
		chain.doFilter(request, response);
	
	}
	
	
	private boolean checkNotRequetFilter(HttpServletRequest request){
		
		String uri = request.getServletPath()+(request.getPathInfo()==null?"":request.getPathInfo());
		return noCheckURLList.contains(uri);
		
	}

	public void destroy() {
		
		
	}

}
