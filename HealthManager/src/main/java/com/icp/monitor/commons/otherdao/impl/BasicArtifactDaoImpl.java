package com.icp.monitor.commons.otherdao.impl;

import com.icp.monitor.commons.core.DaoObjectV2Impl;

import com.icp.monitor.commons.otherbean.BasicArtifact;
import com.icp.monitor.commons.otherdao.BasicArtifactDao;

public class BasicArtifactDaoImpl extends DaoObjectV2Impl<BasicArtifact,Integer> implements BasicArtifactDao{

	public BasicArtifactDaoImpl(Class persistentClass) {
		super(persistentClass);
		
	}

}
