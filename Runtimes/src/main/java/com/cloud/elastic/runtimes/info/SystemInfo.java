package com.cloud.elastic.runtimes.info;

public class SystemInfo {

	private static String instanceKey;
	
	public static void newInstance(String instanceKey){
		
		SystemInfo.instanceKey = instanceKey;
		
	}
	
	public static String getInstanceKey(){
		return SystemInfo.instanceKey;
	}
	
}
