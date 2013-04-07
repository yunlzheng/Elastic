package com.cloud.elastic.controller;

/**
 * 控制器核心借口
 * */
public interface CloudController {

	/**部署应用*/
	public String deploy(String applicationId);
	
	/**卸载应用*/
	public String undeploy(String applicationId);
	
	/**启动应用**/
	public String start(String applicationId);
	
	/**停止应用*/
	public String stop(String applicationId);
	
	/**扩展应用*/
	public String expand(String applicationId);
	
	/**收缩应用*/
	public String shrink(String applicationId);
	
}
