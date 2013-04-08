package com.cloud.elastic.router.velocity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cloud.elastic.commons.dao.RUnitDao;
import com.cloud.elastic.commons.dao.RuntimeDao;
import com.cloud.elastic.commons.bean.RUnit;
import com.cloud.elastic.commons.bean.Runtime;
import com.cloud.elastic.router.velocity.template.HaproxyTemplate;

public class VelocityTest {

	public static void main(String[] args) throws IOException {

			ApplicationContext context= new ClassPathXmlApplicationContext(new String[]{"beans.xml"}); 
			
			RuntimeDao runtimeDao = context.getBean(RuntimeDao.class);
			RUnitDao runitDao = context.getBean(RUnitDao.class); 
			
			List<Runtime> runtimes = runtimeDao.loadAll();
			List<HaproxyTemplate> haTemplates = new ArrayList<HaproxyTemplate>();
			
			for(int i=0;i<runtimes.size();i++){
				
				Runtime runtime = runtimes.get(i);
				
				RUnit tempRunit = new RUnit();
				tempRunit.setRuntime(runtime);
				
				List<RUnit> runits = runitDao.findEqualByEntity(tempRunit, new String[]{"runtime"});
				
				System.out.println(runits.size());
				
				HaproxyTemplate hatemplate = new HaproxyTemplate();
				hatemplate.setRuntime(runtime);
				hatemplate.setRunit(runits);
				haTemplates.add(hatemplate);	
				
			}
			
		 	String rootPath = System.getProperty("user.home")+File.separator+"Router";
		 	
			Properties p = new Properties();
			p.setProperty("input.encoding ", "UTF-8");
			p.setProperty("output.encoding", "UTF-8");
			p.setProperty("runtime.log", rootPath+File.separator+"logs"+File.separator+"velocity.logs");
			p.setProperty("file.resource.loader.path", rootPath+File.separator+"vm");
			
			Velocity.init(p);
			VelocityContext velocityContext = new VelocityContext();
			
			velocityContext.put("haTemplates", haTemplates);
			
			
			Template template = Velocity.getTemplate("helloworld.vm");

			
			File output = new File(new File(rootPath),"output.txt");
			FileOutputStream os = new FileOutputStream(output);
			OutputStreamWriter writer = new OutputStreamWriter(os);
			
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			
			template.merge(velocityContext, bufferedWriter);
			
			bufferedWriter.flush();
			os.close();
			bufferedWriter.close();
			
			
			
	}
	
}
