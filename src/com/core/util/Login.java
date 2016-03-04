package com.core.util;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.core.config.HttpConfig;

/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年1月25日 下午2:48:28 
 * 类说明 
 */
public class Login {
	
	int responseCode = 0;
	//测试用例的参数数据从第几列开始
	private final int CASESTART = 4;
	//得到参数请求个数
	private int PARAM_COUNT=2;
	//得到请求方式，POST or GET
	private String REQUEST_TYPE="POST";
	//得到数据交换方式,Json or Param
	private String TYPE="Json";
	private HttpClient client;
	
	public Login() {}
	
	public Login(HttpConfig config) {
		this.client = config.getClient();
	}
	public String getResponseByPostByJson(int sheet) {
		
		String responseString = "";
		String params = "";
		
		String url = OptionFile.readProperties("./conf/Config.properties", "IP")+OptionFile.getExcel("./excel/testresultnew.xls", sheet, 2, 2);
		System.out.println("Get api url:"+url);
				
		//System.out.println("用例个数："+case_count);
		
		StringBuffer sb = new StringBuffer("{");
		for(int i=0;i<PARAM_COUNT;i++) {			
			sb.append("\"").append(OptionFile.getExcel("./excel/testresultnew.xls", sheet, 3, i+CASESTART)).append("\":").append("\"").append(OptionFile.getExcel("./excel/testresultnew.xls", sheet, 4, i+CASESTART)).append("\",");
		}	
		params = sb.deleteCharAt(sb.lastIndexOf(",")).append("}").toString();
		System.out.println(params);
		HttpPost httpPost = new HttpPost(url);
        
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        
        StringEntity se = new StringEntity(params,"utf-8");
        httpPost.setEntity(se);
        
        HttpResponse response = null;
        HttpEntity entity = null;
        try {
			response = client.execute(httpPost);		
			responseCode = response.getStatusLine().getStatusCode();
		    if (responseCode == 302 || responseCode==301) {
		    	String newuri="";
		    	Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
		        newuri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
		        System.out.println("重定向URL："+newuri);
			    httpPost = new HttpPost(newuri);
			    httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
			     
			    se = new StringEntity(params);
			    httpPost.setEntity(se);
			    response = client.execute(httpPost);
			    responseCode = response.getStatusLine().getStatusCode();
			    entity = response.getEntity();
			    responseString = EntityUtils.toString(entity, "UTF-8");
		    }else {
		    	entity = response.getEntity();
		    	responseString = EntityUtils.toString(entity, "UTF-8");
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		
		return responseString;
	}

}
