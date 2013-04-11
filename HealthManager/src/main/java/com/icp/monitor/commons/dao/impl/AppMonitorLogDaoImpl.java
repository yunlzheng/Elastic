package com.icp.monitor.commons.dao.impl;

import com.icp.monitor.commons.bean.AppMonitorLog;
import com.icp.monitor.commons.core.DaoObjectV2Impl;
import com.icp.monitor.commons.dao.AppMonitorLogDao;

public class AppMonitorLogDaoImpl extends DaoObjectV2Impl<AppMonitorLog, Integer> implements AppMonitorLogDao{

	public AppMonitorLogDaoImpl(Class<?> persistentClass) {
		super(persistentClass);
	}

}
