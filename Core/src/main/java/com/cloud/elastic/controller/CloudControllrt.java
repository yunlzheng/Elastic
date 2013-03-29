package com.cloud.elastic.controller;

/**
 * 控制器核心借口
 * */
public interface CloudControllrt {

	/**部署应用*/
	public void deploy();
	
	/**卸载应用*/
	public void undeploy();
	
	/**启动应用**/
	public void start();
	
	/**停止应用*/
	public void stop();
	
	/**扩展应用*/
	public void expand();
	
	/**收缩应用*/
	public void shrink();
	
}
