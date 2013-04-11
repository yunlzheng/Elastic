package com.icp.monitor.commons.util;

public class LevelHelper {

	public static int getLevel(float total,float hold,float cur){
		
		float level = (cur-hold)/hold*10;
		return 0;
	}
	
	public static void main(String[] args) {
		
		LevelHelper.getLevel(100, 80, 90);
		
	}
}
