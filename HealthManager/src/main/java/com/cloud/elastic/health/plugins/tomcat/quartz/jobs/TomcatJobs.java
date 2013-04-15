package com.cloud.elastic.health.plugins.tomcat.quartz.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;

import org.hibernate.criterion.Order;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cloud.elastic.commons.dao.TomcatDao;
import com.cloud.elastic.commons.dao.TomcatFlowDao;
import com.cloud.elastic.commons.dao.TomcatJvmMemoryDao;
import com.cloud.elastic.commons.dao.TomcatLogDao;
import com.cloud.elastic.commons.dao.TomcatThreadDao;
import com.cloud.elastic.commons.monitor.bean.Tomcat;
import com.cloud.elastic.commons.monitor.bean.TomcatFlow;
import com.cloud.elastic.commons.monitor.bean.TomcatJvmMemory;
import com.cloud.elastic.commons.monitor.bean.TomcatLog;
import com.cloud.elastic.commons.monitor.bean.TomcatThread;
import com.cloud.elastic.health.plugins.tomcat.TomcatException;
import com.cloud.elastic.health.plugins.tomcat.TomcatPaser;

public class TomcatJobs extends QuartzJobBean{

	private ApplicationContext applicationContext;
	
	private TomcatDao tomcatDao;
	
	private TomcatLogDao tomcatLogDao;
	
	private TomcatFlowDao tomcatFlowDao;
	
	private TomcatJvmMemoryDao tomcatJvmMemoryDao;
	
	private TomcatThreadDao tomcatThreadDao;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		int intervalTime = 5;
	
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		
		try {
			
			this.applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
			JobDataMap data = context.getJobDetail().getJobDataMap();
			for(Object key:data.keySet()){
				
				System.out.println(data.get(key));
				
			}
			
			tomcatDao = applicationContext.getBean("TomcatDao",TomcatDao.class);
			tomcatLogDao = applicationContext.getBean("TomcatLogDao",TomcatLogDao.class);
			tomcatFlowDao=applicationContext.getBean("TomcatFlowDao",TomcatFlowDao.class);
			tomcatJvmMemoryDao = applicationContext.getBean("TomcatJvmMemoryDao",TomcatJvmMemoryDao.class);
			tomcatThreadDao = applicationContext.getBean("TomcatThreadDao",TomcatThreadDao.class);
			
		} catch (SchedulerException e) {
			
			e.printStackTrace();
			return;
		}
		
		Tomcat templateTomcat = new Tomcat();
		templateTomcat.setIntervalTime(intervalTime);
		List<Tomcat> tomcats = tomcatDao.findEqualByEntity(templateTomcat, new String[]{"intervalTime"});
		
		//没有Tomcat监控配置
		if(tomcats==null||tomcats.size()==0){
			return;
		}
		
		for(Tomcat tomcat:tomcats){
			
			TomcatPaser paser = null;
			List<TomcatLog> logs = null;
			try {
				
				Criteria criteria = tomcatLogDao.createCriteria();
				criteria.setMaxResults(1);
				criteria.addOrder(Order.desc("beginTime"));
				logs = criteria.list();
				
				paser = TomcatPaser.newInstance(tomcat);
				
				/**查找最后的一条异常信息并判断该异常是否结束，否则则更新结束时间*/
				if(logs!=null&&logs.size()!=0){
					
					TomcatLog tomcatLog = logs.get(0);
					if(tomcatLog.getEndTime()==null){
						
						tomcatLog.setEndTime(new Date());
						tomcatLogDao.update(tomcatLog);
						
					}
					
				}
				
			} catch (Exception e) {
				
				if(logs==null||logs.size()==0||logs.get(0).getEndTime()!=null){
					
					TomcatLog tlog = new TomcatLog();
					tlog.setTomcat(tomcat);
					tlog.setBeginTime(new Date());
					tlog.setResult(TomcatException.CONNECT_REFUSE);
					tomcatLogDao.save(tlog);
					
				}
				
				return;
			}
			
			TomcatFlow flow = paser.getTomcatFlow();
			if(flow!=null){
				
				flow.setTomcat(tomcat);
				flow.setHour(hour);
				flow.setLogDate(new Date());
				tomcatFlowDao.save(flow);
				
				
			}
			
			TomcatJvmMemory jvmMemory = paser.getTomcatJvmMemeory();
			if(jvmMemory!=null){
				jvmMemory.setTomcat(tomcat);
				jvmMemory.setLogDate(new Date());
				tomcatJvmMemoryDao.save(jvmMemory);
			}
			
			
			TomcatThread thread = paser.getTomcatThread();
			if(thread!=null){
				thread.setTomcat(tomcat);
				thread.setLogDate(new Date());
				tomcatThreadDao.save(thread);
			}

		}

	}
	
}
