package com.cloud.elastic.commons.bean;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 应用对象
 * @author 云龙
 * */
@Entity
@Table(name="t_application")
public class Application implements Serializable{

	
	private static final long serialVersionUID = 1L;

	/**RUnit编号*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
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
	
	@OneToMany(targetEntity=RUnit.class,cascade=CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="runitId",updatable=false)
	private Set<RUnit> rUnits;
	
	/**应用所属的用户*/
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Set<RUnit> getrUnits() {
		return rUnits;
	}

	public void setrUnits(Set<RUnit> rUnits) {
		this.rUnits = rUnits;
	}
	
	
	
}
