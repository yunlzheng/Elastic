package com.icp.monitor.commons.statistics;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 表示特定虚拟机日志记录中正常数据和异常数据的比例
 * */
@XmlRootElement(name="vmExceptionScale")
public class VmExceptionScale {

	/**虚拟机IP地址*/
	private String ip;
	
	/**正常记录条数*/
	private Long total;
	
	/**异常记录条数*/
	private Long exception;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getException() {
		return exception;
	}

	public void setException(Long exception) {
		this.exception = exception;
	}

}
