package com.icp.monitor.commons.util;

public class AccountUtil {

	public static String getTenantName(String username){
		
		String tenantName = null;
		if(username.indexOf("@")!=-1){
			
			tenantName = username.substring(username.indexOf("@")+1,username.length());
			
		}
		
		return tenantName;
		
	}
	
	
}
