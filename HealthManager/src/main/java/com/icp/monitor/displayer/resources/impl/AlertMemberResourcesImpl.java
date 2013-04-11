package com.icp.monitor.displayer.resources.impl;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.AlertMember;
import com.icp.monitor.commons.bean.Response;
import com.icp.monitor.commons.dao.AlertMemberDao;
import com.icp.monitor.commons.dao.impl.AlertMemberDaoImpl;
import com.icp.monitor.commons.util.SystemUtil;
import com.icp.monitor.displayer.resources.AlertMemberResources;

public class AlertMemberResourcesImpl implements ApplicationContextAware,
		AlertMemberResources {

	private ApplicationContext context = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AlertMember addByWay(HttpServletRequest request,String ip, int mid, int way) {

		
		AlertMemberDao alertMenberDao = (AlertMemberDaoImpl) context.getBean("AlertMemberDao");

		Hashtable eqProperties = new Hashtable();
		eqProperties.put("ip", ip);
		eqProperties.put("mid", mid);
		Hashtable likeProperties = new Hashtable();
		List<AlertMember> list = alertMenberDao.getList(eqProperties,likeProperties,null);
		if(list!=null){
			
			for(AlertMember alertMember:list){
				
				if(way==1){
					
					alertMember.setEmail(true);
				
				}else if(way==2){
				
					alertMember.setTele(true);
				
				}
				
				alertMenberDao.update(alertMember);
				
			}
		
		}

		return null;
	}

	public List<AlertMember> list() {
		AlertMemberDao alertMenberDao = (AlertMemberDaoImpl) context
				.getBean("AlertMemberDao");
		List<AlertMember> list = alertMenberDao.list("from AlertMember");
		return list;
	}
	

	public AlertMember get(int id) {
		AlertMemberDao alertMenberDao = (AlertMemberDaoImpl) context
				.getBean("AlertMemberDao");
		AlertMember alertMember = alertMenberDao.get(id);
		return alertMember;
	}

	public AlertMember add(String ip, boolean email, boolean tell, int mid) {

		AlertMember alertMember = new AlertMember();
		AlertMemberDao alertMenberDao = (AlertMemberDaoImpl) context
				.getBean("AlertMemberDao");

		alertMember.setEmail(email);
		alertMember.setTele(tell);
		alertMember.setIp(ip);
		alertMember.setJoinTime(SystemUtil.getCurrentTime());
		alertMember.setMid(mid);
		alertMenberDao.add(alertMember);

		return alertMember;
	}

	public AlertMember update(int id, String ip, boolean email, boolean tell,
			int mid) {

		AlertMemberDao alertMenberDao = (AlertMemberDaoImpl) context
				.getBean("AlertMemberDao");
		AlertMember alertMember = new AlertMember();

		alertMember.setEmail(email);
		alertMember.setTele(tell);
		alertMember.setJoinTime(SystemUtil.getCurrentTime());
		alertMember.setMid(mid);
		alertMember.setIp(ip);

		alertMenberDao.update(alertMember);
		return alertMember;
	}

	public Response delete(int id) {
		Response resp = new Response();
		AlertMemberDao alertMenberDao = (AlertMemberDaoImpl) context
				.getBean("AlertMemberDao");
		try {
			alertMenberDao.deleteById(id);
			resp.setCode("200");
			resp.setStatus("success");
		} catch (Exception e) {
			resp.setCode("503");
			resp.setStatus("false");
		}
		return resp;
	}


	@SuppressWarnings("unchecked")
	public List<AlertMember> addAlertMemberBatch(HttpServletRequest request,String ip,String[] datas) {
	
		AlertMemberDao dao = (AlertMemberDao) context.getBean("AlertMemberDao");
		
		/**
		 * 登录用户信息
		 * */
		String account = (String) request.getSession().getAttribute("user");
		
		boolean isadmin = false;
		if(account==null){account="guest@guest.com";}
		if(account.indexOf("@")==-1){
			isadmin=true;
		}
		
		@SuppressWarnings("rawtypes")
		Hashtable eqProperties = new Hashtable();
		eqProperties.put("ip", ip);
		eqProperties.put("admin", isadmin);
		@SuppressWarnings("rawtypes")
		Hashtable likeProperties = new Hashtable();
		List<AlertMember> oldlist = dao.getList(eqProperties , likeProperties,null);
		
		if(datas==null){
			
			System.out.println("删除所有该服务器监控人员信息");
			
		}else{
			
			/**
			 * 更新或者添加监控人员信息
			 * */
			for(String data:datas){
				
				String[] infos = data.split("-");
				int mid = Integer.parseInt(infos[0]);
				boolean mailtag = Boolean.parseBoolean(infos[1]);
				boolean telltag = Boolean.parseBoolean(infos[2]);
				
				boolean find = false;
				AlertMember obj = new AlertMember();
				for(AlertMember alertMember:oldlist){
					
					if(alertMember.getIp().equals(ip)&&alertMember.getMid()==mid){
						
						obj = alertMember;
						find = true;
						
					}
					
				}
				
				if(find){
					
					/**
					 *如果以存在则更新 
					 * */
					obj.setEmail(mailtag);
					obj.setTele(telltag);
					obj.setAdmin(isadmin);
					obj.setJoinTime(SystemUtil.getCurrentTime());
					dao.update(obj);
					System.out.println("更新监控人员信息 mid:"+mid+",ip:"+ip);
				
				}else{
					
					/**
					 * 否则添加
					 * */
					obj.setAdmin(isadmin);
					obj.setEmail(mailtag);
					obj.setIp(ip);
					obj.setJoinTime(SystemUtil.getCurrentTime());
					obj.setMid(mid);
					obj.setTele(telltag);
					dao.add(obj);
					System.out.println("添加监控人员信息 mid:"+mid+",ip:"+ip);
					
				}
				
				
			}
			
			oldlist = dao.getList(eqProperties , likeProperties,null);
			/**
			 * 删除多余监控人员信息
			 * */
			for(AlertMember alertMember:oldlist){
				
					boolean find = false;
				
					for(String data:datas){
						
						String[] infos = data.split("-");
						int mid = Integer.parseInt(infos[0]);
						
						if(alertMember.getIp().equals(ip)&&alertMember.getMid()==mid){
							
							find = true;
							
						}
						
					}
					
					if(!find){

						System.out.println("删除监控人员信息 mid:"+alertMember.getMid()+",ip:"+alertMember.getIp());
						dao.delete(alertMember);
					}
					
				
			}
			
			
		}
		
		return dao.list("from AlertMember");
	}
	
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}


}
