package com.icp.monitor.displayer.jmx;

import java.io.IOException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import javax.management.MBeanServerConnection;

/**
 * 获取操作系统相关信息的类
 * */
public class JavaLangManager {

	private MBeanServerConnection mbsc = null;
	
	private ClassLoadingMXBean classLoadingMXBean = null;
	private CompilationMXBean compilationMXBean = null;
	private MemoryMXBean memMXBean = null;
	private OperatingSystemMXBean opMXBean = null;
	private RuntimeMXBean runtimeMXBean = null;
	private ThreadMXBean threadMXBean = null;
	
	public JavaLangManager(String authenticateName,
			String authenticatePwd,
			String host,
			String port ){
		
		try {
			
			mbsc = JMXFactory.getConnection(authenticateName, authenticatePwd, host, port);
			
			opMXBean = ManagementFactory.newPlatformMXBeanProxy(mbsc, 
					ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, 
					OperatingSystemMXBean.class);
			
			memMXBean = ManagementFactory.newPlatformMXBeanProxy(mbsc, 
					ManagementFactory.MEMORY_MXBEAN_NAME, 
					MemoryMXBean.class);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void getMemoruMXBeanInfo(){
		
		//堆内存信息
		MemoryUsage heap = memMXBean.getHeapMemoryUsage();
		//非堆内存信息
		MemoryUsage noheap = memMXBean.getNonHeapMemoryUsage();
		
		heap.getInit();
		heap.getUsed();
		heap.getMax();
		heap.getCommitted();
		
		noheap.getInit();
		noheap.getUsed();
		noheap.getMax();
		noheap.getCommitted();
		
	
	}
	
	public static void main(String[] args) {
		
		JavaLangManager javaLang = new JavaLangManager("huacloud","123456","192.168.0.105","9004");
		javaLang.getMemoruMXBeanInfo();
		
	}
	
}
