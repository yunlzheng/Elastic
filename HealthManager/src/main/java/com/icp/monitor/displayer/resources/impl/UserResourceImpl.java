package com.icp.monitor.displayer.resources.impl;


import javax.servlet.http.HttpServletRequest;
import com.icp.monitor.displayer.resources.UserResource;

public class UserResourceImpl implements UserResource{


	public String getLoginAccount(HttpServletRequest request){
		
		String account = (String) request.getSession().getAttribute("user");
		return	account;
	}



}
