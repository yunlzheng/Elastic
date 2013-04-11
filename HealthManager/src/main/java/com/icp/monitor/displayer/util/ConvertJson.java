package com.icp.monitor.displayer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.icp.monitor.commons.model.ServerInfo;

public class ConvertJson {
	public ConvertJson(){
		
	}
	public static ArrayList<ServerInfo> getServerInfo(String JsonString){
		//setDataFormat2JAVA();
		
		ArrayList<ServerInfo> list = new ArrayList<ServerInfo>();
		JSONArray array=JSONArray.fromObject(JsonString);
		if(array!=null){
			
			ServerInfo[] servers=new ServerInfo[array.size()];
			for(int i=0;i<array.size();i++){
				
				
				JSONObject jsonObject=array.getJSONObject(i);
				ServerInfo serverInfo=(ServerInfo) JSONObject.toBean(jsonObject, ServerInfo.class);
				list.add(serverInfo);
				
			}
			return list;
		}
		return null;
	}
	public static void setDataFormat2JAVA(){  
        //设定日期转换格式  
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));  
    }  
	public static String readJsonFromUrl(String url) throws IOException{
		
		StringBuilder sb= new StringBuilder();
		
		InputStream is=new URL(url).openStream();
		BufferedReader rd=new BufferedReader(new InputStreamReader(is,Charset.forName("UTF-8")) );
		int cp;
		while((cp=rd.read())!=-1){
			sb.append((char)cp);
		}
		is.close();
		rd.close();
		
		return sb.toString();
	}
	
}
