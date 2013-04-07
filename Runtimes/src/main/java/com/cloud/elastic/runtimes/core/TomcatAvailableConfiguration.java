package com.cloud.elastic.runtimes.core;


/**
 *  运行时单元可用的端口
 * */
public class TomcatAvailableConfiguration {
	
	public static Configuration Configuration1(){
		
		return new Configuration(8006,8081,8010);
		
	}
	
	public static Configuration Configuration2(){
		
		return new Configuration(8007,8082,8011);
		
	}
	
	public static Configuration Configuration3(){
		
		return new Configuration(8008,8083,8012);
		
	}
	
	public static Configuration Configuration4(){
		
		return new Configuration(8009,8084,8013);
		
	}
	
	public static Configuration Configuration5(){
		
		return new Configuration(8405,8085,8014);
		
	}
	
	public static Configuration Configuration6(){
		
		return new Configuration(8406,8086,8015);
		
	}	
	
	public static Configuration Configuration7(){
		
		return new Configuration(8407,8087,8017);
		
	}	
	
	public static Configuration Configuration8(){
		
		return new Configuration(8408,8088,8018);
		
	}
	
}
