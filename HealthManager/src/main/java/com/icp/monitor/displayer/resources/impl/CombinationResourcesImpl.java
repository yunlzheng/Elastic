package com.icp.monitor.displayer.resources.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.Member;
import com.icp.monitor.commons.dao.AlertMessageDao;
import com.icp.monitor.commons.dao.AppMonitorLogDao;
import com.icp.monitor.commons.dao.ExceptionDao;
import com.icp.monitor.commons.dao.MemberDao;
import com.icp.monitor.commons.dao.MonitorLogDao;
import com.icp.monitor.commons.statistics.AlertScale;
import com.icp.monitor.commons.statistics.AlertStatistics;
import com.icp.monitor.commons.statistics.BaseScale;
import com.icp.monitor.commons.statistics.ExceptionScale;
import com.icp.monitor.commons.statistics.KVObj;
import com.icp.monitor.commons.statistics.VmExceptionScale;
import com.icp.monitor.commons.util.AccountUtil;
import com.icp.monitor.displayer.enums.ExceptionEmuns;
import com.icp.monitor.displayer.resources.CombinationResources;

public class CombinationResourcesImpl implements CombinationResources,ApplicationContextAware{

	private ExceptionDao exceptionDao = null;
	private MonitorLogDao monitorDao = null;
	private AlertMessageDao alertMessageDao = null;
	private MemberDao memberDao = null;
	private MonitorLogDao monitorLogDao = null;
	private AppMonitorLogDao appMonitorLogDao = null;
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		exceptionDao = (ExceptionDao) applicationContext.getBean("ExceptionDao");
		monitorDao = (MonitorLogDao) applicationContext.getBean("MonitorLogDao");
		alertMessageDao = (AlertMessageDao)applicationContext.getBean("AlertMessageDao");
		memberDao = (MemberDao)applicationContext.getBean("MemberDao");
		monitorLogDao = (MonitorLogDao)applicationContext.getBean("MonitorLogDao");
		appMonitorLogDao = (AppMonitorLogDao)applicationContext.getBean("AppMonitorLogDao");
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Response getVMExceptionScale(String ip) {
		
		VmExceptionScale vmExceptionScale = new VmExceptionScale();
		
		Hashtable eqProperties = new Hashtable();
		eqProperties.put("Identifiekey", ip);
		
		Hashtable eqProperties2 = new Hashtable();
		eqProperties2.put("ip",ip);
		
		Hashtable likeProperties = new Hashtable();
		Long exception = exceptionDao.getCount(eqProperties, likeProperties);
		Long total = monitorDao.getCount(eqProperties2, likeProperties);
		
		vmExceptionScale.setIp(ip);
		vmExceptionScale.setException(exception);
		vmExceptionScale.setTotal(total);
		
		return Response.ok(vmExceptionScale, MediaType.APPLICATION_JSON).build();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Response getTenantAlertStatistics(HttpServletRequest request) {
		
		List<AlertStatistics> result = new ArrayList<AlertStatistics>();
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){
			account = "guest@guest.com";
		}
		
		//查询租户下的预警成员
		Hashtable memEqProperties = new Hashtable();
		Hashtable memLikeProperties = new Hashtable();
		
		memEqProperties.put("creater", account);
		List<Member> memList = memberDao.getList(memEqProperties, memLikeProperties,null);
		for(Member mem:memList){
		
			Hashtable messageEqProperties = new Hashtable();
			Hashtable mailEqProperties = new Hashtable();
			Hashtable likeProperties = new Hashtable();
			
			//查找监控预警记录中该成员的短信数量
			messageEqProperties.put("username", account);
			messageEqProperties.put("memname",mem.getName());
			messageEqProperties.put("type", "1");
			
			mailEqProperties.put("username", account);
			mailEqProperties.put("memname",mem.getName());
			mailEqProperties.put("type", "2");
			
			Long messageNum = alertMessageDao.getCount(messageEqProperties, likeProperties);
			Long mailNum = alertMessageDao.getCount(mailEqProperties, likeProperties);
			
			AlertStatistics statistic = new AlertStatistics();
			statistic.setName(mem.getName());
			statistic.setMailNum(mailNum);
			statistic.setMessageNum(messageNum);
			
			result.add(statistic);
			
		}
		
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	public Response getExceptionScale(HttpServletRequest request) {
		
		
		String user = (String) request.getSession().getAttribute("user");
		String account = user==null?"guest@guest.com":user;
		
		
		//查询对应用户下异常的比例
		Hashtable<String,String> Level1_EqProperties = new Hashtable<String,String>();
		Hashtable<String,String> Level2_EqProperties = new Hashtable<String,String>();
		Hashtable<String,String> Level3_EqProperties = new Hashtable<String,String>();
		Hashtable<String,String> LikeProperties = new Hashtable<String,String>();

		if(account.indexOf("@")!=-1){
			Level1_EqProperties.put("creater", account);
		}
		
		Level1_EqProperties.put("level", "1");

		if(account.indexOf("@")!=-1){
			Level2_EqProperties.put("creater", account);
		}
		
		Level2_EqProperties.put("level", "2");
		if(account.indexOf("@")!=-1){
			Level3_EqProperties.put("creater", account);
		}
		
		Level3_EqProperties.put("level", "3");
		
		long serious = exceptionDao.getCount(Level1_EqProperties, LikeProperties);
		long general = exceptionDao.getCount(Level2_EqProperties, LikeProperties);
		long warning = exceptionDao.getCount(Level3_EqProperties, LikeProperties);
		
		ExceptionScale scale = new ExceptionScale();
		
		scale.setLevel_1(serious);
		scale.setLevel_2(general);
		scale.setLevel_3(warning);
		
		
		return Response.ok(scale,MediaType.APPLICATION_JSON).build();
	}

	public Response getBaseScale(HttpServletRequest request) {
		
		
		String user = (String)request.getSession().getAttribute("user");
		String account = user == null ? "guest@guest.com" : user;
 		
		/**
		 * 获取MonitorLog的所有记录
		 */
		Hashtable<String, String> alertProperties = new Hashtable<String, String>();
		Hashtable<String, String> eqProperties = new Hashtable<String, String>();
		Hashtable<String, String> likeProperties = new Hashtable<String, String>();
		
		alertProperties.put("username", account);
		
		long monitorNum = monitorLogDao.getCount(eqProperties, likeProperties);
		long appNum = appMonitorLogDao.getCount(eqProperties, likeProperties);
		long alertNum = alertMessageDao.getCount(alertProperties, likeProperties);
		long totalNum = monitorNum + appNum;
		
		BaseScale baseScale = new BaseScale();
		baseScale.setTotalNum(totalNum);
		baseScale.setAlertNum(alertNum);
		
		return Response.ok(baseScale,MediaType.APPLICATION_JSON).build();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Response getAlertCountStatistics(HttpServletRequest request) {
		
		AlertScale scale = new AlertScale();
		
		Hashtable eqProperties = new Hashtable();
		Hashtable likeProperties = new Hashtable();
		
		//短信
		eqProperties.put("type", "1");
		Long smsNum = alertMessageDao.getCount(eqProperties, likeProperties);
		
		eqProperties.clear();
		eqProperties.put("type", "2");
		Long mailNum = alertMessageDao.getCount(eqProperties, likeProperties);
		
		scale.setMailNum(mailNum);
		scale.setSmsNum(smsNum);
		
		return Response.ok(scale,MediaType.APPLICATION_JSON).build();
	}

	public Response getTenantExceptionScale(HttpServletRequest request) {
		
		ArrayList<KVObj> result = new ArrayList<KVObj>();
		String accout = (String) request.getSession().getAttribute("user");
		if(accout==null){accout="guest@guest.com";}
		String tenantName = AccountUtil.getTenantName(accout);
		for(ExceptionEmuns exception : ExceptionEmuns.values()){
			
			Hashtable eqProperties = new Hashtable();
			Hashtable likeProperties = new Hashtable();
			eqProperties.put("name", exception.toString());
			eqProperties.put("tenantName", tenantName);
			Long count = exceptionDao.getCount(eqProperties, likeProperties);
			KVObj kv = new KVObj();
			kv.setName(exception.toString());
			kv.setValue(count.toString());
			result.add(kv);
			
		}
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	
	}

	@SuppressWarnings("rawtypes")
	public Response getUserExceptionScale(HttpServletRequest request) {
		
		ArrayList<KVObj> result = new ArrayList<KVObj>();
		String accout = (String) request.getSession().getAttribute("user");
		if(accout==null){accout="guest@guest.com";}
		for(ExceptionEmuns exception : ExceptionEmuns.values()){
			
			Hashtable eqProperties = new Hashtable();
			Hashtable likeProperties = new Hashtable();
			eqProperties.put("name", exception.toString());
			eqProperties.put("creater", accout);
			Long count = exceptionDao.getCount(eqProperties, likeProperties);
			KVObj kv = new KVObj();
			kv.setName(exception.toString());
			kv.setValue(count.toString());
			result.add(kv);
			
		}
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	
	}


}
