package com.cloud.elastic.commons.bean;

public class HttpLog {

	/**URL地址*/
	private String url;
	
	/**URL对应的IP地址*/
	private String ip;
	
	/**Http状态码*/
	private int httpStatus;
	
	/**
	 * 响应的http头
	 * */
	private String httpHead;
	
	/**响应时间*/
	private long responseTime;
	
	/**下载字节数*/
	private long bytesSize;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getHttpHead() {
		return httpHead;
	}

	public void setHttpHead(String httpHead) {
		this.httpHead = httpHead;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public long getBytesSize() {
		return bytesSize;
	}

	public void setBytesSize(long bytesSize) {
		this.bytesSize = bytesSize;
	}
	
	
}
