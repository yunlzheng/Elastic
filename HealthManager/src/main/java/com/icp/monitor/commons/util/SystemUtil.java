package com.icp.monitor.commons.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * 操作系统信息获取工具类
 * */
public class SystemUtil {

	/**
	 * 返回当前操作系统类型
	 * 
	 * @return linux/windows
	 * */
	public static String getLocalSystem() {

		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if (os.startsWith("win") || os.startsWith("Win")) {
			return "windows";
		} else {
			return "linux";
		}

	}

	public static String getHostName() {

		String hostName = getLocalSystem() + new Date();
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {

			e.printStackTrace();
			
		}

		return hostName;

	}

	public static String getCurrentTime() {

		SimpleDateFormat dateformat1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String a1 = dateformat1.format(new Date());
		return a1;

	}

	/**
	 * 得到当天日期
	 * */
	public static String getToday() {

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DATE);
		StringBuilder sb = new StringBuilder();
		sb.append(year);
		sb.append("-");
		if (month < 10) {
			sb.append("0");
		}
		sb.append(month);
		sb.append("-");
		if(day<10){
			sb.append("0");
		}
		sb.append(day);

		return sb.toString();

	}
	
	/**
	 * 得到昨天时间
	 * */
	public static String getYesterday() {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); // 得到前一天
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DATE);
		StringBuilder sb = new StringBuilder();
		sb.append(year);
		sb.append("-");
		if (month < 10) {
			sb.append("0");
		}
		sb.append(month);
		sb.append("-");
		if(day<10){
			sb.append("0");
		}
		sb.append(day);

		return sb.toString();

	}

	/**
	 * 得到上周时间
	 * */
	public static String getLastWeek() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -6); // 得到前一天
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DATE);
		
		
		StringBuilder sb = new StringBuilder();
		sb.append(year);
		sb.append("-");
		if (month < 10) {
			sb.append("0");
		}
		sb.append(month);
		sb.append("-");
		if(day<10){
			sb.append("0");
		}
		sb.append(day);

		return sb.toString();
		
	}

	/**
	 * 得到上月时间
	 * */
	public static String getLastMonth() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1); // 得到前一天
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DATE);
		
		
		StringBuilder sb = new StringBuilder();
		sb.append(year);
		sb.append("-");
		if (month < 10) {
			sb.append("0");
		}
		sb.append(month);
		sb.append("-");
		if(day<10){
			sb.append("0");
		}
		sb.append(day);

		return sb.toString();
		

	}

	public static void main(String[] args) {
		
		SystemUtil.getToday();
		SystemUtil.getYesterday();
		SystemUtil.getLastWeek();
		SystemUtil.getLastMonth();
	
	}

}
