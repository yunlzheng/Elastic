package com.icp.monitor.commons.util.url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlUtil {
	public UrlUtil(){
	}
	public static UrlInfo getUrlInfo(String url){
		UrlInfo urlInfo=new UrlInfo();
		try {
			long startTime=System.currentTimeMillis();
			URL url1=new URL(url);
			HttpURLConnection urlConnection=(HttpURLConnection)url1.openConnection();
			urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			InputStream in=urlConnection.getInputStream();
			InputStreamReader isr=new InputStreamReader(in);
			BufferedReader br=new BufferedReader(isr);
			while(br.readLine()!=null){
				
			}
			long endTime=System.currentTimeMillis();
			br.close();
			isr.close();
			in.close();
            urlInfo.setTime(String.valueOf(endTime-startTime));
            urlInfo.setStatus("success");
            urlInfo.setUrl(url);         		
            return urlInfo;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			urlInfo.setTime("0");
            urlInfo.setStatus("false");
            urlInfo.setUrl(url);
			return urlInfo;
			
		}	
	}
	public static void main(String[] args) {
		UrlInfo u1=new UrlUtil().getUrlInfo("http://topic.csdn.net/u/20120411/13/62560074-cf30-4aa2-a69c-86f29a4c06c7.html");
		System.out.println(u1.getUrl());
		System.out.println(u1.getStatus());
		System.out.println(u1.getTime());
		UrlInfo u2=new UrlUtil().getUrlInfo("http://www.cdu.edu.cn");
		System.out.println(u2.getUrl());
		System.out.println(u2.getStatus());
		System.out.println(u2.getTime());
	}
}
