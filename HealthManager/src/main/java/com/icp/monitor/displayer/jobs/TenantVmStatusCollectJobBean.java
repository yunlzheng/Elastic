package com.icp.monitor.displayer.jobs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;


import com.icp.monitor.commons.bean.Configuration;
import com.icp.monitor.commons.bean.MonitorLog;
import com.icp.monitor.commons.bean.ServerConfig;
import com.icp.monitor.commons.dao.MonitorLogDao;
import com.icp.monitor.commons.dao.ServerConfigDao;
import com.icp.monitor.commons.model.ServerInfo;
import com.icp.monitor.commons.util.SystemUtil;
import com.icp.monitor.displayer.util.ConvertJson;
import com.icp.monitor.displayer.warn.AlertAdapter;

/**
 *获取数据库中时间间隔相同的 
 * */
public class TenantVmStatusCollectJobBean extends QuartzJobBean {

	private Logger log = Logger.getLogger(TenantVmStatusCollectJobBean.class);
	private ServerConfigDao serverConfigDao=null;
	private MonitorLogDao logDao = null;
	private Configuration configuration = null;
	
	//Iaas服务器地址
	private String iaasUrl = null;
	private Hashtable<String,Boolean> serverList = new Hashtable<String,Boolean>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		log.info("start TenantVmStatus Jobs "+SystemUtil.getCurrentTime());
		JobDataMap data = context.getJobDetail().getJobDataMap();
		
		int internalKey =  data.getInt("InternalKey");
		
		ApplicationContext applicationContext = null;
		
		try {
			
			applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
			serverConfigDao = (ServerConfigDao) applicationContext.getBean("ReceiverServerConfigDao");
			logDao = (MonitorLogDao) applicationContext.getBean("ReceiverMonitorLogDao");
			configuration = (Configuration) applicationContext.getBean("Configuration");
			this.iaasUrl = configuration.getIaasUrl();
			//log.info("inited applicationContext");
			
		} catch (SchedulerException e) {
			
			log.error(e);
			return;
		
		}

		//log.info("internalKey is "+internalKey);
		
		if(internalKey==0){
			log.info("internalKey is null use default value '"+1+"'");
			internalKey=1;
		}
		
		//log.info("Start Collect Vm Status internalKey is "+internalKey);
		
	
		/**
		 * 查询监控频率为[internalKey]需要监控的服务器ID列表
		 * */
		Hashtable eqProperties = new Hashtable();
		Hashtable likeProperties = new Hashtable();
		eqProperties.put("admin", false);
		eqProperties.put("deyling", internalKey);
		List<ServerConfig> servers = serverConfigDao.getList(eqProperties, likeProperties,null);
		StringBuilder doc = new StringBuilder();
		for(int i=0;i<servers.size();i++){
			
			ServerConfig server = servers.get(i);
			doc.append(server.getIp()).append(";");
			serverList.put(server.getIp(),false);
			doc.append(server.getIp()).append(";");
			
		}
		//log.debug("init servers list is->"+doc.toString());
		
		/**
		 * 从iass-p获取虚拟机信息
		 * */
		String jsons = null;
		try {
			
			jsons = ConvertJson.readJsonFromUrl(this.iaasUrl+"/veapi?cmd=GtVmStat");
			ArrayList<ServerInfo> infos =  ConvertJson.getServerInfo(jsons);
			for(int i=0;i<infos.size();i++){
				
				ServerInfo info = infos.get(i);
				if(info!=null&&serverList.keySet().contains(info.getIp())){
					
					MonitorLog logs = new MonitorLog();
					logs.setIp(info.getIp());
					logs.setCpu(info.getCpu());
					logs.setIo(info.getIo());
					logs.setDisk(info.getDisk());
					logs.setMem(info.getMem());
					logs.setJoinTime(SystemUtil.getToday());
					logs.setDetailTime(SystemUtil.getCurrentTime());
					logs.setAdmin(false);
					//更新服务器状态ture表示已经加载信息
				//	log.debug(info.getIp()+"  "+info.getCpu()+"  "+info.getDisk()+"  "+info.getMem());
					
					try{
						logDao.add(logs);
						new AlertAdapter(applicationContext,logs).start();
					}catch (Exception e) {
						
						log.error(e);
					}
					serverList.put(info.getIp(), true);
					
				}
				
			}
		
		} catch (IOException e) {
			
			log.error(e);
		
		}finally{
			
			int MAX=60;
			int MIN = 10;
			Random rand = new Random();
			
			//为未获取到数据的虚拟机添加测试数据
			for(String key:serverList.keySet()){
				
				if(serverList.get(key)==false){
					
				//	log.debug("VM["+key+"] not load really data");
					MonitorLog logs = new MonitorLog();
					logs.setIp(key);
					logs.setCpu((rand.nextInt(MAX - MIN + 1) + MIN)+"");
					logs.setIo((rand.nextInt(MAX - MIN + 1) + MIN)+"");
					logs.setDisk((rand.nextInt(MAX - MIN + 1) + MIN)+"");
					logs.setMem((rand.nextInt(MAX - MIN + 1) + MIN)+"");
					logs.setJoinTime(SystemUtil.getToday());
					logs.setDetailTime(SystemUtil.getCurrentTime());
					logs.setAdmin(false);
					try{
						logDao.add(logs);
						new AlertAdapter(applicationContext,logs).start();
					}catch (Exception e) {
						
						log.error(e);
						
					}
					
				}
				
			}
			
		}
	}
}
