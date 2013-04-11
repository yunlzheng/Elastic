package com.icp.monitor.displayer.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import au.com.bytecode.opencsv.CSVReader;

import com.icp.monitor.commons.bean.LBStatus;
import com.icp.monitor.commons.bean.LBStatusBackend;
import com.icp.monitor.commons.dao.LBStatusBackendDao;
import com.icp.monitor.commons.dao.LBStatusDao;
import com.icp.monitor.commons.util.SystemUtil;

public class LBStatusCollectTimeTask extends TimerTask implements ApplicationContextAware{

	private ApplicationContext context = null;
	private Log log = LogFactory.getLog(LBStatusCollectTimeTask.class);
	private LBStatusDao lbDao = null;
	private LBStatusBackendDao lbBackendDao = null;
	private List<String> noLogStringList = new ArrayList<String>();

	private String filters = null;
	private String lbserver = null;
	
	public String getFilters() {
		return filters;
	}

	public String getLbserver() {
		return lbserver;
	}

	@Value("#{SystemConfig['lbserver']}")
	public void setLbserver(String lbserver) {
		this.lbserver = lbserver;
	}

	@Value("#{SystemConfig['lbSvnameFilter']}")
	public void setFilters(String filters) {
		this.filters = filters;
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		
		context = arg0;
		lbDao = (LBStatusDao) context.getBean("ReceiverLBStatusDao");
		lbBackendDao = (LBStatusBackendDao) context.getBean("ReceiverLBStatusBackendDao");
		
		if (filters != null) {

			StringTokenizer st = new StringTokenizer(filters, ";");
			noLogStringList.clear();
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				noLogStringList.add(key);
				
			}
		}
		
	}

	public boolean checkFilter(String key) {

		return noLogStringList.contains(key);
	}
	
	@Override
	public void run() {

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

						LBStatusBackend lbbackend = new LBStatusBackend(nextLine);
						lbbackend.setJoinTime(joinTime);
						lbbackend.setDetailTime(detailTime);
						if(lbbackend.isValidation()){
							lbBackendDao.add(lbbackend);
						}
						
					}

				}
			} catch (Exception e) {

				log.error(e);

			}
		}




}
