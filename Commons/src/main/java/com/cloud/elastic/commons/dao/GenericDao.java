package com.cloud.elastic.commons.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 
 * Dao接口定义
 * @author 云龙
 * */
public interface GenericDao<T extends Serializable,PK extends Serializable> {

	/**根据主键查询实体*/
	public T get(PK id);
	
	public T load(PK id);
	
	/**获取全部实体*/
	public List<T> loadAll();
	
	/**更新实体类*/
	public void update(T entity);
	
	/**存储实体到数据库*/
	public void save(T entity);
	
	/**增加或者更新实体*/
	public void saveOrUpdate(T entity);
	
	/**添加或者更新所有实体类*/
	public void saveOrUpdateAll(Collection<T> entites);
	
	/**删除指定实体*/
	public void delete(T entity);
	
	/**根据ID删除实体*/
	public void deleteByKey(PK id);
	
	/**删除集合中的全部实体*/
	public void deleteAll(Collection<T> entitys);
	
	/**直接使用HSQL语句增加，更新，删除实体*/
	public int bulkUpdate(String queryString);
	
	/**使用带参数的HSQL语句增加，更新，删除实体*/
	public int bulkUpdate(String queryString,Object[] values);
	
	/**使用HSQL语句检索数据*/
	public List<T> find(String queryString);
	
	/**使用带参数的HSQL语句检索数据*/
    public List<T> find(String queryString, Object[] values);
	
    /**使用指定的检索标准检索数据*/
    public List<T> findByCriteria(DetachedCriteria criteria);
    
    /**使用指定的检索标准检索数据，返回部分记录*/
    public List<T> findByCriteria(DetachedCriteria criteria, int firstResult,
            int maxResults);


    /**使用指定的检索标准检索数据，返回指定范围的记录*/
    public Integer getRowCount(DetachedCriteria criteria);
    
}
