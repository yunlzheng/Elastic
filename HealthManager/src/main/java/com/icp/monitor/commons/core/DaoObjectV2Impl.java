package com.icp.monitor.commons.core;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



public class DaoObjectV2Impl<T extends Serializable, PK extends Serializable>
		extends HibernateDaoSupport implements DaoObjectV2<T, PK> {

	Log log = LogFactory.getLog(DaoObjectV2Impl.class);
	// 实体类类型
	private Class<T> entityClass = null;

	public DaoObjectV2Impl(Class persistentClass) {

		this.entityClass = persistentClass;

	}

	/**
	 * 添加
	 * */
	public void add(T obj) {

		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(obj);
			tx.commit();
			
			session.flush();  
			session.close();
		}catch(Exception e){
			
			log.error(e);
			tx.rollback();
			
		}finally{
			
			//session.close();
			
		}
		
		
	}

	/**
	 * 删除
	 * */
	public void delete(T obj) {

		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();
			
			session.flush();  
			session.close();
		}catch(Exception e){
			log.error(e);
			tx.rollback();
		}finally{
			
			//session.close();
			
		}
		
	}

	/**
	 * 根据Id删除
	 * */
	public void deleteById(PK id) {
		
		this.delete(get(id));
		
		
	}

	/**
	 * 更新
	 * */
	public void update(T obj) {
		
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.update(obj);
			tx.commit();
			
			session.flush();  
			session.close();
		}catch(Exception e){
			log.error(e);
			tx.rollback();
		}
		
	}

	/**
	 * 根据Id获取对象实例
	 * */
	public T get(PK id) {

		return this.getHibernateTemplate().get(entityClass, id);

	}

	/**
	 * 获取所有对象实例
	 * 
	 * @param queryString
	 *            查询字符串 例如 "from CpuLoadLog"
	 * */
	@SuppressWarnings("unchecked")
	public List<T> list(String queryString) {

		return this.getSession().createQuery(queryString).list();
	}

	
	
	@SuppressWarnings("unchecked")
	public List<T> list(String queryString, int first, int max) {
		Query query = this.getSession().createQuery(queryString);
		query.setFirstResult(first);
		query.setMaxResults(max);
		return query.list();
	}

	/**
	 * 根据提交分页获取对象实例
	 * */
	public List<T> getListForPage(final int offset, final int length,
			final Map<String, String> eqProperties,
			final Map<String, String> likeProperties,final Map<String,String> order) {

		@SuppressWarnings("unchecked")
		List<T> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback<List<T>>() {

					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {

						Criteria crit = session.createCriteria(entityClass);
						crit.add(Restrictions.allEq(eqProperties));
						Set<String> keySet = likeProperties.keySet();
						Iterator<String> it = keySet.iterator();
						while (it.hasNext()) {

							String key = it.next();
							String value = likeProperties.get(key);
							crit.add(Restrictions.like(key, value,
									MatchMode.ANYWHERE));

						}
						
						if(order!=null){
							
							String type = order.get("type");
							String key = order.get("key");
							if(type!=null&&key!=null){
								
								if(type.equals("desc")){
									
									
									crit.addOrder(Order.desc(key));
									log.debug("desc:+"+key);
									
								}else if(type.equals("asc")){
									
									crit.addOrder(Order.asc(key));
									log.debug("asc:+"+key);
								}
								
							}
							
						}

						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						crit.setFirstResult(offset);
						crit.setMaxResults(length);
						List<T> page = crit.list();
						return page;

					}

				});

		return list;

	}

	public Long getCount(Map eqProperties,Map likeProperties) {
		
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(entityClass);
		criteria.setProjection(Projections.rowCount());
		
		criteria.add(Restrictions.allEq(eqProperties));
		
		for(Object key:likeProperties.keySet()){
			
			String _key = key.toString();
			criteria.add(Restrictions.like(_key, likeProperties.get(key)));
			
		}
		
		List result = criteria.list();
		return  (Long) result.get(0);
		
		
	}

	
	public List<T> getList(final Map eqProperties,
			final Map likeProperties,final Map orderProperties) {
		
		@SuppressWarnings("unchecked")
		List<T> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback<List<T>>() {

					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {

						Criteria crit = session.createCriteria(entityClass);
						if(eqProperties!=null){
							crit.add(Restrictions.allEq(eqProperties));
						}
						if(likeProperties!=null){
							
							Set<String> keySet = likeProperties.keySet();
							Iterator<String> it = keySet.iterator();
							
							while (it.hasNext()) {

								String key = it.next();
								String value = likeProperties.get(key).toString();
								crit.add(Restrictions.like(key, value,
										MatchMode.ANYWHERE));

							}
						}
						if(orderProperties!=null){
							
							String type = (String) orderProperties.get("type");
							String key = (String) orderProperties.get("key");
							if(type!=null&&key!=null){
								
								if(type.equals("desc")){
									
									crit.addOrder(Order.desc(key));
									log.debug("desc:+"+key);
									
								}else if(type.equals("asc")){
									
									crit.addOrder(Order.asc(key));
									log.debug("asc:+"+key);
								}
								
							}
							
						}

						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						List<T> page = crit.list();
						return page;

					}

				});

		return list;
	}

	public List<String> getListDistinct(final String distincKey,final Class klass) {
		@SuppressWarnings("unchecked")
		List<String> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback<List<String>>() {
					public List<String> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(entityClass);
						crit.setProjection(Projections.distinct(Projections.property(distincKey)));
						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						List<String> page = crit.list();
						return page;

					}

				});

		return list;
	}

	
}
