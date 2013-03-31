package com.cloud.elastic.controler.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import com.cloud.elastic.commons.util.ZipUtil;

public class Proxy extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		req.setCharacterEncoding("utf-8");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String uuname = sdf.format(new Date());
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		String rootPath = System.getProperty("user.home")+File.separator+"cloud.tmp";
		
		String templatePath = rootPath+File.separator+"template"+File.separator+"cloud-tomcat-template.zip";
		
		//Tomcat 模板zip文件
		File templateFile = new File(templatePath);
		
		//生成的Runit文件
		File instanceFile = new File(rootPath+File.separator+"instance"+File.separator+uuname+File.separator+"instance-"+uuname+".zip");
		File instanceDir = instanceFile.getParentFile();
		
		if(!instanceDir.exists()){
			
			instanceDir.mkdir();
			
		}
		
		File instanceTmpFile = new File(instanceDir,"application");
		
		if(!instanceTmpFile.exists()){
			instanceTmpFile.mkdir();
		}
		
		File zipSourceFile = new File(instanceTmpFile,"webapps");
		
		if(!zipSourceFile.exists()){
			zipSourceFile.mkdir();
		}
		
		
		FileUtils.copyFile(templateFile,instanceFile);
		
		File temp = new File(rootPath);
		if(!temp.exists()){
			
			temp.mkdir();
			
		}
		
		factory.setRepository(instanceDir);
		
		factory.setSizeThreshold(1024*1024);
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		File target = null;
		
		try{
			
			@SuppressWarnings("unchecked")
			List<FileItem> list = (List<FileItem>)upload.parseRequest(req);
			
			for(FileItem item:list){
				
				if(!item.isFormField()){
					
					String fname = item.getName();
					String fixed = fname.substring(fname.lastIndexOf("."),fname.length());
					
					String filename = sdf.format(new Date())+fixed;
					target = new File(zipSourceFile,filename);
					OutputStream out = new FileOutputStream(target);  
                    
                    InputStream in = item.getInputStream() ;  
                      
                    int length = 0 ;  
                    byte [] buf = new byte[1024] ;  
                      
                    while( (length = in.read(buf) ) != -1)  
                    {  
                        out.write(buf, 0, length);     
                    }  
                    
                    in.close();  
                    out.close();
                    
                    ZipUtil zipUtil = new ZipUtil();
                    
                    zipUtil.unZip(instanceFile.getAbsolutePath(), instanceTmpFile.getAbsolutePath());
                    
                    zipUtil.createZip(instanceTmpFile.getAbsolutePath(), instanceFile.getAbsolutePath());
                    
                    FileUtils.deleteDirectory(instanceTmpFile);
                    
                    req.getSession().setAttribute("runit", uuname);
                    resp.getWriter().print("success");
                  
                    return;
				}
				
			}
		}catch(Exception e){
			 resp.getWriter().print("fails");
		}
		
		
		
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);
	}
	
	

}
