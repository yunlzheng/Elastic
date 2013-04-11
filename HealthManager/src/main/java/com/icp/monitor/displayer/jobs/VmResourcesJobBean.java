package com.icp.monitor.displayer.jobs;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.icp.monitor.commons.bean.Configuration;
import com.icp.monitor.commons.bean.ResultsTemp;
import com.icp.monitor.commons.dao.ResultsTempDao;
import com.icp.monitor.commons.otherbean.Cluster;
import com.icp.monitor.commons.otherdao.ClusterDao;
import com.icp.monitor.commons.util.SystemUtil;

/**
 * 定期查询Iaas中包含的虚拟机列表，并持久化到数据库
 * */
public class VmResourcesJobBean extends QuartzJobBean{

	private ApplicationContext applicationContext = null;
	private ClusterDao clusterDao = null;
	private ResultsTempDao resultTempDao = null;
	private Configuration configuration = null;
	private Log log = LogFactory.getLog(VmResourcesJobBean.class);
	
	private String key = "vmresources";
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		//log.info("init vmresource jobs");
		StringBuilder doc = new StringBuilder();
		boolean loaded = false;
		try {
			
			applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
			clusterDao = (ClusterDao) applicationContext.getBean("ClusterDao");
			resultTempDao = (ResultsTempDao) applicationContext.getBean("ResultsTempDao");
			configuration = (Configuration) applicationContext.getBean("Configuration");
			
		} catch (SchedulerException e) {
		
			log.error(e.getMessage());
			e.printStackTrace();
			return;
			
		}
		
		List<Cluster> list = clusterDao.list("from Cluster");
		//log.info("list clusters "+list.size());
		for(int i=0;i<list.size();i++){
			
			Cluster cluster = list.get(i);
			WebClient client = WebClient.create(configuration.getIaasUrl()+ "/veapi?cmd=GtAppServerVm&p1=" + cluster.getId());
			String res = client.invoke("GET", "", String.class);
			
			res = res.replace(" ", "");
			res=res.substring(1,res.length()-1);
		
			String[] results = res.split(",");
			for(int j=0;j<results.length;j++){
				
				int index = results[j].indexOf("\"");
				if(index!=-1){
					
					
					String ip = results[j].substring(1,results[j].length()-1);
					if(!loaded){
						doc.append(ip);
						loaded = true;
					}else{
						doc.append(","+ip);
					}
				
				
				}
				
			}
		
		}
		
		//System.out.println(doc.toString());
		ResultsTemp temp = resultTempDao.get(key);
		if(temp!=null){
			
			temp.setValue(doc.toString());
			temp.setUpdateTime(SystemUtil.getCurrentTime());
			resultTempDao.update(temp);
			
		}else{
			
			temp = new ResultsTemp();
			temp.setKey(key);
			temp.setValue(doc.toString());
			temp.setUpdateTime(SystemUtil.getCurrentTime());
			resultTempDao.add(temp);
		}
		
	}

	
	
}
