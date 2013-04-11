package com.cloud.elastic.commons.monitor.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**Tomcat流量信息日志*/
@Entity
@Table(name="t_tomcat_flow")
public class TomcatFlow implements Serializable{

	
	private static final long serialVersionUID = 1L;
	/**编号*/
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(length=32)
	private String uuid;
	/**每秒接收流量*/
	private long bytesReceived;
	/**每秒发送流量*/
	private long bytesSend;
	/**日志时间*/
	private Date logDate;
	/**所属Tomcat*/
	@OneToOne(fetch=FetchType.EAGER)
	private Tomcat tomcat;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public long getBytesReceived() {
		return bytesReceived;
	}
	public void setBytesReceived(long bytesReceived) {
		this.bytesReceived = bytesReceived;
	}
	public long getBytesSend() {
		return bytesSend;
	}
	public void setBytesSend(long bytesSend) {
		this.bytesSend = bytesSend;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public Tomcat getTomcat() {
		return tomcat;
	}
	public void setTomcat(Tomcat tomcat) {
		this.tomcat = tomcat;
	}
	
}
