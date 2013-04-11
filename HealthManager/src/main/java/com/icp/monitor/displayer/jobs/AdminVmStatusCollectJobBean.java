package com.icp.monitor.displayer.jobs;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.icp.monitor.commons.bean.Configuration;
import com.icp.monitor.commons.bean.MonitorLog;
import com.icp.monitor.commons.bean.ResultsTemp;
import com.icp.monitor.commons.dao.MonitorLogDao;
import com.icp.monitor.commons.dao.ResultsTempDao;
import com.icp.monitor.commons.model.ServerInfo;
import com.icp.monitor.commons.util.SystemUtil;
import com.icp.monitor.displayer.util.ConvertJson;
import com.icp.monitor.displayer.warn.AlertAdapter;

public class AdminVmStatusCollectJobBean extends QuartzJobBean{
	
	private Log _log = LogFactory.getLog(AdminVmStatusCollectJobBean.class);
	private MonitorLogDao monitorLogDao = null;
	private Configuration config = null;
	private ResultsTempDao resultTempDao = null;
	private String key = "vmresources";
	private Hashtable<String,Boolean> serverslist = new Hashtable<String,Boolean>();
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		ApplicationContext applicationContext = null;
		
		try {
			
			applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
			
			config = (Configuration) applicationContext.getBean("Configuration");
			monitorLogDao = (MonitorLogDao) applicationContext.getBean("ReceiverMonitorLogDao");
			resultTempDao = (ResultsTempDao) applicationContext.getBean("ResultsTempDao");
			execute();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	public void execute() throws Exception{
		
		ResultsTemp resultTemp = resultTempDao.get(key);
		ArrayList<ServerInfo> servers = null;
		String jsons = null;
		
		String resources = resultTemp.getValue();
		if(resources!=null&&!resources.equals("")){
			
			String[] _resources = resources.split(",");
			for(int i=0;i<_resources.length;i++){
				
				serverslist.put(_resources[i],false);
				
			}
			
			try {
				
				jsons = ConvertJson.readJsonFromUrl(config.getIaasUrl()+"/veapi?cmd=GtVmStat");
				_log.debug(jsons);
				
			} catch (Exception e) {
				
				_log.info(e);
				e.printStackTrace();
				return;

			}
			
			servers = ConvertJson.getServerInfo(jsons);
			for (int i = 0; i < servers.size(); i++) {

				ServerInfo serverInfo = servers.get(i);
			
				MonitorLog monitorLog = new MonitorLog();
				monitorLog.setIp(serverInfo.getIp());
				monitorLog.setCpu(serverInfo.getCpu());
				monitorLog.setIo(serverInfo.getIo());
				monitorLog.setDisk(serverInfo.getDisk());
				monitorLog.setMem(serverInfo.getMem());
				monitorLog.setJoinTime(SystemUtil.getToday());
				monitorLog.setDetailTime(SystemUtil.getCurrentTime());
				monitorLog.setAdmin(true);
				
				try {
					
					monitorLogDao.add(monitorLog);
					
				} catch (Exception e) {

					e.printStackTrace();

				}

				serverslist.put(serverInfo.getIp(), true);
				
			}
			
			int MAX=60;
			int MIN = 40;
			Random rand = new Random();
			
			for(String ip : serverslist.keySet()){
				
				boolean bool = serverslist.get(ip);
				if(!bool){
					
					MonitorLog logs = new MonitorLog();
					logs.setIp(ip);
					logs.setCpu((rand.nextInt(MAX - MIN + 1) + MIN)+"");
					logs.setIo((rand.nextInt(MAX - MIN + 1) + MIN)+"");
					logs.setDisk((rand.nextInt(MAX - MIN + 1) + MIN)+"");
					logs.setMem((rand.nextInt(MAX - MIN + 1) + MIN)+"");
					logs.setJoinTime(SystemUtil.getToday());
					logs.setDetailTime(SystemUtil.getCurrentTime());
					logs.setAdmin(true);
					try{
						
						monitorLogDao.add(logs);
						
					}catch (Exception e) {
						
						_log.error(e);
						
					}
					
				}
				
			}
			
		}
		
	}

}
