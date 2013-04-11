package com.icp.monitor.commons.dao.impl;

import com.icp.monitor.commons.bean.OpenAccount;
import com.icp.monitor.commons.dao.OpenAccountDao;
import com.icp.monitor.commons.core.DaoObjectV2Impl;

public class OpenAccountDaoImpl extends DaoObjectV2Impl<OpenAccount,Integer> implements OpenAccountDao{

	public OpenAccountDaoImpl(Class<?> persistentClass) {
		super(persistentClass);
	}
	
}
