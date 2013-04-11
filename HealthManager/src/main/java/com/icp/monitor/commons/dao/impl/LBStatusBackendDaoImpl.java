package com.icp.monitor.commons.dao.impl;


import com.icp.monitor.commons.bean.LBStatusBackend;
import com.icp.monitor.commons.core.DaoObjectV2Impl;
import com.icp.monitor.commons.dao.LBStatusBackendDao;

public class LBStatusBackendDaoImpl extends DaoObjectV2Impl<LBStatusBackend,Integer> implements LBStatusBackendDao{

	public LBStatusBackendDaoImpl(Class<?> persistentClass) {
		super(persistentClass);
	}

}
