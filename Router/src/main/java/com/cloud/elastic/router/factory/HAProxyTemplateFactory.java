package com.cloud.elastic.router.factory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.elastic.commons.bean.Application;
import com.cloud.elastic.commons.bean.RUnit;
import com.cloud.elastic.commons.bean.Runtime;
import com.cloud.elastic.commons.dao.ApplicationDao;
import com.cloud.elastic.commons.dao.RUnitDao;
import com.cloud.elastic.commons.dao.RuntimeDao;
import com.cloud.elastic.router.velocity.template.BackendTemplate;
import com.cloud.elastic.router.velocity.template.HaproxyTemplate;

/**
 * 根据当前集群环境生成Haproxy配置文件的工厂类
 * @author 云龙
 * */
public class HAProxyTemplateFactory {

	private Log log = LogFactory.getLog(HAProxyTemplateFactory.class);
	
	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private RuntimeDao runtimeDao;
	
	@Autowired
	private RUnitDao rUnitDao;
	
	private VelocityContext velocityContext;
	
	private static String rootPath = System.getProperty("user.dir");
	
	/**私有构造函数*/
	private HAProxyTemplateFactory(){
		
		
		System.out.println(rootPath);
		Properties p = new Properties();
		p.setProperty("input.encoding ", "UTF-8");
		p.setProperty("output.encoding", "UTF-8");
		p.setProperty("runtime.log", rootPath+File.separator+"logs"+File.separator+"velocity.logs");
		p.setProperty("file.resource.loader.path", rootPath+File.separator+"vm");
		
		Velocity.init(p);
		velocityContext = new VelocityContext();
		
	}
	
	public HAProxyTemplateFactory newInstance(){
		
		return new HAProxyTemplateFactory();
		
	}
	
	public File createHaproxyConfigFile() throws IOException{
		
		List<HaproxyTemplate> haproxyTemplates = new ArrayList<HaproxyTemplate>();
		
		List<Application> applications = applicationDao.loadAll();
		
		/**遍历应用*/
		for(Application application:applications){
			
			//模板对象
			
			log.info("HOST:"+application.getUrl());
			
			Runtime tempRuntime = new Runtime();
			tempRuntime.setApplication_uuid(application.getUuid());
			List<Runtime> runtimes = runtimeDao.findEqualByEntity(tempRuntime, new String[]{"application_uuid"});
			
			List<BackendTemplate> backends = new ArrayList<BackendTemplate>();
			
			if(runtimes==null||runtimes.size()==0){
				
				//TODO 应用未绑定运行是环境时，转发到默认地址
				BackendTemplate defaultBackend = new BackendTemplate();
				defaultBackend.setIp("127.0.0.1");
				List<Integer> defaultPorts = new ArrayList<Integer>();
				defaultPorts.add(80);
				defaultBackend.setPorts(defaultPorts);
				log.info("server default :127.0.0.1:80");
				backends.add(defaultBackend);
				
			}else{
				
				
				/**遍历运行时环境*/
				for(Runtime runtime:runtimes){
					
					BackendTemplate backend = new BackendTemplate();
					List<Integer> ports = new ArrayList<Integer>();
					
					
					RUnit tempRUnit = new RUnit();
					tempRUnit.setRuntime(runtime);
					List<RUnit> runits = rUnitDao.findEqualByEntity(tempRUnit, new String[]{"runtime"});
					
					if(runits==null||runits.size()==0){
						
						//TODO 应用绑定了集群但是未运行实例，转发到集群80端口
						ports.add(80);
						log.info("server with default port:"+runtime.getIp()+":"+80);
						
					}else{
						
						for(RUnit runit:runits){
							
							log.info("server:"+runtime.getIp()+":"+runit.getPortHttp());
							ports.add(runit.getPortHttp());
							
						}
						
					}
					
					backend.setIp(runtime.getIp());
					backend.setPorts(ports);
					
					backends.add(backend);
					
				}
				
			}
			
			HaproxyTemplate haproxyTemplate = new HaproxyTemplate();
			haproxyTemplate.setHost(application.getUrl());
			haproxyTemplate.setUuid(application.getUuid());
			haproxyTemplate.setBackendTemplates(backends);
			
			haproxyTemplates.add(haproxyTemplate);
			
		}
		
		velocityContext.put("haproxyTemplates", haproxyTemplates);
		
		/**
		 * 输出模板到文件
		 * */
		Template template = Velocity.getTemplate("haproxy.vm");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String uuname = sdf.format(new Date());
		
		File output = new File(rootPath+File.separator+"output"+File.separator+"haproxy-"+uuname+".cfg");
		FileOutputStream outputStream = new FileOutputStream(output);
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		
		template.merge(velocityContext, bufferedWriter);
		
		bufferedWriter.flush();
		outputStream.close();
		writer.close();
		bufferedWriter.close();
		
		System.out.println("success create haproxy config file "+output.getName());
		
		return output;
		
	}
	
}
