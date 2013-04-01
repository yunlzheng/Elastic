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
	
	public void upload(String uuid) throws IOException{
		
		Content content = nexusClient().getSubsystem(Content.class);
		Location location = new Location("release",nexus_base+uuid+".zip");
		String rootPath = System.getProperty("user.home");
		File toDeploy = new File(rootPath+"cloud.tmp"+File.separator+"instance"+File.separator+"uuid"+File.separator+"instance-"+uuid+".zip");
		content.upload(location, toDeploy);
		
	}
	
	public File download(String uuid) throws IOException{
		
		Content content = nexusClient().getSubsystem(Content.class);
		Location location = new Location("release",nexus_base+uuid+".zip");
		String rootPath = System.getProperty("user.home");
		File downloaded = new File(rootPath+"cloud.tmp"+File.separator+"download"+File.separator+"uuid"+File.separator+"instance-"+uuid+".zip");
		content.download(location, downloaded);
		return downloaded;
	} 
	
	public void delete(String uuid) throws IOException {
		
		Content content = nexusClient().getSubsystem(Content.class);
		Location location = new Location("release",nexus_base+uuid+".zip");
		content.delete( location );
	
	}
	
	@SuppressWarnings("unchecked")
	public NexusClient nexusClient() throws MalformedURLException{
		
		NexusClientFactory factory = new JerseyNexusClientFactory(new JerseyContentSubsystemFactory());
		NexusClient client = factory.createFor(BaseUrl.baseUrlFrom("http://nexus.dev4yun70.com"),new UsernamePasswordAuthenticationInfo(userName,password));
		return client;
		
	}
	
	
	
}
