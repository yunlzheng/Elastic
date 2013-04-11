package com.icp.monitor.displayer.thread.manager;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.ServerConfig;
import com.icp.monitor.commons.dao.ServerConfigDao;
import com.icp.monitor.commons.dao.impl.ServerConfigDaoImpl;
import com.icp.monitor.displayer.thread.GetServerInfoThread;

public class ThreadManager extends Thread implements ApplicationContextAware{
	
	//private Log log = LogFactory.getLog(ThreadManager.class);
	private Hashtable<String, GetServerInfoThread> getServerInfoThreadTable=new Hashtable<String, GetServerInfoThread>();
	private ApplicationContext context = null;
	
	private String iaasUrl;
	private int deyling;
	private ServerConfigDao monitorConfigDao=null;
	private boolean isStart=true;
	private boolean isRun=true;
	
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		
		this.context = arg0;
		monitorConfigDao=(ServerConfigDaoImpl)context.getBean("ReceiverServerConfigDao");
		
	}
	
	
	public String getIaasUrl() {
		return iaasUrl;
	}
	
	@Value("#{SystemConfig['iaasurl']}")
	public void setIaasUrl(String iaasUrl) {
		this.iaasUrl = iaasUrl;
	}

	public int getDeyling() {
		return deyling;
	}

	@Value("#{SystemConfig['recivervUpdateDelying']}")
	public void setDeyling(int deyling) {
		this.deyling = deyling;
	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void startGetServerInfoThread(){
	
		if(isStart){
			
			Hashtable eqProperties = new Hashtable();
			eqProperties.put("admin", false);
			Hashtable likeProperties = new Hashtable();
			List<ServerConfig> monitorConfigs = monitorConfigDao.getList(eqProperties, likeProperties,null);
			
			for(int i=0;i<monitorConfigs.size();i++){
				if(getServerInfoThreadTable.containsKey(monitorConfigs.get(i).getIp())){
					getServerInfoThreadTable.get(monitorConfigs.get(i).getIp()).setDelying(monitorConfigs.get(i).getDeyling());
				}else{
					String _ip = monitorConfigs.get(i).getIp();
					int _deyling = monitorConfigs.get(i).getDeyling();
					GetServerInfoThread getServerInfoThread = new GetServerInfoThread(context,iaasUrl,_ip,_deyling);
					getServerInfoThread.start();
					getServerInfoThreadTable.put(monitorConfigs.get(i).getIp(), getServerInfoThread);
				}
			}
			Enumeration<String> keys=getServerInfoThreadTable.keys();
			while(keys.hasMoreElements()){
				boolean flag=false;
				String key=keys.nextElement();
				for(int i=0;i<monitorConfigs.size();i++){
					if(key.equals(monitorConfigs.get(i).getIp())){
						flag=true;
					}
				}
				/**
				 * 如果表中无该ip则终止程序
				 */
				if(!flag){
					getServerInfoThreadTable.get(key).setRun(false);
					getServerInfoThreadTable.remove(key);
				}
			}
		}else{
			Enumeration<GetServerInfoThread> enumeration=getServerInfoThreadTable.elements();
			while(enumeration.hasMoreElements()){
				
				enumeration.nextElement().setBreak(true);
			}
			getServerInfoThreadTable.clear();
			
		}
	}
		
	@Override
	public void run() {
	
			while(isRun){
				
				
				startGetServerInfoThread();
				//System.out.println("update receiver collector thread:"+getServerInfoThreadTable.size());
				
				try {
					Thread.sleep(deyling);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		
		
	}

	
	
}
