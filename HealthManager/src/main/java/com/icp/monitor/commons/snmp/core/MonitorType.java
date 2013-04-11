package com.icp.monitor.commons.snmp.core;

/**
 * @author zheng
 * */
public class MonitorType {

	/**
	 * CPU
	 * */
	public static final String CPU = "cpu";
	
	/**
	 * 内存
	 * */
	public static final String MEMORY = "memory";
	
	/**
	 * 硬盘
	 * */
	public static final String DISK = "disk";
	/**
	 * 取消对服务器某项监控
	 */
	public static final String CANCELCPU="cancel_cpu";
	public static final String CANCELDISK="cancel_disk";
	public static final String CANCELCPULOAD="cancel_cpuload";
	public static final String CANCELIO="cancel_io";
	public static final String CANCELMEMORY="cancel_memory";
	public static final String CANCELSYSTEMLOAD="cancel_systemload";
	/**
	 * CPULOAD
	 */
	public static final String CPULOAD="cpuload";
	/**
	 * IO
	 */
	public static final String IO="io";
	/**
	 * 系统负载
	 */
	public static final String SYSTEMLOAD="systemload";
	/**
	 * 网络
	 */
	public static final String NETWORK="network";
}
