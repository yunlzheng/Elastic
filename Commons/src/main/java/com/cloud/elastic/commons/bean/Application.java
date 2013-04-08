package com.cloud.elastic.commons.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
	
	/**
	 * 应用状态
	 * @see Health
	 * */
	@Column(name="health")
	private int health;
	
	@Column(name="operatStatus")
	private int operatStatus = OperatStatus.STABLE.getStatus();
	
	/**应用使用的最小内存*/
	@Column(name="minMemory")
	private int minMemory;
	
	/**应用使用的最大内存*/
	@Column(name="maxMemory")
	private int maxMemory;
	
	@Column(name="create_date")
	private String createDate;


	
	/**应用所属的用户*/
	@OneToOne(fetch=FetchType.EAGER)
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
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
	
	
	
	public int getOperatStatus() {
		return operatStatus;
	}

	public void setOperatStatus(int operatStatus) {
		this.operatStatus = operatStatus;
	}
	
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public enum OperatStatus{
		
		STABLE(0,"未进行任何操作"),
		DEPLOYING(1,"部署中"),
		STARTING(2,"启动中"),
		STOPING(3,"停止中"),
		UNDEPLOYED(4,"卸载中"),
		EXPANDING(5,"扩展中"),
		SHIRKING(6,"收缩中");
		
		private int status;
		private String desc;
		
		private OperatStatus(int status,String desc) {
			this.status = status;
			this.desc = desc;
		}
		
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}

	public enum Health{
		
		UNBINDED(100,"未绑定到运行环境"),
		BINDED(101,"没有运行任何实例"),
		RUNNING(102,"运行中"),
		STOPED(103,"停止");
		
		private int health;
		private String desc;
		
		private Health(int health,String desc){
			this.health=health;
			this.desc=desc;
		}

		public int getHealth() {
			return health;
		}

		public void setHealth(int health) {
			this.health = health;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
	}
	
	
}
