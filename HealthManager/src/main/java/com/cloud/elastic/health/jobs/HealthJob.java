package com.cloud.elastic.health.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cloud.elastic.commons.dao.ApplicationDao;
import com.cloud.elastic.commons.dao.RUnitDao;
import com.cloud.elastic.commons.dao.RuntimeDao;

public class HealthJob extends QuartzJobBean{

	private ApplicationContext applicationContext = null;
	private ApplicationDao applicationDao = null;
	private RuntimeDao runtimeDao = null;
	private RUnitDao runitDao = null;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		try {
			
			this.applicationContext  = (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
			applicationDao = this.applicationContext.getBean(ApplicationDao.class);
			runtimeDao = this.applicationContext.getBean(RuntimeDao.class);
			runitDao = this.applicationContext.getBean(RUnitDao.class);
			
			
			
		} catch (SchedulerException e) {
			
			e.printStackTrace();
			return;
			
		}
		
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	public RuntimeDao getRuntimeDao() {
		return runtimeDao;
	}

	public void setRuntimeDao(RuntimeDao runtimeDao) {
		this.runtimeDao = runtimeDao;
	}
	
	

}
