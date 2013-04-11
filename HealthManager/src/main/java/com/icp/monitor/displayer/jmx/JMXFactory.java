package com.icp.monitor.displayer.jmx;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * JMX信息获取工厂类
 * */
public class JMXFactory {

	private static JMXConnector connector = null;
	private static MBeanServerConnection  mBeanServerConnection = null;
	/**
	 * 获取JMX MBeanServerConnection的方法 需要用户认证
	 * @param authenticateName 用户名
	 * @param authenticatePwd 密码
	 * @param host JMX服务器IP地址
	 * @param port JMX服务器端口号
	 * @throws IOException 
	 * */
	@SuppressWarnings("unused")
	public static MBeanServerConnection getConnection(
			String authenticateName,
			String authenticatePwd,
			String host,
			String port) throws IOException{
		
		Map<String,String[]> map = new HashMap<String,String[]>();
		map.put("jmx.remote.credentials", new String[] { authenticateName,authenticatePwd });  
		String jmxURL = "service:jmx:rmi:///jndi/rmi://"+host+":"+port+"/jmxrmi";
		
		JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
		connector = JMXConnectorFactory.connect(serviceURL, map);
		mBeanServerConnection  = connector.getMBeanServerConnection();
		return mBeanServerConnection;
		
	}
	
	/**
	 * 获取JMX MBeanServerConnection的方法 不需要用户认证
	 * */
	@SuppressWarnings("unused")
	public static MBeanServerConnection getConnection(
			String host,
			String port) throws IOException{

		String jmxURL = "service:jmx:rmi:///jndi/rmi://"+host+":"+port+"/jmxrmi";
		JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
		connector = JMXConnectorFactory.connect(serviceURL);
		mBeanServerConnection  = connector.getMBeanServerConnection();
		return mBeanServerConnection;
		
	}
	
}
