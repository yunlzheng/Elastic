package com.icp.monitor.commons.dao.impl;

import com.icp.monitor.commons.bean.AlertMessage;
import com.icp.monitor.commons.core.DaoObjectV2Impl;
import com.icp.monitor.commons.dao.AlertMessageDao;

public class AlertMessageDaoImpl extends DaoObjectV2Impl<AlertMessage,Integer> implements AlertMessageDao{

	public AlertMessageDaoImpl(Class<?> persistentClass) {
		super(persistentClass);
	}

}
