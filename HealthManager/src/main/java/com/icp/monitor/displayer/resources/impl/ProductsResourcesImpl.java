package com.icp.monitor.displayer.resources.impl;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.bean.OpenAccount;
import com.icp.monitor.commons.dao.OpenAccountDao;
import com.icp.monitor.commons.util.SystemUtil;
import com.icp.monitor.displayer.resources.ProductsResources;

public class ProductsResourcesImpl implements ProductsResources,ApplicationContextAware{

	private Log log = LogFactory.getLog(ProductsResourcesImpl.class);
	private ApplicationContext context = null;
	private String ssiAddress = null;
	private String systemId = null;
	private String systemName = null;
	private String systemVersion = null;
	private String systemKey = null;
	
	public String getSystemId() {
		return systemId;
	}

	@Value("#{SystemConfig['systemId']}")
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSsiAddress() {
		return ssiAddress;
	}
	
	@Value("#{SystemConfig['ssi']}")
	public void setSsiAddress(String ssiAddress) {
		this.ssiAddress = ssiAddress;
	}

	public String getSystemName() {
		return systemName;
	}

	@Value("#{SystemConfig['systemName']}")
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	@Value("#{SystemConfig['systemVersion']}")
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
	
	public String getSystemKey() {
		return systemKey;
	}

	@Value("#{SystemConfig['systemKey']}")
	public void setSystemKey(String systemKey) {
		this.systemKey = systemKey;
	}

	public Response openServices(HttpServletRequest request,String orderid,String account) {
		
		log.info("orderid:"+orderid);
		//String account = (String) request.getSession().getAttribute("user");
		OpenAccountDao dao = (OpenAccountDao)context.getBean("OpenAccountDao");
		List<OpenAccount> list = dao.list("from OpenAccount where account='"+account+"'");
		
		if(list.size()>0){
			return Response.ok(list.get(0), MediaType.APPLICATION_JSON).build();
		}

//		//ProductOpenCallBack c = new ProductOpenCallBack(getSsiAddress());
//
//		
//		OpenAccount openAccount = new OpenAccount();
//		
//		try {
//
//			Hashtable<String,String> properties = new Hashtable<String,String>();
//			properties.put("type","云服务器及其应用程序监控");
//			properties.put("key", "云监控");//用于后期定位特定服务
//		
//			log.info("开通服务的用户:"+account);
//			log.info("子系统注册地址:"+getSsiAddress());
//			log.info("SystemKey:"+getSystemKey());
//			log.info("Systemid:"+getSystemId());
//			
//			String result = c.callback(getSystemKey(), getSystemId(), orderid, account, SystemUtil.getCurrentTime(), SystemUtil.getCurrentTime(), "",properties);
//			log.info("ssi响应:"+result);
//			
//			if(result.equals("{\"result\":\"true\"}")){
//				
//				log.debug("addAccount");
//				openAccount.setAccount(account);
//				openAccount.setUpdateTime(SystemUtil.getCurrentTime());
//				dao.add(openAccount);
//				
//			}
//			
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//			log.info(e);
//			
//		} 
		
		//return Response.ok(openAccount, MediaType.APPLICATION_JSON).build();
		return null;
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		
		this.context = arg0;
		
	}

}
