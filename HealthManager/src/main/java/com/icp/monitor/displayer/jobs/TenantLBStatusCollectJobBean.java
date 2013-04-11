package com.icp.monitor.displayer.jobs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.utils.Key;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import au.com.bytecode.opencsv.CSVReader;

import com.icp.monitor.commons.bean.Configuration;
import com.icp.monitor.commons.bean.LBStatus;
import com.icp.monitor.commons.bean.LBStatusBackend;
import com.icp.monitor.commons.dao.LBStatusBackendDao;
import com.icp.monitor.commons.dao.LBStatusDao;
import com.icp.monitor.commons.util.SystemUtil;

public class TenantLBStatusCollectJobBean extends QuartzJobBean{

	private Logger log = Logger.getLogger(TenantLBStatusCollectJobBean.class);
	
	private List<String> noLogStringList = new ArrayList<String>();

	private String filters = null;
	private Configuration configuration = null;
	private LBStatusBackendDao lbBackendDao = null;
	private LBStatusDao lbDao = null;
	private String lbserver = null;
	
	
	
	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getLbserver() {
		return lbserver;
	}

	public void setLbserver(String lbserver) {
		this.lbserver = lbserver;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		Key key = context.getJobDetail().getKey();
		log.info(key.getName()+" start TenantLBStatus Jobs "+SystemUtil.getCurrentTime());
		JobDataMap data = context.getJobDetail().getJobDataMap();
		
		ApplicationContext applicationContext = null;
		try{
			
			/**初始化相关数据*/
			applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
			configuration = (Configuration) applicationContext.getBean("Configuration");
			
			lbDao = (LBStatusDao) applicationContext.getBean("ReceiverLBStatusDao");
			lbBackendDao = (LBStatusBackendDao) applicationContext.getBean("ReceiverLBStatusBackendDao");
			
			lbserver = configuration.getLbserver();
			filters = configuration.getLbSvnameFilter();
			if (filters != null) {

				StringTokenizer st = new StringTokenizer(filters, ";");
				noLogStringList.clear();
				while (st.hasMoreTokens()) {
					String filterkey = st.nextToken();
					noLogStringList.add(filterkey);
					
				}
			}
			
			execute();
			
		}catch(Exception e){
			log.error(e);
		}
		
	}
	
	public void execute() {

			log.info(SystemUtil.getCurrentTime());
			String query = this.getLbserver();
			URL url = null;
			try {

				url = new URL(query);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						url.openStream()));

				CSVReader reader = new CSVReader(in);
				String[] nextLine;
				
				String joinTime = SystemUtil.getToday();
				String detailTime = SystemUtil.getCurrentTime();
				
				while ((nextLine = reader.readNext()) != null) {

					
					if (!checkFilter(nextLine[1])) {
						
						LBStatus lb = new LBStatus(nextLine);
						lb.setJoinTime(joinTime);
						lb.setDetailTime(detailTime);
						if(lb.isValidation()){
							lbDao.add(lb);
						}
						
						
					} else if (nextLine[1].equals("BACKEND")) {
						
						Hashtable eqProperties = new Hashtable();
						Hashtable likeProperties = new Hashtable();
						Hashtable orderProperties = new Hashtable();
						
						eqProperties.put("pxname",nextLine[0]);
						List<LBStatusBackend> list = lbBackendDao.getList(eqProperties, likeProperties, orderProperties);
						if(list.size()>0){
							//更新
							LBStatusBackend backend = list.get(0);
							LBStatusBackend lbbackend = new LBStatusBackend(nextLine);
							lbbackend.setId(backend.getId());
							if(lbbackend.isValidation()){
								lbBackendDao.update(lbbackend);
							}
							
							
						}else{
							
							//添加
							LBStatusBackend lbbackend = new LBStatusBackend(nextLine);
							lbbackend.setJoinTime(joinTime);
							lbbackend.setDetailTime(detailTime);
							if(lbbackend.isValidation()){
								lbBackendDao.add(lbbackend);
							}
							
						}
						
						
						
					}

				}
			} catch (Exception e) {

				log.error(e);

			}
		}
	
	public boolean checkFilter(String key) {

		return noLogStringList.contains(key);
	}

}
