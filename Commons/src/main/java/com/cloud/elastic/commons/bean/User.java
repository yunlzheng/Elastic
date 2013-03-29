package com.cloud.elastic.commons.bean;

/**
 * 租户用户对象
 * @author 云龙
 * */
public class User {

	/**用户编号*/
	private int id;
	
	/**用户昵称*/
	private String nickName;
	
	/**用户电子邮箱地址*/
	private String email;
	
	/**用户登录密码*/
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
