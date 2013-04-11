package com.icp.monitor.displayer.jobs;


import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.icp.monitor.commons.util.SystemUtil;

public class SpringQtzJobTestBean extends QuartzJobBean{

	private Logger log = Logger.getLogger(SpringQtzJobTestBean.class);
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		
		log.info(SystemUtil.getCurrentTime());

	}

}
