package com.icp.monitor.commons.otherdao.impl;

import com.icp.monitor.commons.core.DaoObjectV2Impl;
import com.icp.monitor.commons.otherbean.Cluster;
import com.icp.monitor.commons.otherdao.ClusterDao;

public class ClusterDaoImpl extends DaoObjectV2Impl<Cluster,Integer> implements ClusterDao{

	public ClusterDaoImpl(Class persistentClass) {
		super(persistentClass);
		
	}

}
