package com.icp.monitor.displayer.resources.impl;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.MonitorThreshold;
import com.icp.monitor.commons.bean.Response;
import com.icp.monitor.commons.dao.MonitorThresholdDao;
import com.icp.monitor.commons.dao.impl.MonitorThresholdDaoImpl;
import com.icp.monitor.commons.util.SystemUtil;
import com.icp.monitor.displayer.resources.MonitorThresholdResources;

public class MonitorThresholdResourcesImpl implements ApplicationContextAware,MonitorThresholdResources{

	private ApplicationContext context = null;
	
	public List<MonitorThreshold> list() {
		MonitorThresholdDao  monitorthresholdDao = (MonitorThresholdDaoImpl)context.getBean("MonitorThresholdDao");
		List<MonitorThreshold> list = monitorthresholdDao.list("from MonitorThreshold");
		return list;
	}

	
	public MonitorThreshold get(String ip) {
		MonitorThresholdDao  monitorthresholdDao = (MonitorThresholdDaoImpl)context.getBean("MonitorThresholdDao");
		MonitorThreshold monitorThreshold = monitorthresholdDao.get(ip);
		return monitorThreshold;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MonitorThreshold add(HttpServletRequest request,String ip,String cpuThreshlod,String memThreshlod,String ioThreshlod,String diskThreshlod) {
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){
			
			account = "guest@guest.com";
		}
		boolean isAdmin = false;
		boolean update = false;
		
		if(account.indexOf("@")==-1){
			isAdmin = true;
		}
		
		MonitorThreshold monitorThreashold = null;
	
		MonitorThresholdDao  monitorthresholdDao = (MonitorThresholdDaoImpl)context.getBean("MonitorThresholdDao");
		
		Hashtable eqProperties = new Hashtable();
		eqProperties.put("admin", isAdmin);
		eqProperties.put("ip", ip);
		Hashtable likeProperties = new Hashtable();
		
		List<MonitorThreshold> list = monitorthresholdDao.getList(eqProperties, likeProperties,null);
		
		if(list.size()!=0){
			update = true;
			monitorThreashold = list.get(0);
		}else{
			monitorThreashold = new MonitorThreshold();
		}
		
		monitorThreashold.setCreater(account);
		monitorThreashold.setCpuThreshlod(Integer.parseInt(cpuThreshlod));
		monitorThreashold.setDiskThreshlod(Integer.parseInt(diskThreshlod));
		monitorThreashold.setIoThreshlod(Integer.parseInt(ioThreshlod));
		monitorThreashold.setIp(ip);
		monitorThreashold.setMemThreshlod(Integer.parseInt(memThreshlod));
		monitorThreashold.setJoinTime(SystemUtil.getCurrentTime());
		monitorThreashold.setAdmin(isAdmin);
		if(update){
			monitorthresholdDao.update(monitorThreashold);
		}else{
			monitorthresholdDao.add(monitorThreashold);
		}
		
	
		return monitorThreashold;
	}
	
	public MonitorThreshold update(HttpServletRequest request,String ip,
			String cpuThreshlod,
			String memThreshlod,
			String ioThreshlod,
			String diskThreshlod) {
		
		
		MonitorThresholdDao  monitorthresholdDao = (MonitorThresholdDaoImpl)context.getBean("MonitorThresholdDao");
		MonitorThreshold monitorThreashold = monitorthresholdDao.get(ip);
		
		monitorThreashold.setCpuThreshlod(Integer.parseInt(cpuThreshlod));
		monitorThreashold.setDiskThreshlod(Integer.parseInt(diskThreshlod));
		monitorThreashold.setIoThreshlod(Integer.parseInt(ioThreshlod));
		monitorThreashold.setJoinTime(SystemUtil.getCurrentTime());
		monitorThreashold.setMemThreshlod(Integer.parseInt(memThreshlod));
		
		monitorthresholdDao.update(monitorThreashold);
		return monitorThreashold;
	}

	
	public Response delete(String ip) {
		MonitorThresholdDao  monitorthresholdDao = (MonitorThresholdDaoImpl)context.getBean("MonitorThresholdDao");
		Response resp = new Response();
		try {
			monitorthresholdDao.deleteById(ip);
			resp.setCode("200");
			resp.setStatus("success");
		} catch (Exception e) {
			resp.setCode("503");
			resp.setStatus("false");
		}
		return resp;
	}

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}

	

}
