package com.icp.monitor.commons.dao.impl;

import com.icp.monitor.commons.bean.SmsInfoConfig;
import com.icp.monitor.commons.core.DaoObjectV2Impl;
import com.icp.monitor.commons.dao.SmsInfoConfigDao;

public class SmsInfoConfigDaoImpl extends DaoObjectV2Impl<SmsInfoConfig, Integer> implements SmsInfoConfigDao{

	public SmsInfoConfigDaoImpl(Class<?> persistentClass) {
		super(persistentClass);
	}

}
