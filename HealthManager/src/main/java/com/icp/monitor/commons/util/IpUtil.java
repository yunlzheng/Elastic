package com.icp.monitor.commons.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 用于获取本机IP地址的工具类
 * 
 * @author zhengyl
 * */
public class IpUtil {

	/**
	 * 返回本机IP地址
	 * 
	 * @return Ip Address
	 * @throws UnknownHostException
	 * */
	public static String getLocalIpAddress() {

		if (SystemUtil.getLocalSystem().equals("windows")) {

			InetAddress[] ia = null;
			try {
				ia = InetAddress.getAllByName(InetAddress.getLocalHost()
						.getHostName());
			} catch (UnknownHostException e) {

				e.printStackTrace();
				return "localhost";

			}
			return ia[0].getHostAddress().toString();

		} else {

			return printIp();
		}


	}

	
	private static String printIp() {  
		  
        try {  
            for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();) {  
                NetworkInterface item = e.nextElement();  
  
               // System.out.println(item.toString());  
               // System.out.println(item.getMTU() + " " + item.isLoopback() + " " + item.isPointToPoint() + " " + item.isUp() + " " + item.isVirtual());  
  
                for (InterfaceAddress address : item.getInterfaceAddresses()) {  
                	
                    if (address.getAddress() instanceof Inet4Address) {  
                    	
                        Inet4Address inet4Address = (Inet4Address) address.getAddress();  
                       
                        if(!inet4Address.getHostAddress().equals("127.0.0.1")){
                        	 System.out.println(inet4Address.getHostAddress());
                        	return inet4Address.getHostAddress();
                        }
                        //System.out.println(inet4Address.isLinkLocalAddress() + " " + inet4Address.isLoopbackAddress() + " " + inet4Address.isMCGlobal() + " " + inet4Address.isMulticastAddress());  
                    }  
                
                }  
            }  
        } catch (Exception ex) {  
        	ex.printStackTrace();
        }  
        
        return "127.0.0.1";
    }  
    public static void main(String[] args) {  
        printIp();  
    }  
	


}
