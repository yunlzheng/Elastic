package com.icp.monitor.commons.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

public interface DaoObjectV2 <T extends Serializable,PK extends Serializable>{

	@Transactional
	public void add(T obj);
	
	@Transactional
	public void delete(T obj);
	
	@Transactional
	public void deleteById(PK id);
	
	@Transactional
	public void update(T obj);
	
	public Long getCount(Map eqProperties,Map likeProperties);
	
	public T get(PK id);
	public List<T> list(String queryString);
	
	public List<T> list(String queryString,int first,int max);
	
	/**
	 * @param orderProperties
	 * type:asc|desc
	 * key:
	 * */
	public List<T> getListForPage(final int offset, final int length,final Map<String, String> eqProperties, final Map<String, String> likeProperties,final Map<String,String> orderProperties);
	
	public List<T> getList(final Map eqProperties, final Map likeProperties,final Map orderProperties);
	
	/**查询取消重复项目*/
	public List<String> getListDistinct(final String distincKey,final Class klass);
}
