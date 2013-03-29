package com.cloud.elastic.router;

/**
 * Router核心接口
 * */
public interface Router {

	/**
	 * 注册应用访问信息
	 * */
	public void registe();
	
	/**
	 * 注销应用访问信息
	 * */
	public void unregiste();
	
}
