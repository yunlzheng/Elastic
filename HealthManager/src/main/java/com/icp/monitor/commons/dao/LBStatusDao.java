package com.icp.monitor.commons.dao;

import java.util.Date;
import java.util.List;

import com.icp.monitor.commons.bean.LBStatus;
import com.icp.monitor.commons.core.DaoObjectV2;



public interface LBStatusDao extends DaoObjectV2<LBStatus,Integer>{

	public List<LBStatus> ListByDateAndPxname(Date _begin, Date _end,String pxname);

}
