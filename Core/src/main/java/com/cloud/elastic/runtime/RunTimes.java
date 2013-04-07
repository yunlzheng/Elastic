package com.cloud.elastic.runtime;

/**
 * 运行时环境核心API接口
 * */
public interface RunTimes {

	/**创建运行时单元
	 * @throws Exception */
	public void createRunit() throws Exception;
	
	/**删除运行时单元*/
	public void destoryRunit();

	/**克隆运行时单元
	 * @throws Exception */
	public void cloneRunit() throws Exception;
	
	/**启动运行时单元*/
	public void startRunit();
	
	/**停止运行时单元*/
	public void stopRunit();
	
}
