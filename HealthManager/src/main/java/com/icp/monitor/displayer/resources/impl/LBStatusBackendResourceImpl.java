package com.icp.monitor.displayer.resources.impl;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.LBStatusBackend;
import com.icp.monitor.commons.dao.LBStatusBackendDao;
import com.icp.monitor.displayer.resources.LBStatusBackendResource;

public class LBStatusBackendResourceImpl implements LBStatusBackendResource,ApplicationContextAware{

	private ApplicationContext context = null;
	
	public LBStatusBackend get(int id) {
		LBStatusBackendDao dao = (LBStatusBackendDao) context.getBean("LBStatusBackendDao");
		return dao.get(id);
	}

	public List<LBStatusBackend> list() {
		LBStatusBackendDao dao = (LBStatusBackendDao) context.getBean("LBStatusBackendDao");
		return dao.list("from LBStatusBackend");
	}
	



	public List<LBStatusBackend> listByPxname(String pxname) {
		
		LBStatusBackendDao dao = (LBStatusBackendDao) context.getBean("LBStatusBackendDao");
		return dao.list("from LBStatusBackend where pxname='"+pxname+"' order by id desc",0,10);
		
	}

	public List<LBStatusBackend> getLastStatusBypxname(String pxname,String appname){
		
		LBStatusBackendDao dao = (LBStatusBackendDao) context.getBean("LBStatusBackendDao");
		return dao.list("from LBStatusBackend where pxname='"+pxname+"/"+appname+"' order by id desc",0,1);
		
	}
	
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		
		this.context = context;
		
	}

}
