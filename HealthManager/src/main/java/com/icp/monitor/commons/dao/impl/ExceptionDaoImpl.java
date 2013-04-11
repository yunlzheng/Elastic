package com.icp.monitor.commons.dao.impl;

import com.icp.monitor.commons.bean.ExceptionMessage;
import com.icp.monitor.commons.core.DaoObjectV2Impl;
import com.icp.monitor.commons.dao.ExceptionDao;

public class ExceptionDaoImpl extends DaoObjectV2Impl<ExceptionMessage, Integer> implements ExceptionDao{

	public ExceptionDaoImpl(Class<?> persistentClass) {
		super(persistentClass);
	}

}
