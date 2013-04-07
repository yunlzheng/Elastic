package com.cloud.elastic.runtimes.core;

public class Configuration{
	
	/**停止端口*/
	private int port_shutdown;
	/**Http端口*/
	private int port_http;
	/**ajp端口*/
	private int port_ajp;
	
	public Configuration(int port_showdown,int port_http,int port_ajp){
		this.port_shutdown=port_showdown;
		this.port_http=port_http;
		this.port_ajp = port_ajp;
	}
	

	public int getPort_shutdown() {
		return port_shutdown;
	}


	public void setPort_shutdown(int port_shutdown) {
		this.port_shutdown = port_shutdown;
	}


	public int getPort_http() {
		return port_http;
	}
	
	public void setPort_http(int port_http) {
		this.port_http = port_http;
	}
	
	public int getPort_ajp() {
		return port_ajp;
	}
	
	public void setPort_ajp(int port_ajp) {
		this.port_ajp = port_ajp;
	}

}