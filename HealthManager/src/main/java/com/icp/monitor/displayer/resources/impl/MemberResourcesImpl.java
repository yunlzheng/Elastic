package com.icp.monitor.displayer.resources.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.AlertMember;
import com.icp.monitor.commons.bean.Member;
import com.icp.monitor.commons.bean.Response;
import com.icp.monitor.commons.dao.AlertMemberDao;
import com.icp.monitor.commons.dao.MemberDao;
import com.icp.monitor.commons.dao.impl.MemberDaoImpl;
import com.icp.monitor.commons.util.SystemUtil;
import com.icp.monitor.displayer.resources.MemberResources;

public class MemberResourcesImpl implements MemberResources,
		ApplicationContextAware {

	private ApplicationContext context = null;

	/**
	 * 如果当前登录用户为空则返回测试数据
	 * 否则返回该用户所有的通知帐号信息
	 * */
	public List<Member> list(HttpServletRequest request) {

		HttpSession session = request.getSession();
		String userAccount = (String) session.getAttribute("user");
		if(userAccount==null){
			return null;
		}
		MemberDao memberDao = (MemberDao) context.getBean("MemberDao");
		List<Member> list = memberDao.list("from Member where creater='"+userAccount+"'");
		return list;

	}
	
	public List<Member> list(HttpServletRequest request,String userAccount) {
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){
			account = "guest@guest.com";
		}
		MemberDao memberDao = (MemberDao) context.getBean("MemberDao");
		List<Member> list = memberDao.list("from Member where creater='"+userAccount+"'");
		return list;
		
	}
	
	public Member get(int id) {

		MemberDao memberDao = (MemberDao) context.getBean("MemberDao");
		Member member = memberDao.get(id);
		return member;
	}

	public Member add(HttpServletRequest request,String name, String email, String tell, String creater) {
		
		Member member = new Member();
		MemberDao memberDao = (MemberDao) context.getBean("MemberDao");
		member.setEmail(email);
		member.setName(name);
		member.setTell(tell);
		member.setCreater(creater);
		member.setJoinTime(SystemUtil.getCurrentTime());
		
		try{
			memberDao.add(member);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return member;
	}

	public Member update(int id, String name, String email, String tell) {
		
		MemberDao memberDao = (MemberDao) context.getBean("MemberDao");
		Member member = memberDao.get(id);
		member.setId(id);
		member.setEmail(email);
		member.setName(name);
		member.setTell(tell);
		member.setJoinTime(SystemUtil.getCurrentTime());
		memberDao.update(member);
		return member;
	}
	
	public List<Member> checkList(HttpServletRequest request,String check) {
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){
			account="guest@guest.com";
		}
		MemberDao memberDao = (MemberDaoImpl) context.getBean("MemberDao");	
		Map<String,String> eqProperties = new HashMap<String,String>();
		
		Map<String, String> likeProperties = new HashMap<String, String>();
		if(!check.equals("null")){
			likeProperties.put("name", check);
		}
		
		eqProperties.put("creater", account);
		return memberDao.getList(eqProperties, likeProperties,null);
	}

	

	public Response delete(int id) {
		
		Response resp = new Response();
		
		try{
			
			MemberDao memberDao = (MemberDao) context.getBean("MemberDao");
			AlertMemberDao alertMemberDao =(AlertMemberDao)context.getBean("AlertMemberDao");
			
			Member obj = memberDao.get(id);
			resp.setKey(id+"");
			if(obj!=null){
				
			    List<AlertMember>  list = alertMemberDao.list("from AlertMember where mid="+id);
				for(int i=0;i<list.size();i++){
					
					alertMemberDao.delete(list.get(i));
					
				}
				memberDao.delete(obj);
				resp.setCode("200");
				resp.setStatus("success");
				
			}
			
		}catch(Exception e){
			
			resp.setCode("503");
			resp.setStatus("false");
		}
		
		return resp;
		
	}

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {

		this.context = context;

	}
	


	@SuppressWarnings("unchecked")
	public List<Member> listConfig(HttpServletRequest request, String ip) {
		
		AlertMemberDao dao = (AlertMemberDao) context.getBean("AlertMemberDao");
		MemberDao memberDao = (MemberDao)context.getBean("MemberDao");
		
		/**
		 * 登录用户信息
		 * */
		String account = (String) request.getSession().getAttribute("user");
		
		boolean isadmin = false;
		if(account==null){account="guest@guest.com";}
		if(account.indexOf("@")==-1){
			isadmin=true;
		}
		
		/**
		 * 获取IP为ip的服务器配置的所有预警人员
		 * */
		@SuppressWarnings("rawtypes")
		Hashtable eqProperties = new Hashtable();
		eqProperties.put("ip", ip);
		eqProperties.put("admin", isadmin);
		@SuppressWarnings("rawtypes")
		Hashtable likeProperties = new Hashtable();
		
		List<AlertMember> alertlist = dao.getList(eqProperties , likeProperties,null);
		
		List<Member> result = new ArrayList<Member>();
		
		for(AlertMember alertMem:alertlist){
			
			Member member = memberDao.get(alertMem.getMid());
			if(member!=null){
				result.add(member);
			}
			
			
		}
		
		return result;
	}
	

	@SuppressWarnings("unchecked")
	public List<Member> listNoConfig(HttpServletRequest request, String ip) {
		
		AlertMemberDao dao = (AlertMemberDao) context.getBean("AlertMemberDao");
		MemberDao memberDao = (MemberDao)context.getBean("MemberDao");
		
		/**
		 * 登录用户信息
		 * */
		String account = (String) request.getSession().getAttribute("user");
		
		boolean isadmin = false;
		if(account==null){account="guest@guest.com";}
		if(account.indexOf("@")==-1){
			isadmin=true;
		}
		
		/**
		 * 获取IP为ip的服务器配置的所有预警人员
		 * */
		@SuppressWarnings("rawtypes")
		Hashtable eqProperties = new Hashtable();
		eqProperties.put("ip", ip);
		eqProperties.put("admin", isadmin);
		@SuppressWarnings("rawtypes")
		Hashtable likeProperties = new Hashtable();
		List<AlertMember> alertlist = dao.getList(eqProperties , likeProperties,null);
		
		/**
		 * 获取用户下的所有预警人员信息
		 * */
		List<Member> memlist = memberDao.list("from Member where creater='"+account+"'");
		
		List<Member> result = new ArrayList<Member>();
		
		for(Member member:memlist){
			
			boolean find = false;
			for(AlertMember alertMem:alertlist){
				
				if(member.getId()==alertMem.getMid()){
					
					//该用户已配置该服务器的监控
					find = true;
					
				}
				
			}
			
			if(!find){
				
				result.add(member);
				
			}
			
		}
		
		return result;
	}

	

}
