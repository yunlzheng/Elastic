package com.cloud.elastic.health.plugins.tomcat;

import java.net.URL;
import com.cloud.elastic.commons.XmlFactory;
import com.cloud.elastic.commons.monitor.bean.Tomcat;
import com.cloud.elastic.commons.monitor.bean.TomcatFlow;
import com.cloud.elastic.commons.monitor.bean.TomcatJvmMemory;
import com.cloud.elastic.commons.monitor.bean.TomcatThread;

/**
 * Tomcat状态解释器
 * */
public class TomcatPaser {

	private String url;
	private String servicesName;

	private TomcatFlow tomcatFlow =new TomcatFlow();;
	private TomcatThread tomcatThread = new TomcatThread();
	private TomcatJvmMemory tomcatJvmMemeory = new TomcatJvmMemory();
	
	private TomcatPaser(Tomcat tomcat){
		
		this.servicesName = tomcat.getServicesName();
		this.url = tomcat.getStatusPageUrl()+"?XML=true";
		
	}
	
	private TomcatPaser(String url,String servicesName){
		
		this.url=url+"?XML=true";
		this.servicesName = servicesName;
		
	}
	
	public static TomcatPaser newInstance(Tomcat tomcat) throws Exception{
		
		TomcatPaser newInstance = new TomcatPaser(tomcat);
		newInstance.converter();
		return newInstance;
	}
	
	public static TomcatPaser newInstance(String url,String servicesName) throws Exception{
		
		TomcatPaser newInstance = new TomcatPaser(url, servicesName);
		newInstance.converter();
		return newInstance;
	}
	
	private TomcatPaser converter() throws Exception{
		
		URL uri = new URL(this.url);
		XmlFactory factory = XmlFactory.newInFactory(uri);
		
		String maxThreadCount = factory.getAttrValue("/status/connector[@name='\""+this.servicesName+"\"']/threadInfo/@maxThreads");
		String currentThreadCount = factory.getAttrValue("/status/connector[@name='\""+this.servicesName+"\"']/threadInfo/@currentThreadCount");
		String currentThreadsBusy = factory.getAttrValue("/status/connector[@name='\""+this.servicesName+"\"']/threadInfo/@currentThreadsBusy");
		
		try{
			
			this.tomcatThread.setCurrentThreadCount(Integer.parseInt(currentThreadCount));
			this.tomcatThread.setCurrentThreadsBusy(Integer.parseInt(currentThreadsBusy));
			this.tomcatThread.setMaxThreadCount(Integer.parseInt(maxThreadCount));
			
		}catch(Exception e){
			this.tomcatThread=null;
			e.printStackTrace();
		}
		
		
		String bytesReceived = factory.getAttrValue("/status/connector[@name='\""+this.servicesName+"\"']/requestInfo/@bytesReceived");
		String bytesSent = factory.getAttrValue("/status/connector[@name='\""+this.servicesName+"\"']/requestInfo/@bytesSent");
		
		try{
			
			this.tomcatFlow.setBytesReceived(Long.parseLong(bytesReceived));
			this.tomcatFlow.setBytesSend(Long.parseLong(bytesSent));
			
		}catch(Exception e){
			this.tomcatFlow=null;
			e.printStackTrace();
		}
		
		
		String tomcatJvmMemoryFree =  factory.getAttrValue("/status/jvm/memory/@free");
		String tomcatJvmMemoryTotal = factory.getAttrValue("/status/jvm/memory/@total");
		String tomcatJvmMemoryMax = factory.getAttrValue("/status/jvm/memory/@max");
		
		
		try{
			
			this.tomcatJvmMemeory.setJvmFree(Long.parseLong(tomcatJvmMemoryFree.toString()));
			this.tomcatJvmMemeory.setJvmMax(Long.parseLong(tomcatJvmMemoryMax.toString()));
			this.tomcatJvmMemeory.setJvmTotal(Long.parseLong(tomcatJvmMemoryTotal.toString()));
			
		}catch(Exception e){
			this.tomcatJvmMemeory=null;
			e.printStackTrace();
		}
		
		return this;
		
	}
	
	public TomcatFlow getTomcatFlow() {
		return tomcatFlow;
	}

	public TomcatThread getTomcatThread() {
		return tomcatThread;
	}

	public TomcatJvmMemory getTomcatJvmMemeory() {
		return tomcatJvmMemeory;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static void main(String[] args) {
		
		try {
			TomcatPaser paser = TomcatPaser.newInstance("http://localhost:8080/manager/status","http-bio-8080");
			paser.getTomcatFlow();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}
	
}
