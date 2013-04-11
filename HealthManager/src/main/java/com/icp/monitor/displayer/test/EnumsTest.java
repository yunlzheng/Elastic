package com.icp.monitor.displayer.test;

import com.icp.monitor.displayer.enums.ExceptionEmuns;

public class EnumsTest {

	public static void main(String[] args) {
		
		for(ExceptionEmuns exception : ExceptionEmuns.values()){
			
			System.out.println(exception);
		}
		
	}
}
