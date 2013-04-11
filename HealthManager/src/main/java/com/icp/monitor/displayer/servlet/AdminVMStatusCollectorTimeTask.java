package com.icp.monitor.displayer.servlet;

import java.util.ArrayList;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.MonitorLog;
import com.icp.monitor.commons.dao.MonitorLogDao;
import com.icp.monitor.commons.model.ServerInfo;
import com.icp.monitor.commons.util.SystemUtil;
import com.icp.monitor.displayer.util.ConvertJson;

public class AdminVMStatusCollectorTimeTask extends TimerTask implements ApplicationContextAware{

	private Log log = LogFactory.getLog(AdminVMStatusCollectorTimeTask.class);
	private ApplicationContext context = null;
	private MonitorLogDao monitorLogDao = null;
	
	private String iaasUri = null;
	
	public String getIaasUri() {
		return iaasUri;
	}

	@Value("#{SystemConfig['iaasurl']}")
	public void setIaasUri(String iaasUri) {
		this.iaasUri = iaasUri;
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		
		this.context = arg0;
		monitorLogDao = (MonitorLogDao) context.getBean("ReceiverMonitorLogDao");
		
	}

	
	
	@Override
	public void run() {
		
		ArrayList<ServerInfo> servers = null;
		String jsons = null;
		
		try {
			//http://iassp.wocloud.com
			jsons = ConvertJson.readJsonFromUrl(getIaasUri()+ "/veapi?cmd=GtVmStat");
			System.out.println("++++++++++++++++"+jsons);
		} catch (Exception e) {
			
			log.info(e);
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

		}
		
	}

	
	
}
