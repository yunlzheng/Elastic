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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.icp.monitor.commons.bean.OpenAccount;
import com.icp.monitor.commons.dao.OpenAccountDao;


public class AuthorityFilter implements Filter{

	private Log log = LogFactory.getLog(AuthorityFilter.class);
	private List<String> noCheckURLList = new ArrayList<String>();
	private String openPage = null;
	private String sessionKey = null;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		
		ServletContext servletContext = filterConfig.getServletContext();
		openPage = servletContext.getInitParameter("openPage");
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
	
	public void destroy() {
		
		
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
	
		log.info("doFilter");
		
		if(!checkNotRequetFilter(request)){
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()); 
			String account = (String)request.getSession().getAttribute(sessionKey);
			log.info("login user "+account);
			
			if(account.indexOf("@")!=-1){
				
				OpenAccountDao dao = (OpenAccountDao)context.getBean("OpenAccountDao");
				List<OpenAccount> list = dao.list("from OpenAccount where account='"+account+"'");
				if(list==null||list.size()==0){
					
					log.info("Redirect To OpenPage");
					response.sendRedirect(openPage);
					return;
					
				}
			
			}
			
		}
		
		chain.doFilter(servletRequest, servletResponse);
	
	}

	private boolean checkNotRequetFilter(HttpServletRequest request){
		
		String uri = request.getServletPath()+(request.getPathInfo()==null?"":request.getPathInfo());
		return noCheckURLList.contains(uri);
		
	}
}
