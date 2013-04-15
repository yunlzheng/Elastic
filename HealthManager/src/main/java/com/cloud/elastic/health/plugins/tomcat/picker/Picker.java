package com.cloud.elastic.health.plugins.tomcat.picker;

/**
 * 采集器接口
 * T1:监控主体
 * T2：监控项目
 * */
public interface Picker<T1,T2> {

	public T2 execute(T1 entity);
	
}
