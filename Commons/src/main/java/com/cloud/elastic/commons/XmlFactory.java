package com.cloud.elastic.commons;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Iterator;
import java.util.List;



import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


/**
 * Xml操作工厂对象
 * @author 云龙
 * */
public class XmlFactory {

	private Document doc = null;

	
	private XmlFactory(File sources) throws DocumentException{
		
		SAXReader reader = new SAXReader();
		doc = reader.read(sources);
		
	}
	
	private XmlFactory(URL url) throws DocumentException {
		
		SAXReader reader = new SAXReader();
		doc = reader.read(url);
		
	}
	
	public static XmlFactory newInstance(File sources) throws DocumentException{
		
		return new XmlFactory(sources);
		
	}
	
	public static XmlFactory newInFactory(URL url) throws DocumentException{
		
		return new XmlFactory(url);
		
	}
	
	
	
	/**解析XML*/
	public void paasAll(){
		
		Element root = doc.getRootElement();
		@SuppressWarnings("unchecked")
		Iterator<Element> it = root.elements().iterator();
		while(it.hasNext()){
			
			Element ele = (Element)it.next(); 
			@SuppressWarnings("unchecked")
			List<Attribute> list = ele.attributes();
			for(Attribute attr : list){
				System.out.println("\t"+attr.getPath());
			}
			System.out.println(ele.getUniquePath());
			
			this.paas(ele);
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void paas(Element root){
		
		Iterator<Element> it = root.elements().iterator();
		while(it.hasNext()){
			
			Element ele = (Element)it.next(); 
			
			List<Attribute> list = ele.attributes();
			for(Attribute attr : list){
				System.out.println("    "+attr.getPath());
			}
			System.out.println(ele.getUniquePath());
			
			this.paas(ele);
			
		}
		
	}
	
	
	/**获得唯一节点值*/
	public  String getNodeValue(String selectExpress){
		
		Element ele = (Element) doc.selectObject(selectExpress);
		return ele.getText();
	}
	
	/**获得唯一属性值*/
	public String getAttrValue(String selectExpress){
		
		Attribute attr = (Attribute) doc.selectObject(selectExpress);
		return attr.getText();
	}
	
	
	/**
	 * 修改满足表达式selectExpress中的属性值
	 * @param selectExpress 选择表达式 
	 * @param attribute 属性值
	 * @param value 值
	 * */
	public void updateAttribute(String selectExpress,String attribute,String value){
		
		String express = selectExpress+"/@"+attribute;
		System.out.println(express);
		@SuppressWarnings("rawtypes")
		List listAttr = doc.selectNodes(express);
		
		@SuppressWarnings("rawtypes")
		Iterator itAttr = listAttr.iterator();
		while(itAttr.hasNext()){
			Attribute attr = (Attribute)itAttr.next();
			attr.setValue(value);
		}
		
	}
	
	public void updateAttributeByNode(String selectExpress,String attribute,String value,String targetAttribute,String targetValue){
		
		@SuppressWarnings("rawtypes")
		List listEle = doc.selectNodes(selectExpress);
		@SuppressWarnings("rawtypes")
		Iterator itEle = listEle.iterator();
		Element targetEle = null;
		while(itEle.hasNext()){
			
			Element empEle = (Element) itEle.next();
			@SuppressWarnings("rawtypes")
			List attributes = empEle.attributes();
			for(Object obj : attributes){
			
				Attribute attr = (Attribute) obj;
				if(attr.getName().equals(attribute)||attr.getValue().equals(value)){
					
					targetEle = empEle;
					
				}
				
			}
			
		}
		
		String path = targetEle.getUniquePath();
		System.out.println(path);
		
	}
	
	/**
	 * 保存Document对象到文件
	 * @throws IOException 
	 * */
	public void documentToFile(String target) throws IOException{
		
		//创建输出流
		Writer out = new FileWriter(target);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		//创建输出对象
		XMLWriter writer = new XMLWriter(out,format);
		writer.write(doc);
		writer.close();
			
	}
	
	public static void main(String[] args) {
		
		try {
			XmlFactory factory = XmlFactory.newInstance(new File(System.getProperty("user.home")+File.separator+"server.xml"));
			factory.updateAttribute("/Server", "port", "88888");
			factory.updateAttribute("/Server/Service/Connector[1]","port","2222");
			factory.updateAttribute("/Server/Service/Connector[2]","port","4444");
			factory.documentToFile(System.getProperty("user.home")+File.separator+"server.xml");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
