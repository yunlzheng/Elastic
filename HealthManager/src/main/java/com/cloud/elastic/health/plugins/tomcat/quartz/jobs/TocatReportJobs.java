package com.cloud.elastic.health.plugins.tomcat.quartz.jobs;

import java.util.Calendar;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 生成报表Tomcat报表的后台处理任务
 * */
public class TocatReportJobs extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		Calendar calendar = Calendar.getInstance();
		
//		int month = calendar.get(Calendar.MONTH);
//		int hour = calendar.get(Calendar.HOUR_OF_DAY);
//		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		//int targetMonth = month-1;
		//int targetHour = hour-1;
		//int targetDay = day-1;
		
		
		
	}

}
