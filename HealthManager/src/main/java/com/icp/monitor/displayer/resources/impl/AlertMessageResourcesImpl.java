package com.icp.monitor.displayer.resources.impl;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.AlertMessage;
import com.icp.monitor.commons.dao.AlertMessageDao;
import com.icp.monitor.commons.util.ApplicationContextUtil;
import com.icp.monitor.displayer.resources.AlertMessageResource;

public class AlertMessageResourcesImpl implements AlertMessageResource,ApplicationContextAware{

	ApplicationContext  context = null;
	AlertMessageDao  alerMessageDao = null;
	
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.context = arg0;
		alerMessageDao = (AlertMessageDao) context.getBean("AlertMessageDao");
	}

	
	public List<AlertMessage> list(HttpServletRequest request) {
		context = ApplicationContextUtil.getApplicationContext();
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){
			account="guest@guest.com";
		}
		List<AlertMessage> list  = null;
		
		if(account.indexOf("@")!=-1){
			
			
			list = alerMessageDao.list("from AlertMessage where username='"+account+"' order by memname");
		
		}else{
			
			list = alerMessageDao.list("from AlertMessage order by memname");
			
		}
		return list;
	}

	
	public AlertMessage get(int id) {
		context = ApplicationContextUtil.getApplicationContext();
		
		AlertMessage alertMessage = alerMessageDao.get(id);
		return alertMessage;
	}

	
	public List<AlertMessage> getByIp(String ip) {
		context = ApplicationContextUtil.getApplicationContext();
	
		List<AlertMessage> list = alerMessageDao.list("from AlertMessage where ip = '" + ip + "'");
		
		return list;
	}

	
	public List<AlertMessage> getByUserName(String username) {
		context = ApplicationContextUtil.getApplicationContext();
	
		List<AlertMessage> list = alerMessageDao.list("from AlertMessage where username='" + username + "'");
		return list;
	}

	
	public List<AlertMessage> getByMemName(String memname) {
		context = ApplicationContextUtil.getApplicationContext();
		
		List<AlertMessage> alertMessage = alerMessageDao.list("from AlertMessage where memname='" + memname + "'");
		return alertMessage;
	}


	public List<AlertMessage> listByPage(HttpServletRequest request,
			int offset, int length, String[] eqs, String[] likes) {
		
		Map<String, String> eqProperties = new Hashtable<String,String>();
		Map<String, String> likeProperties = new Hashtable<String,String>();
		Map<String, String> orderProperties = new Hashtable<String,String>();
		for(int i = 0 ; i < eqs.length ; i++){
			System.out.println(eqs[i]);
		}
		if(eqs.length>0&&eqs[0]!=null){
			
			for(String iter:eqs){
				
				String set[] = iter.split(":");
				eqProperties.put(set[0], set[1]);
				
			}
			
		}
		
		if(likes.length>0&&likes[0]!=null){
			
			for(String iter:likes){
				
				String set[] = iter.split(":");
				likeProperties.put(set[0], set[1]);
				
			}
			
		}
		
		//用户控制
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){
			account="guest@guest.com";
		}
		
		if(account.indexOf("@")!=-1){
			eqProperties.put("username", account);
		}
		
		orderProperties.put("type", "desc");
		orderProperties.put("key", "id");
		List<AlertMessage> result = alerMessageDao.getListForPage(offset, length, eqProperties, likeProperties,orderProperties);
		return result;
	
	}


	
}
