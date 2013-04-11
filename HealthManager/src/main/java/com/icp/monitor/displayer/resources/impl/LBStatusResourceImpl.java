package com.icp.monitor.displayer.resources.impl;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.LBStatus;
import com.icp.monitor.commons.dao.LBStatusDao;
import com.icp.monitor.displayer.resources.LBStatusResource;

public class LBStatusResourceImpl implements LBStatusResource,ApplicationContextAware{

	private ApplicationContext context = null;
	
	public LBStatus get(int id) {
		
		LBStatusDao lbStatusDao = (LBStatusDao) context.getBean("ReceiverLBStatusDao");
		return lbStatusDao.get(id);
	}

	public List<LBStatus> list() {
		
		LBStatusDao lbStatusDao = (LBStatusDao) context.getBean("ReceiverLBStatusDao");
		return lbStatusDao.list("from LBStatus");
	
	}

	public List<LBStatus> listByPxname(String pxname) {
		
		LBStatusDao lbStatusDao = (LBStatusDao) context.getBean("ReceiverLBStatusDao");
		@SuppressWarnings("unused")
		Hashtable<String, String> eqProperties = new Hashtable<String, String>();
		return lbStatusDao.list("from LBStatus where pxname='"+pxname+"'");
		
	}

	public List<LBStatus> listByPaxnameOrderByDate(String pxname,
			String begin, String end) {
		
		Date _begin = java.sql.Date.valueOf(begin);
		Date _end = java.sql.Date.valueOf(end);
		
		LBStatusDao lbStatusDao = (LBStatusDao) context.getBean("ReceiverLBStatusDao");
		Hashtable<String, String> eqProperties = new Hashtable<String, String>();
		
		eqProperties.put("pxname", pxname);
		
		@SuppressWarnings("unused")
		Hashtable<String, String> likeProperties = new Hashtable<String, String>();
		List<LBStatus> list = lbStatusDao.ListByDateAndPxname(_begin,_end,pxname);
		
		return list;
	}

	public List<LBStatus> listSvname(String pxname) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		this.context = applicationContext;
		
	}

	

}
