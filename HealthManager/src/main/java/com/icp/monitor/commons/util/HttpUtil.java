package com.icp.monitor.commons.util;


import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

public class HttpUtil {

	private static Logger log = Logger.getLogger("HttpUtil");
	
	public static String sendHttpGet(String host,Hashtable<String, String> params) {

		String result = "";
		HttpClient httpclient = new HttpClient();// 创建一个客户端
		GetMethod getMethod = new GetMethod(host);

		HttpMethodParams param = new HttpMethodParams();

		/**循环添加参数*/
		for (Iterator<String> it = params.keySet().iterator(); it.hasNext();) {
			
			String key = (String) it.next();
			String value = params.get(key);
			param.setParameter(key, value);
		
		}
	
		getMethod.setParams(param);
		param.toString();
		
		try {

			int statusCode = httpclient.executeMethod(getMethod);
			result = getMethod.getResponseBodyAsString();
			log.info("GET<"+host+"> StatusCode<"+statusCode+">");
			

		} catch (HttpException e) {

			log.error(e);

		} catch (IOException e) {

			log.error(e);
			
		}finally{
			
			getMethod.releaseConnection();
			
		}

		return result;

	}
	
	

	public static String sendHttpPost(String host,Hashtable<String, String> params){
		
		String result = "";
		
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(host);
		NameValuePair[] postData = new NameValuePair[params.size()];  
		
		int i=0;
		/**循环添加参数*/
		for (Iterator<String> it = params.keySet().iterator(); it.hasNext();) {
			
			String key = (String) it.next();
			String value = (String) params.get(key);
			postData[i] = new NameValuePair(key,value);
			i++;
			
		}
		
		postMethod.addParameters(postData);
		
		try {
			
			client.executeMethod(postMethod);
			
			int statusCode = postMethod.getStatusCode();
			log.info("POST<"+host+"> StatusCode<"+statusCode+">");
			result = postMethod.getResponseBodyAsString();
			
		} catch (HttpException e) {
		
			log.error(e);
			
		} catch (IOException e) {
			
			log.error(e);
		
		}finally{
			
			postMethod.releaseConnection();
			
		}
		
		return result;
		
	}
	
	

}
