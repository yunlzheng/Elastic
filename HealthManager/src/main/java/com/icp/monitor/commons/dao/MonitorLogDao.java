package com.icp.monitor.commons.dao;


import java.util.Date;
import java.util.List;

import com.icp.monitor.commons.bean.*;
import com.icp.monitor.commons.core.DaoObjectV2;

public interface MonitorLogDao extends DaoObjectV2<MonitorLog,Integer>{

	public List<MonitorLog> SelectByDate(String ip,Date beagin,Date end,boolean admin);
	
}
