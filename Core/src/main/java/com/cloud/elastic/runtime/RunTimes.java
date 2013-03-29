package com.cloud.elastic.runtime;

/**
 * 运行时环境核心API接口
 * */
public interface RunTimes {

	/**创建运行时单元*/
	public void createRunit();
	
	/**删除运行时单元*/
	public void destoryRunit();

	/**克隆运行时单元*/
	public void cloneRunit();
	
	/**启动运行时单元*/
	public void startRunit();
	
	/**停止运行时单元*/
	public void stopRunit();
	
}
