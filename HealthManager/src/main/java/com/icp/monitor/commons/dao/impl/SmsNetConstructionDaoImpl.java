package com.icp.monitor.commons.dao.impl;

import com.icp.monitor.commons.bean.SmsNetConstruction;
import com.icp.monitor.commons.core.DaoObjectV2Impl;
import com.icp.monitor.commons.dao.SmsNetConstructionDao;

public class SmsNetConstructionDaoImpl extends DaoObjectV2Impl<SmsNetConstruction, Integer> implements SmsNetConstructionDao{

	public SmsNetConstructionDaoImpl(Class<?> persistentClass) {
		super(persistentClass);
	}

}
