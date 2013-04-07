package com.cloud.elastic.runtimes.rabbit.message;

public class ApplicationMessage {

	/**消息命令* */
	private String action;
	
	/**应用编号*/
	private String uuid;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
