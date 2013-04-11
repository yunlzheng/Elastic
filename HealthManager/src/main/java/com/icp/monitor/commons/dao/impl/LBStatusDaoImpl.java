package com.icp.monitor.commons.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.icp.monitor.commons.bean.LBStatus;
import com.icp.monitor.commons.core.DaoObjectV2Impl;
import com.icp.monitor.commons.dao.LBStatusDao;

public class LBStatusDaoImpl extends DaoObjectV2Impl<LBStatus,Integer> implements LBStatusDao{

	public LBStatusDaoImpl(Class<?> persistentClass) {
		super(persistentClass);
	}


	@SuppressWarnings("unchecked")
	public List<LBStatus> ListByDateAndPxname(Date _begin, Date _end,
			String pxname) {
	List<LBStatus> result = null;
		
		Session session = this.getHibernateTemplate().getSessionFactory()
				.openSession();
		
		result = session
				.createQuery(
						"from VMStatus lb where lb.pxname=:pxname and lb.joinTime>:beginTime and lb.joinTime<=:endTime")
				.setText("pxname", pxname)
				.setTimestamp("beginTime", _begin).setTimestamp("endTime", _end)
				.list();
		
		
		return result;
	}


}
