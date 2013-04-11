package com.icp.monitor.displayer.jmx;

import java.io.IOException;
import java.lang.management.MemoryUsage;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxTest {

	private static JMXConnector connector = null;
	private static MBeanServerConnection  mBeanServerConnection = null;
	private static String authenticateName = "huacloud";
	private static String authenticatePwd = "123456";
	
	private static void getConnection() throws IOException{
		
		Map<String,String[]> map = new HashMap<String,String[]>();
		map.put("jmx.remote.credentials", new String[] { authenticateName,authenticatePwd });  
		String jmxURL = "service:jmx:rmi:///jndi/rmi://192.168.0.105:9004/jmxrmi";
		JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
		connector = JMXConnectorFactory.connect(serviceURL, map);
		mBeanServerConnection  = connector.getMBeanServerConnection();
		
	}
	
	/**
	 * 获取所有ObjectName
	 * @throws IOException 
	 * @throws ReflectionException 
	 * @throws IntrospectionException 
	 * @throws InstanceNotFoundException 
	 * */
	public static void printObjectName() throws IOException, InstanceNotFoundException, IntrospectionException, ReflectionException{
		
		Set MBeanset = mBeanServerConnection.queryMBeans(null, null);
		Iterator MBeansetIterator = MBeanset.iterator();   
		while (MBeansetIterator.hasNext()) {   
		    ObjectInstance objectInstance = (ObjectInstance) MBeansetIterator   .next();   
		    ObjectName objectName = objectInstance.getObjectName();   
		    MBeanInfo objectInfo = mBeanServerConnection.getMBeanInfo(objectName);   
		    System.out.print("ObjectName:" + objectName.getCanonicalName()   + ".");   
		    System.out.print("mehtodName:");   
		    for (int i = 0; i < objectInfo.getAttributes().length; i++) {   
		        System.out.print(objectInfo.getAttributes()[i].getName()+ ",");   
		    }   
		    System.out.println("");
		}
		
	}
	
	/**
	 * @throws NullPointerException 
	 * @throws MalformedObjectNameException 
	 * @throws IOException 
	 * @throws ReflectionException 
	 * @throws IntrospectionException 
	 * @throws InstanceNotFoundException 
	 * @throws MBeanException 
	 * @throws AttributeNotFoundException 
	 * 
	 * */
	public static void  printCatalinaMemory() throws MalformedObjectNameException, NullPointerException, InstanceNotFoundException, IntrospectionException, ReflectionException, IOException, AttributeNotFoundException, MBeanException{
		
		ObjectName youngHeapObjName = new ObjectName("java.lang:type=MemoryPool,name=Eden Space");   
	    
	    MBeanInfo youngHeapInfo = mBeanServerConnection.getMBeanInfo(youngHeapObjName);   
	  
	    MBeanAttributeInfo[] youngHeapAttributes = youngHeapInfo.getAttributes();   
	           
	    MemoryUsage youngHeapUsage = MemoryUsage.from((CompositeData) mBeanServerConnection.getAttribute(youngHeapObjName, "Usage"));   
	           
        System.out.print("目前新生区分 配最大内存:"+youngHeapUsage.getMax()/1024+"KB,");   
        System.out.print("新生区已分配:"+youngHeapUsage.getCommitted()/1024+"KB,");   
        System.out.print("新生区初始化:"+youngHeapUsage.getInit()/1024+"KB,");   
        System.out.println("新生区已使用"+youngHeapUsage.getUsed()/1024+"KB");   
	
		
	}
	
	/**
	 * Tomcat部署的各应用的活动数
	 * @throws IOException 
	 * @throws ReflectionException 
	 * @throws MBeanException 
	 * @throws InstanceNotFoundException 
	 * @throws AttributeNotFoundException 
	 * */
	public static void printCatalinaThread() throws MalformedObjectNameException, NullPointerException, IOException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException{
		
			ObjectName managerObjName = new ObjectName("Catalina:type=Manager,*");   
	        Set<ObjectName> s = mBeanServerConnection.queryNames(managerObjName, null);   
	        for (ObjectName obj : s) {   
	               
	            ObjectName objname = new ObjectName(obj.getCanonicalName());   
	            System.out.print("objectName:"+objname);   
	            System.out.print(",最大会话数:"  + mBeanServerConnection.getAttribute(objname, "maxActiveSessions")+",");   
	            System.out.print("会话数:"  + mBeanServerConnection.getAttribute(objname, "activeSessions")+",");   
	            System.out.println("活动会话数:"  + mBeanServerConnection.getAttribute(objname, "sessionCounter"));  
	
	        }
		
	}
	
	
	public static void main(String[] args) throws Exception {
		JmxTest.getConnection();
		//JmxTest.printObjectName();
		JmxTest.printCatalinaMemory();
	}
	
}
