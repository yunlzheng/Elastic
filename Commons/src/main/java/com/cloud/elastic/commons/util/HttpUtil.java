package com.cloud.elastic.commons.util;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class HttpUtil {

	private static Log log = LogFactory.getLog(HttpUtil.class);
	
	public static String sendHttpGet(String host,Hashtable<String, String> params) {

		String result = "";
		HttpClient httpclient = new HttpClient();// 创建一个客户端
		GetMethod getMethod = new GetMethod(host);

		HttpMethodParams param = new HttpMethodParams();

		if(params!=null){
			
			/**循环添加参数*/
			for (Iterator<String> it = params.keySet().iterator(); it.hasNext();) {
				
				String key = (String) it.next();
				String value = params.get(key);
				param.setParameter(key, value);
			
			}
			
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
	
	
	
}
