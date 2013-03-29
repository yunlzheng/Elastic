package com.cloud.elastic.commons.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 租户用户对象
 * @author 云龙
 * */
@Entity
@Table(name="t_user")
public class User implements Serializable{

	
	private static final long serialVersionUID = 1L;

	/**用户编号*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	/**用户昵称*/
	@Column(name="nick_name")
	private String nickName;
	
	/**用户电子邮箱地址*/
	@Column(name="email")
	private String email;
	
	/**用户登录密码*/
	@Column(name="passwrod")
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
