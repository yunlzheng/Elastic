package com.cloud.elastic.runtimes.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.cloud.elastic.commons.XmlFactory;
import com.cloud.elastic.commons.dao.ApplicationDao;
import com.cloud.elastic.commons.dao.RUnitDao;
import com.cloud.elastic.commons.dao.RuntimeDao;
import com.cloud.elastic.commons.util.ZipUtil;
import com.cloud.elastic.commons.bean.Application;
import com.cloud.elastic.commons.bean.RUnit;
import com.cloud.elastic.commons.bean.Runtime;
import com.cloud.elastic.runtime.RunTimes;
import com.cloud.elastic.runtimes.info.SystemInfo;


public class RunTimesCore implements RunTimes{

	private static Log log = LogFactory.getLog(RunTimesCore.class);
	private static String rootPath = System.getProperty("user.home")+File.separator+"Runtimes"+File.separator;
	
	@Autowired
	private RuntimeDao runtimeDao;
	
	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private RUnitDao rUnitDao;
	
	@Autowired
	private RabbitAdmin rabbitAdmin;
	
	@Value("#{config['exchange.router']}")
	private String exchange_router;
	
	private Boolean initAvailablePort=false;
	
	
	/**
	 * 配置对象
	 * String 配置方法名
	 * Boolean 是否可用
	 * */
	private Hashtable<String,Boolean> availableConfiguration= new Hashtable<String ,Boolean>();

