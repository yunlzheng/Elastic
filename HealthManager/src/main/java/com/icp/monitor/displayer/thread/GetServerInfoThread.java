package com.icp.monitor.displayer.thread;


import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import com.icp.monitor.commons.bean.MonitorLog;
import com.icp.monitor.commons.dao.MonitorLogDao;
import com.icp.monitor.commons.model.ServerInfo;
import com.icp.monitor.commons.util.SystemUtil;
import com.icp.monitor.displayer.util.ConvertJson;
import com.icp.monitor.displayer.warn.AlertAdapter;

public class GetServerInfoThread extends Thread{

	private boolean isRun = true;
	private int delying;
	private String ip = null;
	private ApplicationContext context = null;
	private boolean isBreak=false;
	private String iaasUrl = null;
	
	private MonitorLogDao monitorLogDao = null;
	
	public GetServerInfoThread(ApplicationContext arg0,String iaasUrl, String ip, int deyling) {
		
		this.context = arg0;
		this.iaasUrl = iaasUrl;
		this.ip = ip;
		this.delying = deyling;
		monitorLogDao = (MonitorLogDao) context.getBean("ReceiverMonitorLogDao");
	}
	
	
	@Override
	public void run() {
		
		System.out.println("icp.monitor tenant start vm status collector thread[thread-"+ip+"]");
		while (isRun) {
			
			boolean finded = false;
			ArrayList<ServerInfo> servers = null;
			String jsons = null;
			
			try{
				jsons = ConvertJson.readJsonFromUrl(this.iaasUrl+"/veapi?cmd=GtVmStat");
				
			}catch(Exception e){
				
				System.out.println(e.getMessage());
				
			}
			
			servers = ConvertJson.getServerInfo(jsons);
			if(servers.size()!=0){
				
				for (int i = 0; i < servers.size(); i++) {
					
					ServerInfo info = servers.get(i);
					
					if( info!=null&&this.ip.equals(info.getIp())){
					
						MonitorLog monitorLog = new MonitorLog();
						monitorLog.setIp(info.getIp());
						monitorLog.setCpu(info.getCpu());
						monitorLog.setIo(info.getIo());
						monitorLog.setDisk(info.getDisk());
						monitorLog.setMem(info.getMem());
						monitorLog.setJoinTime(SystemUtil.getToday());
						monitorLog.setDetailTime(SystemUtil.getCurrentTime());
						monitorLog.setAdmin(false);
						
						try{
							
							monitorLogDao.add(monitorLog);
							new AlertAdapter(context,monitorLog).start();
							finded = true;
							
						}catch(Exception e){}
		
					}
					
				}
				
			}
		
			if(!finded){
				
				MonitorLog monitorLog = new MonitorLog();
				monitorLog.setIp(ip);
				monitorLog.setCpu("60");
				monitorLog.setIo("60");
				monitorLog.setDisk("20");
				monitorLog.setMem("75");
				monitorLog.setJoinTime(SystemUtil.getToday());
				monitorLog.setDetailTime(SystemUtil.getCurrentTime());
				monitorLog.setAdmin(false);
				
				try{
					
					monitorLogDao.add(monitorLog);
					new AlertAdapter(context,monitorLog).start();
					finded = true;
					
				}catch(Exception e){}
				
			}
	
			if(isBreak){
				isBreak=false;
				break;
			}
			try {
				Thread.sleep(delying);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	public boolean isBreak() {
		return isBreak;
	}

	public void setBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}

	public int getDelying() {
		return delying;
	}

	public void setDelying(int delying) {
		this.delying = delying;
	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}



}
