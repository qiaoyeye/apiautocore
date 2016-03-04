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
 * @version ����ʱ�䣺2016��1��25�� ����2:48:28 
 * ��˵�� 
 */
public class Login {
	
	int responseCode = 0;
	//���������Ĳ������ݴӵڼ��п�ʼ
	private final int CASESTART = 4;
	//�õ������������
	private int PARAM_COUNT=2;
	//�õ�����ʽ��POST or GET
	private String REQUEST_TYPE="POST";
	//�õ����ݽ�����ʽ,Json or Param
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
				
		//System.out.println("����������"+case_count);
		
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
		    	Header header = response.getFirstHeader("location"); // ��ת��Ŀ���ַ���� HTTP-HEAD �е�
		        newuri = header.getValue(); // �������ת��ĵ�ַ�����������ַ���������룬�Ա�õ���ת�����Ϣ��ɶ��
		        System.out.println("�ض���URL��"+newuri);
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
