package com.icp.monitor.displayer.resources.impl;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.AlertMember;
import com.icp.monitor.commons.bean.MonitorLog;
import com.icp.monitor.commons.bean.ServerConfig;
import com.icp.monitor.commons.bean.Response;
import com.icp.monitor.commons.dao.AlertMemberDao;
import com.icp.monitor.commons.dao.MonitorLogDao;
import com.icp.monitor.commons.dao.MonitorThresholdDao;
import com.icp.monitor.commons.dao.ServerConfigDao;
import com.icp.monitor.commons.dao.impl.ServerConfigDaoImpl;
import com.icp.monitor.commons.util.SystemUtil;
import com.icp.monitor.displayer.resources.ServerConfigResources;

public class ServerConfigResourcesImpl implements ServerConfigResources,
		ApplicationContextAware {

	private ApplicationContext context = null;
	private ServerConfigDao monitorConfigDao = null;
	private AlertMemberDao alertMemberDao = null;
	private MonitorLogDao serverLogDao = null;
	private MonitorThresholdDao monitorThresholdDao = null;
	

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
		monitorConfigDao = (ServerConfigDaoImpl) context.getBean("ServerConfigDao");
		monitorThresholdDao = (MonitorThresholdDao) context.getBean("MonitorThresholdDao");
		alertMemberDao = (AlertMemberDao) context.getBean("AlertMemberDao");
		serverLogDao = (MonitorLogDao) context.getBean("MonitorLogDao");
		
	}
	
	public List<ServerConfig> list(HttpServletRequest request) {

		String account = (String) request.getSession().getAttribute("user");
		if(account==null){account="guest@guest.com";}
		ServerConfigDao monitorConfigDao = (ServerConfigDaoImpl) context.getBean("ServerConfigDao");
		List<ServerConfig> list = monitorConfigDao.list("from ServerConfig where creater='"+account+"'");
		return list;

	}

	public ServerConfig get(HttpServletRequest request,String ip) {

		String account = (String) request.getSession().getAttribute("user");
		if(account==null){account="guest@guest.com";}
		List<ServerConfig> list = monitorConfigDao.list("from ServerConfig where ip='"+ip+"' and creater='"+account+"'");
		ServerConfig monitorConfig = null;
		if(list!=null&&list.size()>=1){
			
			monitorConfig = list.get(0);
			
		}
		
		return monitorConfig;

	}

	public ServerConfig add(HttpServletRequest request,String ip, boolean showCpu, boolean showMem,
			boolean showIo, boolean showDisk, int deyling) {

		String account = (String) request.getSession().getAttribute("user");
		if(account==null){account="guest@guest.com";}
		
		boolean isadmin = false;
		
		if(account.indexOf("@")==-1){
			isadmin = true;
		}

		
		ServerConfig monitorconfig = new ServerConfig();

		monitorconfig.setIp(ip);
		monitorconfig.setJoinTime(SystemUtil.getCurrentTime());
		monitorconfig.setDeyling(deyling);
		monitorconfig.setShowCpu(showCpu);
		monitorconfig.setShowMem(showMem);
		monitorconfig.setShowDisk(showDisk);
		monitorconfig.setShowIo(showIo);
		monitorconfig.setAdmin(isadmin);
		monitorconfig.setCreater(account);
		monitorConfigDao.add(monitorconfig);
		
		return monitorconfig;

	}

	public ServerConfig update(String ip, boolean showCpu, boolean showMem,
			boolean showIo, boolean showDisk, int deyling) {

	  
		ServerConfig monitorconfig = monitorConfigDao.get(ip);

		monitorconfig.setIp(ip);
		monitorconfig.setJoinTime(SystemUtil.getCurrentTime());
		monitorconfig.setShowCpu(showCpu);
		monitorconfig.setShowDisk(showDisk);
		monitorconfig.setShowIo(showIo);
		monitorconfig.setShowMem(showMem);
		monitorconfig.setJoinTime(SystemUtil.getCurrentTime());
		monitorconfig.setDeyling(deyling);

		monitorConfigDao.update(monitorconfig);
		return monitorconfig;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Response delete(String ip) {

		Response resp = new Response();
		try {

			monitorConfigDao.deleteById(ip);
			monitorThresholdDao.deleteById(ip);
			resp.setCode("200");
			resp.setStatus("success");
			
			Hashtable eqProperties = new Hashtable();
			eqProperties.put("ip", ip);
			eqProperties.put("admin", false);
			Hashtable likeProperties = new Hashtable();
			
			List<AlertMember> list = alertMemberDao.getList(eqProperties,likeProperties,null);
			for(int i=0;i<list.size();i++){
				
				alertMemberDao.delete(list.get(i));
				
			}
			
			eqProperties.clear();
			likeProperties.clear();
			
			eqProperties.put("ip", ip);
			eqProperties.put("admin", false);
			
			List<MonitorLog> list2 = serverLogDao.getList(eqProperties, likeProperties,null);
			for(int i=0;i<list2.size();i++){
				
				serverLogDao.delete(list2.get(i));
				
			}
			
		} catch (Exception e) {
			resp.setCode("503");
			resp.setStatus("false");
		}
		return null;
		
	}


}
