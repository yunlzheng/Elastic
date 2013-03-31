package com.cloud.elastic.commons.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


/**
 * 应用对象
 * @author 云龙
 * */
@Entity
@Table(name="t_application")
public class Application implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(length=32)
	private String uuid;
	
	/**应用名称*/
	@Column(name="name")
	private String name;
	
	/**应用绑定的域名*/
	@Column(name="url")
	private String url;
	
	/**运行时单元存放的地址*/
	@Column(name="repositoryUrl")
	private String repositoryUrl;
	
	/**应用状态*/
	@Column(name="health")
	private String health;
	
	/**应用使用的最小内存*/
	@Column(name="minMemory")
	private int minMemory;
	
	/**应用使用的最大内存*/
	@Column(name="maxMemory")
	private int maxMemory;
	

	/**应用所属的用户*/
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private User user;
	
	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public int getMinMemory() {
		return minMemory;
	}

	public void setMinMemory(int minMemory) {
		this.minMemory = minMemory;
	}

	public int getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(int maxMemory) {
		this.maxMemory = maxMemory;
	}
	
}
