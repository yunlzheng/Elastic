package org.Runtimes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.cloud.elastic.runtimes.core.Configuration;
import com.cloud.elastic.runtimes.core.TomcatAvailableConfiguration;

/**
 * Java反射测试
 * */
public class MethonRel {

	public static void main(String[] args) {
		
		try {
			
			Method method1 = TomcatAvailableConfiguration.class.getMethod("Configuration2", new Class[]{});
			Configuration config = (Configuration) method1.invoke(null, new   Object[]{});
			System.out.println(config.getPort_ajp());
			
			Method[] methods = TomcatAvailableConfiguration.class.getMethods();
			for(int i=0;i<methods.length;i++){
				
				Method _method = methods[i];
				if(_method.getName().indexOf("Configuration")!=-1){
					
					System.out.println(_method.getName());
					
				}
				
			}
			
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
