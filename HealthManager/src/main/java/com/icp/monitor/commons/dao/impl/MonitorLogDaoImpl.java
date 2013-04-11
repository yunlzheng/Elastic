package com.icp.monitor.commons.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.icp.monitor.commons.bean.*;
import com.icp.monitor.commons.dao.*;
import com.icp.monitor.commons.core.*;

public class MonitorLogDaoImpl extends DaoObjectV2Impl<MonitorLog, Integer>
		implements MonitorLogDao {

	private Log log = LogFactory.getLog(MonitorLogDaoImpl.class);
	
	public MonitorLogDaoImpl(Class<?> persistentClass) {
		super(persistentClass);

	}

	@SuppressWarnings("unchecked")
	public List<MonitorLog> SelectByDate(String ip,Date beagin, Date end,boolean admin) {
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		
		List<MonitorLog> result = null;

		result = session
				.createQuery(
						"from MonitorLog log where log.ip=:ipaddr and log.joinTime>:beginTime and log.joinTime<=:endTime and log.admin="+admin)
				.setText("ipaddr", ip)
				.setTimestamp("beginTime", beagin).setTimestamp("endTime", end)
				.list();
		
		log.info("search ip "+ip +"begin "+beagin+"  end "+end +"  result:"+result.size());
		
		return result;
	}
}