	/**
	 * 创建运行时单元
	 * @throws Exception 
	 * */
	public void createRunit() throws Exception {
		
		log.info("createRunit handling...");
		/**初始化*/
		if(!initAvailablePort){
			initAvailablePortConfig();
		}
		
		String uuid = SystemInfo.getInstanceKey();
		
		Runtime runtime = runtimeDao.get(uuid);
		Application application = applicationDao.get(runtime.getApplication_uuid());
		if(application==null){
			throw new NullArgumentException("应用不存在");
		}
		
		String url = application.getUrl();
		
		File rootFile = new File(rootPath);
		//如果根目录不存在则创建根目录
		if(!rootFile.exists()){
			rootFile.mkdir();
			log.info("mkdir Container Floder:Runtimes ");
		}
		
		File instanceBaseFile = new File(rootPath,"baseUnit");
		
		//创建运行时单元模板
		if(!createBaseUnit()){
			
			throw new Exception("无法下载Runit基础文件");
		
		}
		
		File instancesFile = new File(rootPath,url);
		if(!instancesFile.exists()){
			instancesFile.mkdir();
			log.info("mkdir user.home/Runtimes/"+url);
		}
		
		String configuration = autoChoiceAvailablePortConfig();
		if(configuration==null){
			
			throw new Exception("没有可用的端口");
			
		}
		
		//创建新的运行时单元
		File newRunit = new File(instancesFile,configuration);
		try {
			
			log.info("copying runit from instanceBaseFile...");
			FileUtils.copyDirectory(instanceBaseFile, newRunit);
			log.info("copy end success...");
		
		}catch (IOException e) {
			
			e.printStackTrace();
		}
		
		//修改配置文件
		Configuration config = null;
		int portAjp = 0,portHttp=0,portShutDown=0;
		
		try {
			
			Method method = TomcatAvailableConfiguration.class.getMethod(configuration,new Class[]{});
			config = (Configuration) method.invoke(null, new   Object[]{});
			portAjp = config.getPort_ajp();
			portHttp  = config.getPort_http();
			portShutDown  = config.getPort_shutdown();
			
			File serverConfig = new File(newRunit,"conf"+File.separator+"server.xml");  
			XmlFactory xmlFactory = XmlFactory.newInstance(serverConfig);
			xmlFactory.updateAttribute("/Server", "port", portShutDown+"");
			xmlFactory.updateAttribute("/Server/Service/Connector[1]","port",portHttp+"");
			xmlFactory.updateAttribute("/Server/Service/Connector[2]","port",portAjp+"");
			xmlFactory.documentToFile(serverConfig.getAbsolutePath());
			
			serverConfig = null;
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
		} 
		
		RUnit runit = new RUnit();
		runit.setTomcatAvailableConfiguration(configuration);
		runit.setPortAjp(portAjp);
		runit.setPortHttp(portHttp);
		runit.setPortShutDown(portShutDown);
		runit.setRuntime(runtime);
		runit.setStatus(RUnit.Status.RUNNIG.getStatus());
		runit.setHealthStatus(RUnit.Status.RUNNIG.getStatus());
		runit.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		try{
			
			rUnitDao.save(runit);
			availableConfiguration.put(configuration, false);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
		//更新应用操作状态为启动中
		application.setOperatStatus(Application.OperatStatus.STARTING.getStatus());
		applicationDao.update(application);
		
		//启动运行是单元
		try {
			
			log.info("starting new runit...");
			
			Properties props=System.getProperties(); //获得系统属性集    
			
			
			File catalinaHome = newRunit;
			String osName = props.getProperty("os.name"); //操作系统名称    
			if(osName.toLowerCase().indexOf("windows")!=-1){
				
				//windows操作系统
				ExecuteThread executer = new ExecuteThread(new File(catalinaHome,"bin").getAbsolutePath()+File.separator+"startup.bat", catalinaHome);
				Thread t = new Thread(executer);
				t.start();

			}else{
				
				//linux操作系统
				ExecuteThread executer = new ExecuteThread(new File(catalinaHome,"bin").getAbsolutePath()+File.separator+"startup.sh", catalinaHome);
				Thread t = new Thread(executer);
				t.start();
			
			}
			
			catalinaHome = null;
			
			//更新应用操作状态为启动中,更新应用状态为运行中
			application.setOperatStatus(Application.OperatStatus.STABLE.getStatus());
			application.setHealth(Application.Health.RUNNING.getHealth());
			applicationDao.update(application);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		runtime.setInstancesNum(countInstanceNum());
		runtimeDao.update(runtime);
		
		updateHaproxy();
		log.info("createRunit handle over...");
		
	}

	/**
	 * 销毁所有运行时单元
	 * */
	public void destoryRunit() {
		
		log.info("destoryRunit handling...");
		
		String uuid = SystemInfo.getInstanceKey();
		
		Runtime runtime = runtimeDao.get(uuid);
		Application application = applicationDao.get(runtime.getApplication_uuid());
		
		//更新应用操作状态为卸载中
		try{
			application.setOperatStatus(Application.OperatStatus.UNDEPLOYED.getStatus());
			applicationDao.update(application);
		}catch(Exception e){
			
		}
		
		
		RUnit templateUnit = new RUnit();
		templateUnit.setRuntime(runtime);
		String[] properteNames = {"runtime"};
		
		List<RUnit> rUnits = rUnitDao.findEqualByEntity(templateUnit, properteNames);
		for(RUnit runit :rUnits){
			rUnitDao.delete(runit);
		}
		
		
		File rootFile = new File(rootPath);
		
		File[] files = rootFile.listFiles();
		
		if(files!=null){
			
			for(File file : files){
				
				File[] units = file.listFiles();
				if(units!=null){
					
					for(File unit :units){
						
						if(new File(unit,"bin"+File.separator+"shutdown.bat").exists()||new File(unit,"bin"+File.separator+"shutdown.sh").exists()){
							
							Properties props=System.getProperties(); //获得系统属性集    
							
							String osName = props.getProperty("os.name"); //操作系统名称    
							if(osName.toLowerCase().indexOf("windows")!=-1){
								
								//windows操作系统
								ExecuteThread executer = new ExecuteThread(new File(unit,"bin").getAbsolutePath()+File.separator+"shutdown.bat", unit);
								Thread t = new Thread(executer);
								t.start();

							}else{
								
								//linux操作系统
								ExecuteThread executer = new ExecuteThread(new File(unit,"bin").getAbsolutePath()+File.separator+"shutdown.sh", unit);
								Thread t = new Thread(executer);
								t.start();
							
							}
							
							
						}
						unit=null;
						
					}
					
					units=null;
					
				}
				
				file = null;
				
			}
			
			files = null;
			
		}
		
		
		try {
			FileUtils.deleteDirectory(rootFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try{
			
			//更新应用操作状态为卸载中  更新健康状态为未运行任何实例
			application.setOperatStatus(Application.OperatStatus.STABLE.getStatus());
			application.setHealth(Application.Health.BINDED.getHealth());
			applicationDao.update(application);
			
			
		}catch(Exception e){
			
		}
		
		runtime.setInstancesNum(countInstanceNum());
		runtimeDao.update(runtime);
		
		updateHaproxy();
		log.info("destoryRunit handle over...");
		
	}

	/**
	 * 
	 * 复制运行时单元
	 * @throws Exception 
	 * 
	 */
	public void cloneRunit() throws Exception {
		
		this.createRunit();
		
	}
	
	/**
	 * 
	 * 启动所有运行时单元
	 * 
	 * */
	public void startRunit() {
		
		log.info("startRunit handling...");
		
		/**初始化*/
		if(!initAvailablePort){
			initAvailablePortConfig();
		}
		
		String uuid = SystemInfo.getInstanceKey();
		
		Runtime runtime = runtimeDao.get(uuid);
		Application application = applicationDao.get(runtime.getApplication_uuid());
		
		if(application==null){
			
			throw new NullArgumentException("应用不存在");
		
		}
		
		//更新应用操作状态状态为启动中
		application.setOperatStatus(Application.OperatStatus.STARTING.getStatus());
		applicationDao.update(application);
		
		String url = application.getUrl();
		
		/**
		 * 修改运行时单元状态
		 * */
		RUnit templateUnit = new RUnit();
		templateUnit.setRuntime(runtime);
		String[] properteNames = {"runtime"};
		
		List<RUnit> rUnits = rUnitDao.findEqualByEntity(templateUnit, properteNames);
		for(RUnit runit :rUnits){
			

			String configName = runit.getTomcatAvailableConfiguration();
			
			if(RUnit.Status.STOPING.getStatus()==runit.getStatus()){
				
				File instancesFile = new File(rootPath,url);
				File runitHome = new File(instancesFile,configName);
				
				Properties props=System.getProperties(); //获得系统属性集    
				String osName = props.getProperty("os.name"); //操作系统名称    
				if(osName.toLowerCase().indexOf("windows")!=-1){
					
					//windows操作系统
					ExecuteThread executer = new ExecuteThread(new File(runitHome,"bin").getAbsolutePath()+File.separator+"startup.bat", runitHome);
					Thread t = new Thread(executer);
					t.start();

				}else{
					
					//linux操作系统
					ExecuteThread executer = new ExecuteThread(new File(runitHome,"bin").getAbsolutePath()+File.separator+"startup.sh", runitHome);
					Thread t = new Thread(executer);
					t.start();
				
				}
				
				runit.setStatus(RUnit.Status.RUNNIG.getStatus());
				runit.setHealthStatus(RUnit.Status.RUNNIG.getStatus());
				rUnitDao.update(runit);
				
			}

		}
		
		//更新应用操作状态，更新应用状态为运行中
		application.setOperatStatus(Application.OperatStatus.STABLE.getStatus());
		application.setHealth(Application.Health.RUNNING.getHealth());
		applicationDao.update(application);
		
		
		log.info("startRunit handle over!");
		
	}

	/**
	 * 停止所有运行时单元
	 * */
	public void stopRunit() {
		
		log.info("stopRunit handling...");
		
		/**初始化*/
		if(!initAvailablePort){
			initAvailablePortConfig();
		}
		
		String uuid = SystemInfo.getInstanceKey();
		Runtime runtime = runtimeDao.get(uuid);
		Application application = applicationDao.get(runtime.getApplication_uuid());
		if(application==null){
			throw new NullArgumentException("应用不存在");
		}
		
		//更新应用操作状态
		application.setOperatStatus(Application.OperatStatus.STOPING.getStatus());
		applicationDao.update(application);
		
		String url = application.getUrl();
		
		/**
		 * 修改运行时单元状态
		 * */
		RUnit templateUnit = new RUnit();
		templateUnit.setRuntime(runtime);
		String[] properteNames = {"runtime"};
		
		List<RUnit> rUnits = rUnitDao.findEqualByEntity(templateUnit, properteNames);
		for(RUnit runit :rUnits){
			

			String configName = runit.getTomcatAvailableConfiguration();
			
			if(RUnit.Status.RUNNIG.getStatus()==runit.getStatus()){
				
				File instancesFile = new File(rootPath,url);
				File runitHome = new File(instancesFile,configName);
				
				Properties props=System.getProperties(); //获得系统属性集    
				String osName = props.getProperty("os.name"); //操作系统名称    
				if(osName.toLowerCase().indexOf("windows")!=-1){
					
					//windows操作系统
					ExecuteThread executer = new ExecuteThread(new File(runitHome,"bin").getAbsolutePath()+File.separator+"shutdown.bat", runitHome);
					Thread t = new Thread(executer);
					t.start();

				}else{
					
					//linux操作系统
					ExecuteThread executer = new ExecuteThread(new File(runitHome,"bin").getAbsolutePath()+File.separator+"shutdown.sh", runitHome);
					Thread t = new Thread(executer);
					t.start();
				
				}
				
				runit.setStatus(RUnit.Status.STOPING.getStatus());
				runit.setHealthStatus(RUnit.Status.STOPING.getStatus());
				rUnitDao.update(runit);
				
			}

		}
		
		//更新应用操作状态
		application.setOperatStatus(Application.OperatStatus.STABLE.getStatus());
		application.setHealth(Application.Health.STOPED.getHealth());
		applicationDao.update(application);
		log.info("stopRunit handle over");
	}
	
	/**
	 * 減少運行是單元
	 * @throws Exception 
	 * */
	public void shrinkRunit() throws Exception{
		
		log.info("shrinkRunit handling...");
	
		/**初始化*/
		if(!initAvailablePort){
			initAvailablePortConfig();
		}
		
		String uuid = SystemInfo.getInstanceKey();
		Runtime runtime = runtimeDao.get(uuid);
		Application application = applicationDao.get(runtime.getApplication_uuid());
		if(application==null){
			throw new NullArgumentException("应用不存在");
		}
		
		application.setOperatStatus(Application.OperatStatus.SHIRKING.getStatus());
		applicationDao.update(application);
		
		String url = application.getUrl();
		
		/**
		 * 修改运行时单元状态
		 * */
		RUnit templateUnit = new RUnit();
		templateUnit.setRuntime(runtime);
		String[] properteNames = {"runtime"};
		
		List<RUnit> rUnits = rUnitDao.findEqualByEntity(templateUnit, properteNames);
		if(rUnits.size()>1){
			
			RUnit runit = rUnits.get(0);
			String configName = runit.getTomcatAvailableConfiguration();
			File instancesFile = new File(rootPath,url);
			File runitHome = new File(instancesFile,configName);
			
			Properties props=System.getProperties(); //获得系统属性集    
			String osName = props.getProperty("os.name"); //操作系统名称    
			if(osName.toLowerCase().indexOf("windows")!=-1){
				
				//windows操作系统
				ExecuteThread executer = new ExecuteThread(new File(runitHome,"bin").getAbsolutePath()+File.separator+"shutdown.bat", runitHome);
				Thread t = new Thread(executer);
				t.start();

			}else{
				
				//linux操作系统
				ExecuteThread executer = new ExecuteThread(new File(runitHome,"bin").getAbsolutePath()+File.separator+"shutdown.sh", runitHome);
				Thread t = new Thread(executer);
				t.start();
				
			}
			
			rUnitDao.delete(runit);
			
			try {
				Thread.sleep(3000);
				FileUtils.deleteDirectory(runitHome);
			
			}catch (IOException e) {
				
				e.printStackTrace();
			}
			
			
		}else{
			
			application.setOperatStatus(Application.OperatStatus.STABLE.getStatus());
			applicationDao.update(application);
			throw new Exception("应用当前只有唯一运行实例，无法收缩资源");
			
		}
		
		application.setOperatStatus(Application.OperatStatus.STABLE.getStatus());
		applicationDao.update(application);
		
		runtime.setInstancesNum(countInstanceNum());
		runtimeDao.update(runtime);
		
		updateHaproxy();
		log.info("shrinkRunit handle over!!!");
	}
	
	public void free() {
		
		this.destoryRunit();
		String uuid = SystemInfo.getInstanceKey();
		Runtime runtime = runtimeDao.get(uuid);
		runtime.setApplication_uuid(null);
		runtimeDao.update(runtime);
		updateHaproxy();
		
	}
	
	
	public void initAvailablePortConfig(){
		
		/**
		 * 获取Tomcat可用端口配置的所有项
		 * */
		Method[] methods = TomcatAvailableConfiguration.class.getMethods();
		for(int i=0;i<methods.length;i++){
			
			Method _method = methods[i];
			if(_method.getName().indexOf("Configuration")!=-1){
				
				/**初始化Hash*/
				availableConfiguration.put(_method.getName(), true);
				
			}
			
		}
		
		/**
		 * 查找该运行时环境已经创建的所有运行时单元
		 * */
		String uuid = SystemInfo.getInstanceKey();
		Runtime runtime = runtimeDao.get(uuid);
		RUnit templateUnit = new RUnit();
		templateUnit.setRuntime(runtime);
		String[] properteNames = {"runtime"};
		
		List<RUnit> rUnits = rUnitDao.findEqualByEntity(templateUnit, properteNames);
		for(RUnit unit : rUnits){
			
			/**得到配置名*/
			String configurationName = unit.getTomcatAvailableConfiguration();
			/**设置已经使用的配置的可用性为false*/
			availableConfiguration.put(configurationName, false);
			
		}
	
		initAvailablePort = true;
		
	}
	
	/**
	 * 选择可用的端口配置组
	 * */
	public String autoChoiceAvailablePortConfig(){
		
		for(String key:availableConfiguration.keySet()){
			
			boolean available = availableConfiguration.get(key);
			if(available==true){
				return key;
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * 从Nexus仓库下载Runit压缩包到本地，并解压
	 * */
	public boolean createBaseUnit(){
		
		String uuid = SystemInfo.getInstanceKey();
		
		Runtime runtime = runtimeDao.get(uuid);
		Application application = applicationDao.get(runtime.getApplication_uuid());
		
		if(application==null){
			throw new NullArgumentException("应用不存在");
		}
		
		String url = application.getUrl();
		File instanceBaseFile = new File(rootPath,"baseUnit");
		//创建目录
		if(!instanceBaseFile.exists()){
			instanceBaseFile.mkdir();
			log.info("mkdir Base Instance Container:"+url);
		}
		
		String runitRepositoryUrl = application.getRepositoryUrl();
		log.info("Repository URL:"+runitRepositoryUrl);
		if(runitRepositoryUrl==null||!runitRepositoryUrl.startsWith("http")){
			log.info("Application Repository Url not right!");
		}else{
			
			File baseZip = new File(rootPath,"runit.zip");
			
			if(baseZip.exists()){
				return true;
			}
			
			try {
				
				log.info("Download Base Resources:"+runitRepositoryUrl);
				FileUtils.copyURLToFile(new URL(runitRepositoryUrl),baseZip);
				log.info("success download resources to "+baseZip.getName());
				
				ZipUtil zipUtil = new ZipUtil();
				zipUtil.unZip(baseZip.getAbsolutePath(), instanceBaseFile.getAbsolutePath());
				
				baseZip.delete();
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
		}
			
		
		return true;
		
			
		
	}
	
	public int countInstanceNum(){
		
		String uuid = SystemInfo.getInstanceKey();
		
		Runtime runtime = runtimeDao.get(uuid);
		RUnit templateUnit = new RUnit();
		templateUnit.setRuntime(runtime);
		String[] properteNames = {"runtime"};
		
		List<RUnit> rUnits = rUnitDao.findEqualByEntity(templateUnit, properteNames);
		
		return rUnits.size();
		
	}
	
	public void updateHaproxy(){
		
		Message message = new Message("update ha".getBytes(),new MessageProperties());
		rabbitAdmin.getRabbitTemplate().send(exchange_router, "*.update", message);
		log.info("send message to exchange["+exchange_router+"] with routingKey[*.update]");
		
	}
	
	class ExecuteThread implements Runnable{

		private String command;
		private File dir;
		
		public ExecuteThread(String command,File dir){
			
			this.command = command;
			this.dir = dir;
			
		}
		
		public void run() {
			
			log.info("command:"+command+"\t"+dir.getAbsolutePath());
			
			try {
				
				Process process = java.lang.Runtime.getRuntime().exec(command,null,dir);
				InputStream is = process.getInputStream();
				@SuppressWarnings("unused")
				int length = 0 ;  
				byte[] buffer = new byte[1024];
				
				while((length = is.read(buffer))!=-1){
					
					log.info(new String(buffer));
				}
				
				process.destroy();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
		
	}

	


}
