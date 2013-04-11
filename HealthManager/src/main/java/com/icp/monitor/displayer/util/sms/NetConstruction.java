package com.icp.monitor.displayer.util.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.context.ApplicationContext;

import com.icp.monitor.commons.bean.Response;
import com.icp.monitor.commons.bean.SmsNetConstruction;
import com.icp.monitor.commons.dao.SmsNetConstructionDao;

/**
 *网建接口
 */
public class NetConstruction {

	/**
	 * 接受信息人电话号码(小灵通不能发送)
	 */
	private String reciverNum;  //"15196635305"
	
	private SmsNetConstructionDao scDao = null;
	
	private Response response = null;
	
	private ApplicationContext content = null;
	/**
	 * 短信发送内容
	 */
	private String messageContent; //"尊敬的同事你好:你收到的是中国网建的测试短信,收到请回复"
	
	public NetConstruction(String reciverNum,String messageContent,ApplicationContext content){
		this.reciverNum = reciverNum;
		this.messageContent = messageContent;
		this.content = content;
	}
	
	/**
	 * 网建接口
	 */
	public Response smsNetConSendMessage(){

		scDao = (SmsNetConstructionDao)content.getBean("smsNetConstructionDao");
		response = new Response();
		
		try {
			
			SmsNetConstruction snc = null; 
			
			if((scDao.list("from SmsNetConstruction")).size() >= 1){
				snc = scDao.get(1);
			}
			
			String encoding = "";//snc.getEncoding();
			String username = "";//snc.getUsername(); //发送放的姓名
			String key = "";//snc.getKey();  //发送方的安全密码 "ca747cab2ee49c028e4c"
			String urlHead = "http://";
			String urlEnd = ".sms.webchinese.cn";
			String url = urlHead + encoding + urlEnd;
			
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(url); 
			post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset="+encoding);//在头文件中设置转码
			NameValuePair[] data ={ new NameValuePair("Uid", username),new NameValuePair("Key", key),new NameValuePair("smsMob",reciverNum),new NameValuePair("smsText",messageContent)};
			post.setRequestBody(data);

			client.executeMethod(post);
			
			String result = new String(post.getResponseBodyAsString().getBytes("gbk")); //返回发送成功的条数
			System.out.println("发送条数:" + result);
			
			
			if(Integer.parseInt(result) > 0){
				response.setCode("200");
				response.setStatus("success");
			}

			post.releaseConnection();
	      } catch (Exception e) {
			response.setCode("503");
			response.setStatus("false");
			e.printStackTrace();
		}
			return response;
		}
	
}
