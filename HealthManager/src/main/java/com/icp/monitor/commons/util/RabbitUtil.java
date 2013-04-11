package com.icp.monitor.commons.util;

import java.io.IOException;

import com.icp.monitor.commons.snmp.core.MonitorType;
import com.icp.monitor.commons.snmp.core.QueueType;
import com.icp.monitor.commons.util.IpUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * 管理通道: management-[监控子系统IP地址]
 * 监控通道:monitor-[监控子系统IP地址]-[受控服务器IP地址]-[监控项]
 * 
 * */
public class RabbitUtil {

	public static ConnectionFactory createDefaultFactory(String host) {

		// 创建链接工厂
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		return factory;

	}

	/**
	 * @param queue_type 消息通道类型 管理型，监控性
	 * @param 监控类型 cpu,memory等等，如果queue_type=managerment 该值则为空
	 * @param 受控服务器IP地址
	 * */
	public static String createDefaultQueueName(String queue_type,
			String monitor_type, String mip) {

		String ip = IpUtil.getLocalIpAddress();
		
		StringBuilder doc = new StringBuilder();

		if (queue_type.equals(QueueType.MONITOR)) {

			doc.append(QueueType.MONITOR).append("-").append(ip).append("-")
					.append(mip).append("-");

			if (monitor_type != null) {

				if (monitor_type.equals(MonitorType.CPU))
					doc.append(MonitorType.CPU);
				if (monitor_type.equals(MonitorType.MEMORY))
					doc.append(MonitorType.MEMORY);
				if (monitor_type.equals(MonitorType.DISK))
					doc.append(MonitorType.DISK);
				if (monitor_type.equals(MonitorType.CPULOAD))
					doc.append(MonitorType.CPULOAD);
				if (monitor_type.equals(MonitorType.IO))
					doc.append(MonitorType.IO);
				if(monitor_type.equals(MonitorType.SYSTEMLOAD))
					doc.append(MonitorType.SYSTEMLOAD);
				if(monitor_type.equals(MonitorType.NETWORK))
					doc.append(MonitorType.NETWORK);

			}

			return doc.toString();

		} else if (queue_type.equals(QueueType.MANAGERMENT)) {

			doc.append(QueueType.MANAGERMENT).append("-").append(ip);
			return doc.toString();

		} else {

			return null;
		}

	}
	
	/**
	 * @param queue_type 消息通道类型 管理型，监控性
	 * @param 监控类型 cpu,memory等等，如果queue_type=managerment 该值则为空
	 * @param mip 受控服务器IP地址
	 * @param ip 信息采集子系统IP地址
	 * */
	public static String createDefaultQueueName(String queue_type,
			String monitor_type,String ip, String mip) {

		StringBuilder doc = new StringBuilder();

		if (queue_type.equals(QueueType.MONITOR)) {

			doc.append(QueueType.MONITOR).append("-").append(ip).append("-")
					.append(mip).append("-");

			if (monitor_type != null) {

				if (monitor_type.equals(MonitorType.CPU))
					doc.append(MonitorType.CPU);
				if (monitor_type.equals(MonitorType.MEMORY))
					doc.append(MonitorType.MEMORY);
				if (monitor_type.equals(MonitorType.DISK))
					doc.append(MonitorType.DISK);
				if (monitor_type.equals(MonitorType.CPULOAD))
					doc.append(MonitorType.CPULOAD);
				if(monitor_type.equals(MonitorType.IO))
					doc.append(MonitorType.IO);
				if(monitor_type.equals(MonitorType.NETWORK))
					doc.append(MonitorType.NETWORK);
				if(monitor_type.equals(MonitorType.SYSTEMLOAD))
					doc.append(MonitorType.SYSTEMLOAD);



			}

			return doc.toString();

		} else if (queue_type.equals(QueueType.MANAGERMENT)) {

			doc.append(QueueType.MANAGERMENT).append("-").append(ip);
			return doc.toString();

		} else {

			return null;
		}

	}
	
	public static void Send(String host,String queuename,String message) throws IOException{
		
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(host);
	    System.out.println(factory.getPort());
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    channel.queueDeclare(queuename, false, false, false, null);
	    channel.basicPublish("", queuename, null, message.getBytes());
	    System.out.println(" [x] Sent '" + message + "'");
	    channel.close();
	    connection.close();
		
	}
	
	public static void Recv(String host,String queuename) throws IOException{
		
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(host);
	    System.out.println(factory.getPort());
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    channel.queueDeclare(queuename, false, false, false, null);
	    channel.queueBind(queuename, "iaasp_appserver_q", "");
	    
	  
	    channel.close();
	    connection.close();
		
	}
	
	
	
	public static void main(String[] args) {
		
		try {
			RabbitUtil.Recv("iaasp.wocloud.com", "vm-temp-appserver");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
