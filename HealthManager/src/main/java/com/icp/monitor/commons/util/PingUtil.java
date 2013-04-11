package com.icp.monitor.commons.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Ping 工具类
 * */
public class PingUtil {

	/**
	 * ping方法 
	 * @param ip 目标地址
	 * @param count 次数
	 * */
	public static void ping(String ip,int count){

		
		if (SystemUtil.getLocalSystem().equals("linux")) {

			try {
				// 获取所ping的IP进程
				Process pro = Runtime.getRuntime().exec("ping " + ip + " -c "+count);
				BufferedReader buf = new BufferedReader(new InputStreamReader(
						pro.getInputStream()));
				String line = buf.readLine();
				while (line != null) {

					if (line != null && (!line.equals(""))) {

						if (line.indexOf("64 bytes from") != -1) {
							System.out.println(line);
						}

					}

					
					line = buf.readLine();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(SystemUtil.getLocalSystem().equals("windows")){
			
			System.out.println("Linux");
			
		}else{
			
			System.out.println("unknown system");
			
		}

	}
	
	public static void main(String[] args){
		PingUtil.ping("www.baidu.com", 3);
	}

}
