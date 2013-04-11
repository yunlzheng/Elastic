package com.icp.monitor.displayer.resources.impl;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.MonitorLog;
import com.icp.monitor.commons.bean.Response;
import com.icp.monitor.commons.dao.MonitorLogDao;
import com.icp.monitor.commons.dao.impl.MonitorLogDaoImpl;
import com.icp.monitor.commons.util.SystemUtil;
import com.icp.monitor.displayer.resources.MonitorLogResources;

public class MonitorLogResourcesImpl implements ApplicationContextAware,
		MonitorLogResources {

	private Log log = LogFactory.getLog(MonitorLogResourcesImpl.class);
	private MonitorLogDao monitorLogDao = null;
	
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
	
	    monitorLogDao = (MonitorLogDaoImpl) context.getBean("MonitorLogDao");

	}
	
	public List<MonitorLog> list(HttpServletRequest request) {
		
		String account = (String) request.getSession().getAttribute("user");
		
		String hql = null;
		
		if(account.indexOf("@")!=-1){
			
			hql = "from MonitorLog where admin=false";
			
		}else{
			
			hql = "from MonitorLog where admin=true";
			
		}
		
		List<MonitorLog> list = monitorLogDao.list(hql);
		return list;
	}

	public MonitorLog get(HttpServletRequest request,int id) {
		
		
		MonitorLog modnitorLog = monitorLogDao.get(id);
		return modnitorLog;
	}

	public MonitorLog add(HttpServletRequest request,String ip, String cpu, String mem, String io,
			String disk) {

		
		MonitorLog monitorlog = new MonitorLog();
		monitorlog.setCpu(cpu);
		monitorlog.setDisk(disk);
		monitorlog.setIo(io);
		monitorlog.setIp(ip);
		monitorlog.setMem(mem);
		monitorlog.setJoinTime(SystemUtil.getToday());
		monitorlog.setDetailTime(SystemUtil.getCurrentTime());
		monitorLogDao.add(monitorlog);
		return monitorlog;

	}

	public MonitorLog update(HttpServletRequest request,String id, String ip, String cpu, String mem,
			String io, String disk) {

		MonitorLog monitorlog = new MonitorLog();

		monitorlog.setCpu(cpu);
		monitorlog.setDisk(disk);
		monitorlog.setId(Integer.parseInt(id));
		monitorlog.setIo(io);
		monitorlog.setIp(ip);
		monitorlog.setJoinTime(SystemUtil.getToday());
		monitorlog.setDetailTime(SystemUtil.getCurrentTime());
		monitorlog.setMem(mem);

		monitorLogDao.update(monitorlog);
		return monitorlog;
	}

	public Response delete(HttpServletRequest request,int id) {
		
		Response resp = new Response();
		try {
			monitorLogDao.deleteById(id);
			resp.setCode("200");
			resp.setStatus("success");
		} catch (Exception e) {
			resp.setCode("503");
			resp.setStatus("false");
		}
		return null;
	}

	public List<MonitorLog> getByIp(HttpServletRequest request,String ip) {

		String account = (String) request.getSession().getAttribute("user");
		if(account==null){account="guest@guest.com";}
		
		Hashtable<String, Object> eqProperties = new Hashtable<String, Object>();
		Hashtable<String, String> likeProperties = new Hashtable<String, String>();
		
		if(account.indexOf("@")!=-1){
			eqProperties.put("admin", false);
		}else{
			eqProperties.put("admin", true);
		}
		
		likeProperties.put("ip", ip);
		List<MonitorLog> list = monitorLogDao.getList(eqProperties,likeProperties,null);
		return list;
	}

	public List<MonitorLog> getByDate(HttpServletRequest request,String ip,String begin, String end) {

		String account = (String) request.getSession().getAttribute("user");
		if(account==null){account="guest@guest.com";}
		Date _begin = java.sql.Date.valueOf(begin);
		Date _end = java.sql.Date.valueOf(end);
		
		boolean isadmin = false;
		if(account.indexOf("@")==-1){
			isadmin=true;
		}
		
		List<MonitorLog> list = monitorLogDao.SelectByDate(ip,_begin, _end,isadmin);
		log.info("search by date "+ip+"  result:"+list.size());

		return list;
	}

	public List<MonitorLog> getByMode(HttpServletRequest request,String ip,int mode) {

		String account = (String) request.getSession().getAttribute("user");
		if(account==null){account="guest@guest.com";}
		boolean isadmin = false;
		if(account.indexOf("@")==-1){
			isadmin=true;
		}
		
		String today = SystemUtil.getToday();
		String yesterday = SystemUtil.getYesterday();
		String lastWeek = SystemUtil.getLastWeek();
		String lastMonth = SystemUtil.getLastMonth();

		String begin = null;
		String end = today;
		Date _begin = null;
		Date _end = null;

		List<MonitorLog> list = null;

		switch (mode) {
		case 1:
			begin = yesterday;
			_begin = java.sql.Date.valueOf(begin);
			_end = java.sql.Date.valueOf(end);
			list = monitorLogDao.SelectByDate(ip,_begin, _end,isadmin);
			break;
		case 2:
			begin = lastWeek;
			_begin = java.sql.Date.valueOf(begin);
			_end = java.sql.Date.valueOf(end);
			list = monitorLogDao.SelectByDate(ip,_begin, _end,isadmin);
			break;
		case 3:

			begin = lastMonth;
			_begin = java.sql.Date.valueOf(begin);
			_end = java.sql.Date.valueOf(end);
			list = monitorLogDao.SelectByDate(ip,_begin, _end,isadmin);
			
			break;
		default:
			break;
		}

		log.info("search by mode "+ip+"  mode "+mode+"  result:"+list.size());
		
		return list;
	}

	

	public List<MonitorLog> searchByProperties(HttpServletRequest request,
			int offset, int len, String[] eqs, String[] likes) {
		
		
		Map<String, String> eqProperties = new Hashtable<String,String>();
		Map<String, String> likeProperties = new Hashtable<String,String>();
		
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
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){account="guest@guest.com";}
		if(account.indexOf("@")!=-1){
			eqProperties.put("creater", account);
		}
		List<MonitorLog> result = monitorLogDao.getListForPage(offset, len, eqProperties, likeProperties,null);
		return result;

	}

}
