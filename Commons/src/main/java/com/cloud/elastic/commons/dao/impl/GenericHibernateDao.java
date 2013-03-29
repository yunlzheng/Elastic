package com.cloud.elastic.commons.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cloud.elastic.commons.dao.GenericDao;

/**
 * @author 云龙
 * */
public class GenericHibernateDao <T extends Serializable,PK extends Serializable>
	extends HibernateDaoSupport implements GenericDao<T, PK>{

	private Class<T> entityClass;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GenericHibernateDao(){
		
		this.entityClass = null;
        Class<? extends GenericHibernateDao> c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
	}
	
	public T get(PK id) {
		
		return getHibernateTemplate().get(entityClass, id);
	}
	
	public T load(PK id) {
		
		 return getHibernateTemplate().load(entityClass, id);
	}

	public List<T> loadAll() {
		
		return (List<T>)getHibernateTemplate().loadAll(entityClass);
	}

	public void update(T entity) {
		
		getHibernateTemplate().update(entity);
		
	}

	public void save(T entity) {
		
		getHibernateTemplate().save(entity);
		
	}

	public void saveOrUpdate(T entity) {
		
		getHibernateTemplate().saveOrUpdate(entity);
		
	}

	public void saveOrUpdateAll(Collection<T> entites) {
		
		getHibernateTemplate().saveOrUpdateAll(entites);
		
	}

	public void delete(T entity) {
		
		getHibernateTemplate().delete(entity);
		
	}

	public void deleteByKey(PK id) {
		
		   this.delete(this.load(id));
		
	}

	public void deleteAll(Collection<T> entities) {
		
		getHibernateTemplate().deleteAll(entities);
		
	}

	public int bulkUpdate(String queryString) {
		
		 return getHibernateTemplate().bulkUpdate(queryString);
	
	}

	public int bulkUpdate(String queryString, Object[] values) {
		 
		return getHibernateTemplate().bulkUpdate(queryString, values);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String queryString) {
		
		return getHibernateTemplate().find(queryString);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(String queryString, Object[] values) {
		 return getHibernateTemplate().find(queryString, values);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(DetachedCriteria criteria) {
		 return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(DetachedCriteria criteria, int firstResult,
			int maxResults) {
		
		  return getHibernateTemplate().findByCriteria(criteria, firstResult,
	                maxResults);
	}

	public Integer getRowCount(DetachedCriteria criteria) {
		
		criteria.setProjection(Projections.rowCount());
        List<T> list = this.findByCriteria(criteria, 0, 1);
        return (Integer) list.get(0);
	}
	
	

}
