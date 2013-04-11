package com.icp.monitor.displayer.resources.impl;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.icp.monitor.commons.bean.ExceptionMessage;
import com.icp.monitor.commons.bean.Response;
import com.icp.monitor.commons.dao.ExceptionDao;
import com.icp.monitor.commons.dao.impl.ExceptionDaoImpl;
import com.icp.monitor.displayer.resources.ExceptionResources;

public class ExceptionResourcesImpl implements ExceptionResources,ApplicationContextAware{


	private ExceptionDao exceptionDao = null;
	
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		
			this.exceptionDao = (ExceptionDaoImpl)context.getBean("ExceptionDao");
			
	}
	
	public List<ExceptionMessage> list(HttpServletRequest request) {
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){account="guest@guest.com";}
		
		return exceptionDao.list("from ExceptionMessage where creater='"+account+"' order by name");
	}

	public ExceptionMessage get(int id) {
		
		return exceptionDao.get(id);
	}

	public ExceptionMessage add(String type, String name,String account, String level, String message,
			String startTime, String endTime) {
		
	    ExceptionMessage exceptionMessage = new ExceptionMessage();
	    exceptionMessage.setLevel(level);
	    exceptionMessage.setEndTime(endTime);
	    exceptionMessage.setCreater(account);
	    exceptionMessage.setMessage(message);
	    exceptionMessage.setName(name);
	    exceptionMessage.setStartTime(startTime);
	    
	    exceptionDao.add(exceptionMessage);
		return exceptionMessage;
	}

	public ExceptionMessage update(String type, String name,String account, String level, String message,
			String startTime, String endTime) {
		
		
		 	ExceptionMessage exceptionMessage = new ExceptionMessage();
		    exceptionMessage.setLevel(level);
		    exceptionMessage.setEndTime(endTime);
		    exceptionMessage.setCreater(account);
		    exceptionMessage.setMessage(message);
		    exceptionMessage.setName(name);
		    exceptionMessage.setStartTime(startTime);
		    exceptionDao.update(exceptionMessage);
		
		    return exceptionMessage;
	}

	public Response delete(int id) {
		Response resp = new Response();
		
		try {
			
			exceptionDao.deleteById(id);
			resp.setCode("200");
			resp.setStatus("success");
			
		} catch (Exception e) {
			
			resp.setCode("503");
			resp.setStatus("false");
			
		}
		
		return null;
	}



	public List<ExceptionMessage> listByPage(HttpServletRequest request, int offset,
			int length, String[] eqs, String[] likes) {
		
		Map<String, String> eqProperties = new Hashtable<String,String>();
		Map<String, String> likeProperties = new Hashtable<String,String>();
		Map<String, String> orderProperties = new Hashtable<String,String>();
		
		if(eqs.length>0&&eqs[0]!=null){
			
			for(String iter:eqs){
				
				String set[] = iter.split(":");
				eqProperties.put(set[0], set[1]);
				
			}
			
		}
		
		if(likes.length>0&&likes[0]!=null){
			
			for(String iter:likes){
				
				String set[] = iter.split(":");
				likeProperties.put(set[0], set[1]);
				
			}
			
		}
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){account="guest@guest.com";}
		if(account.indexOf("@")!=-1){
			eqProperties.put("creater", account);
		}
		
		orderProperties.put("type", "desc");
		orderProperties.put("key", "id");
		
		List<ExceptionMessage> result = exceptionDao.getListForPage(offset, length, eqProperties, likeProperties,orderProperties);
	
		return result;
	}

	public Long count(String[] eqs, String[] likes) {
		
		Map<String, String> eqProperties = new Hashtable<String,String>();
		Map<String, String> likeProperties = new Hashtable<String,String>();
		if(eqs.length>0&&eqs[0]!=null){
			
			for(String iter:eqs){
				
				String set[] = iter.split(":");
				eqProperties.put(set[0], set[1]);
				
			}
			
		}
		
		if(likes.length>0&&likes[0]!=null){
			
			for(String iter:likes){
				
				String set[] = iter.split(":");
				likeProperties.put(set[0], set[1]);
				
			}
			
		}
		
		return exceptionDao.getCount(eqProperties, likeProperties);
		
	}

}
