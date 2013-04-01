package com.cloud.elastic.runtimes.info;

public class Info {

	public static final String version="0.0.1";
	public static final String author = "zheng";
	
	private String uuid;
	private boolean ready;
	
	public Info(String uuid,boolean ready){
		
		this.uuid = uuid;
		this.ready = ready;
		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	
	
}
