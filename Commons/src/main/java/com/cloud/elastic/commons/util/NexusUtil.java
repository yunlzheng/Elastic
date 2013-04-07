package com.cloud.elastic.commons.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.sonatype.nexus.client.core.NexusClient;
import org.sonatype.nexus.client.core.subsystem.content.Content;
import org.sonatype.nexus.client.core.subsystem.content.Location;
import org.sonatype.nexus.client.rest.BaseUrl;
import org.sonatype.nexus.client.rest.NexusClientFactory;
import org.sonatype.nexus.client.rest.UsernamePasswordAuthenticationInfo;
import org.sonatype.nexus.client.rest.jersey.JerseyNexusClientFactory;
import org.sonatype.nexus.client.rest.jersey.subsystem.JerseyContentSubsystemFactory;
import org.springframework.beans.factory.annotation.Value;

public class NexusUtil {

	@Value("#{config['nexus.host']}")
	private String host;
	
	@Value("#{config['nexus.username']}")
	private String userName;
	
	@Value("#{config['nexus.password']}")
	private String password;
	
	@Value("#{config['nexus.base']}")
	private String nexus_base;
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNexus_base() {
		return nexus_base;
	}

	public void setNexus_base(String nexus_base) {
		this.nexus_base = nexus_base;
	}

	public String upload(String uuid) throws IOException{
		
		Content content = nexusClient().getSubsystem(Content.class);
		Location location = new Location("releases",nexus_base+uuid+"/"+uuid+".zip");
		String rootPath = System.getProperty("user.home");
		File toDeploy = new File(rootPath+File.separator
				+"cloud.tmp"+File.separator
				+"instance"+File.separator+uuid+
				File.separator+"instance-"+uuid+".zip");
		content.upload(location, toDeploy);
		return host+"/content/repositories/releases/"+nexus_base+uuid+"/"+uuid+".zip";
		
	}
	
	public File download(String uuid) throws IOException{
		
		Content content = nexusClient().getSubsystem(Content.class);
		Location location = new Location("releases",nexus_base+uuid+".zip");
		String rootPath = System.getProperty("user.home");
		File downloaded = new File(rootPath+File.separator+"cloud.tmp"+File.separator+"download"+File.separator+uuid+File.separator+"instance-"+uuid+".zip");
		content.download(location, downloaded);
		return downloaded;
	} 
	
	public void delete(String uuid) throws IOException {
		
		Content content = nexusClient().getSubsystem(Content.class);
		Location location = new Location("releases",nexus_base+uuid+".zip");
		content.delete( location );
	
	}
	
	@SuppressWarnings("unchecked")
	public NexusClient nexusClient() throws MalformedURLException{
		
		NexusClientFactory factory = new JerseyNexusClientFactory(new JerseyContentSubsystemFactory());
		NexusClient client = factory.createFor(BaseUrl.baseUrlFrom(host),new UsernamePasswordAuthenticationInfo(userName,password));
		return client;
		
	}
	
	public static void main(String[] args) {
		
		NexusUtil util = new NexusUtil();
		util.setHost("http://192.168.146.1:8080/nexus-2.0.3");
		util.setUserName("admin");
		util.setPassword("admin123");
		util.setNexus_base("com/mini_cloud/instance-");
		
		try {
			String url = util.upload("20130402102829");
			System.out.println(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
