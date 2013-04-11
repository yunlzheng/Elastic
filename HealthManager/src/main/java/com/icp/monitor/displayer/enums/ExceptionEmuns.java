package com.icp.monitor.displayer.enums;

/**
 * 异常类型的枚举类
 * */
public enum ExceptionEmuns {

	CPUSHARE{
		
		@Override
		public String toString() {
			
			return "Cpu占有率";
		}
		
	},
	IOSHARE{
		
		@Override
		public String toString() {
			
			return "Io占有率";
		}
		
	},
	MEMSHARE{
		
		@Override
		public String toString() {
			
			return "内存占有率";
		}
		
	},
	DISKSHARE{
		
		@Override
		public String toString() {
			
			return "硬盘占有率";
		}
		
	},
	APPAVAILABLE{
		
		@Override
		public String toString() {
			
			return "应用可用性";
			
		}
		
	}
	
}
