package com.core.execute;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.core.config.HttpConfig;
import com.core.config.InitClient;
import com.core.util.GetVerifyCode;
import com.core.util.OptionFile;

/** 
 * @author QiaoJiafei 
 * @version ����ʱ�䣺2016��3��4�� ����10:45:28 
 * ��˵�� 
 */
public class Execute {
	private int sheet;
	//��Ӧcode��������֤
	private int statusCode = 0;	
	//json��������֤
	private String responseString = "";
	public String getResponseString() {
		return responseString;
	}

	//���������Ĳ������ݴӵڼ��п�ʼ
	private final int CASESTART = 4;
	//�õ������������
	private int PARAM_COUNT;
	//�õ�����ʽ��POST or GET
	private String REQUEST_TYPE;
	//�õ����ݽ�����ʽ,Json or Param
	private String TYPE;
	private HttpClient client;
	
	public int getStatusCode() {
		return statusCode;
	}
	public HttpClient getClient() {
		return client;
	}
	public void setClient(HttpClient client) {
		this.client = client;
	}
	public Execute() {
		// TODO Auto-generated constructor stub
		sheet = 1;
		client = HttpClients.createDefault();
	}
	
	public Execute(HttpConfig config) {
		this.client = config.getClient();
		this.sheet = config.getSheet();
		this.PARAM_COUNT = config.getPARAM_COUNT();
		this.REQUEST_TYPE = config.getREQUEST_TYPE();
		this.TYPE = config.getTYPE();
	}
	
	/** 
	* @Title: execute 
	* @Description: TODO
	* @param client
	* @param sheet
	* @param row
	* @return void
	* @throws 
	*/
	public void executor(int row) {
		String priority = getPriority();

		if(REQUEST_TYPE.equals("POST") && TYPE.equals("Json")) {
			System.out.println(doPostByJson(row));
		}else if(REQUEST_TYPE.equals("POST") && TYPE.equals("Param")) {
			//System.out.println(getResponseByPostByParam(client, row));
			System.out.println(doPostByParamWithMap(row));
		}else if(REQUEST_TYPE.equals("GET") && TYPE.equals("Json")) {
			
		}else if(REQUEST_TYPE.equals("GET") && TYPE.equals("Param")) {
			System.out.println(doGetByParam(row));
		}else {
			System.out.println("��ȡexcel�л�õ�����ʽ�����ݽ�����ʽ������"+REQUEST_TYPE+", "+TYPE);
		}
		
	}
	
	/**
	 * ��ȡ���ȼ�
	 * */
	private String getPriority() {
		return OptionFile.readProperties("./conf/Config.properties", "priority");
	}
	
	/**
	 * ��ȡ�ӿڵ�ַ
	 * */
	private String getURL() {
		return OptionFile.readProperties("./conf/Config.properties", "IP")+OptionFile.getExcel("./excel/testresultnew.xls", sheet, 2, 2);
	}
	
	/**
	 * ��ȡ��������
	 * */
	private RequestConfig getRequestConfig() {
		return HttpConfig._getRequestConfig();
	}
	
	/** 
	* @Title: getResponseByPostByJson 
	* @Description: ͨ��POST��Json��ʽ���ʽӿ�
	* @param client
	* @param sheet
	* @param RowNum
	* @return
	* @return String
	* @throws 
	*/
	private String doPostByJson(int RowNum) {
		
		String responseString = "";
		String params = "";
		
		String url = getURL();
		System.out.println("Get api url:"+url);
				
		//System.out.println("����������"+case_count);
		
		StringBuffer sb = new StringBuffer("{");
		for(int i=0;i<PARAM_COUNT;i++) {			
			sb.append("\"").append(OptionFile.getExcel("./excel/testresultnew.xls", sheet, 3, i+CASESTART)).append("\":").append("\"").append(OptionFile.getExcel("./excel/testresultnew.xls", sheet, RowNum, i+CASESTART)).append("\",");
		}	
		params = sb.deleteCharAt(sb.lastIndexOf(",")).append("}").toString();
		if(params.contains("#imgyzm")) {
			String yzm = GetVerifyCode.getYZM(client, "");
			params = params.replace("#imgyzm", yzm);
		}
		System.out.println(params);
		HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(getRequestConfig());
		
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        //�����һ�е�С�״������ӵģ�������п�ɾ������һ��
        httpPost.setHeader("Accept", "application/json"); 
        
        StringEntity se = new StringEntity(params,"utf-8");
        
        //StringEntity se = new StringEntity(json);//С�״��룬û�������ַ�
        //�����2�е�С�״������ӵģ�������п�ɾ������2��
        se.setContentType("application/json"); //���÷���json���Ͳ���
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        
        httpPost.setEntity(se);
        
        return getResponseResult(httpPost);
	}
	
	/** 
	* @Title: getResponseByPostByParamWithMap 
	* @Description: ͨ��Map���ýӿڲ����ķ�ʽ����getResponseByPostByParam����ѡ��һ
	* @param client
	* @param RowNum
	* @return
	* @return String
	* @throws 
	*/
	private String doPostByParamWithMap(int RowNum) {
		
		String responseString = "";
		String params = "";
		
		String url = OptionFile.readProperties("./conf/Config.properties", "IP")+OptionFile.getExcel("./excel/testresultnew.xls", sheet, 2, 2);
		System.out.println("Get api url:"+url);
							
		//System.out.println("����������"+case_count);
		
		Map<String, String> map = new HashMap<String, String>();
		for(int i=0;i<PARAM_COUNT;i++) {			
			//sb.append(OptionFile.getExcel("./excel/testresultnew.xls", sheet, 3, i+CASESTART)).append("=").append(OptionFile.getExcel("./excel/testresultnew.xls", sheet, RowNum, i+CASESTART)).append("&");
			String key = OptionFile.getExcel("./excel/testresultnew.xls", sheet, 3, i+CASESTART);
			String value = OptionFile.getExcel("./excel/testresultnew.xls", sheet, RowNum, i+CASESTART);
			map.put(key, value);
		}
		
		List<NameValuePair> ps = new ArrayList<NameValuePair>();
        for (String pKey : map.keySet()) {
        	String temp = map.get(pKey);
        	
        	//��ҪΪ��ע��ʱ���ж��Ƿ���Ҫ��֤��
        	if (temp.contains("#imgyzm")) {
        		temp = GetVerifyCode.getYZM(client, "");
			}
            ps.add(new BasicNameValuePair(pKey, temp));
        }
        
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(getRequestConfig());
        
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(ps));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		return getResponseResult(httpPost);
	}
	
	
	/** 
	* @Title: getResponseByGetByParam 
	* @Description: ͨ��POST����Param��ʽ���ʽӿ�
	* @param client
	* @param sheet
	* @param RowNum
	* @return
	* @return String
	* @throws 
	*/
	private String doPostByParam(int RowNum) {
		
		String responseString = "";
		String params = "";
		
		String url = OptionFile.readProperties("./conf/Config.properties", "IP")+OptionFile.getExcel("./excel/testresultnew.xls", sheet, 2, 2);
		System.out.println("Get api url:"+url);
							
		//System.out.println("����������"+case_count);
		
		StringBuffer sb = new StringBuffer("?");
		for(int i=0;i<PARAM_COUNT;i++) {			
			sb.append(OptionFile.getExcel("./excel/testresultnew.xls", sheet, 3, i+CASESTART)).append("=").append(OptionFile.getExcel("./excel/testresultnew.xls", sheet, RowNum, i+CASESTART)).append("&");
		}	
		params = sb.deleteCharAt(sb.lastIndexOf("&")).toString();
		if(params.contains("#imgyzm")) {
			String yzm = GetVerifyCode.getYZM(client, "");
			params = params.replace("#imgyzm", yzm);
		}
		System.out.println(params);
		HttpPost httpPost = new HttpPost(url+params);
        
		/*
		httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity se = new StringEntity(params,"utf-8");
        httpPost.setEntity(se);
        */
        
		return getResponseResult(httpPost);
	}
	
	/** 
	* @Title: getResponseByGetByParam 
	* @Description: ͨ��GET����param�ķ�ʽ���ʽӿ�
	* @param client
	* @param RowNum
	* @return
	* @return String
	* @throws 
	*/
	private String doGetByParam(int RowNum) {
		
		String responseString = "";
		String params = "";
		
		String url = OptionFile.readProperties("./conf/Config.properties", "IP")+OptionFile.getExcel("./excel/testresultnew.xls", sheet, 2, 2);
		System.out.println("Get api url:"+url);
							
		//System.out.println("����������"+case_count);
		
		StringBuffer sb = new StringBuffer("?");
		for(int i=0;i<PARAM_COUNT;i++) {			
			sb.append(OptionFile.getExcel("./excel/testresultnew.xls", sheet, 3, i+CASESTART)).append("=").append(OptionFile.getExcel("./excel/testresultnew.xls", sheet, RowNum, i+CASESTART)).append("&");
		}	
		params = sb.deleteCharAt(sb.lastIndexOf("&")).toString();
		if(params.contains("#imgyzm")) {
			String yzm = GetVerifyCode.getYZM(client, "");
			params = params.replace("#imgyzm", yzm);
		}
		System.out.println(params);
		HttpGet httpGet = new HttpGet(url+params);

        httpGet.setConfig(getRequestConfig());
        //-------------------------------------------------------------
		/*
		httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity se = new StringEntity(params,"utf-8");
        httpPost.setEntity(se);
        */
        
       return getResponseResult(httpGet);
	}
	
	private String getResponseResult(HttpUriRequest request) {
		String responseString = "";
		HttpResponse response = null;
        HttpEntity entity = null;
        try {
			response = client.execute(request);		
			statusCode = response.getStatusLine().getStatusCode();
		    if (statusCode == 302 || statusCode==301) {
		    	String newuri="";
		    	Header header = response.getFirstHeader("location"); // ��ת��Ŀ���ַ���� HTTP-HEAD �е�
		        newuri = header.getValue(); // �������ת��ĵ�ַ�����������ַ���������룬�Ա�õ���ת�����Ϣ��ɶ��
		        System.out.println("�ض���URL��"+newuri);
		        request = new HttpGet(newuri);
			    response = client.execute(request);
			    statusCode = response.getStatusLine().getStatusCode();
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
	
	/** 
	* URI�ض�����
	* @Title: redirectURI 
	* @Description: TODO
	* @param request
	* @return
	* @return String
	* @throws 
	*/
	private String redirectURI(HttpUriRequest request) {
		
		HttpResponse response = null;
        HttpEntity entity = null;
		String newuri="";
    	Header header = response.getFirstHeader("location"); // ��ת��Ŀ���ַ���� HTTP-HEAD �е�
        newuri = header.getValue(); // �������ת��ĵ�ַ�����������ַ���������룬�Ա�õ���ת�����Ϣ��ɶ��
        System.out.println("�ض���URL��"+newuri);
        request = new HttpGet(newuri);
	    /*
	    httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
	    se = new StringEntity(params);
	    httpPost.setEntity(se);
	    */
	    try {
			response = client.execute(request);
			statusCode = response.getStatusLine().getStatusCode();
		    entity = response.getEntity();
		    responseString = EntityUtils.toString(entity, "UTF-8");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return responseString;
	}

	/*private String getResponse(HttpClient client, int sheet, int RowNum) {
	
		String responseString = "";
		String params = "";
		
		String url = OptionFile.readProperties("./conf/Config.properties", "IP")+OptionFile.getExcel("./excel/testresultnew.xls", sheet, 2, 2);
		System.out.println("Get api url:"+url);
				
		
		
		//System.out.println("����������"+case_count);
		
		if (TYPE.equals("Json")) {
			StringBuffer sb = new StringBuffer("{");
			for(int i=0;i<PARAM_COUNT;i++) {			
				sb.append("\"").append(OptionFile.getExcel("./excel/testresultnew.xls", sheet, 3, i+CASESTART)).append("\":").append("\"").append(OptionFile.getExcel("./excel/testresultnew.xls", sheet, RowNum, i+CASESTART)).append("\",");
			}	
			params = sb.deleteCharAt(sb.lastIndexOf(",")).append("}").toString();
			if(params.contains("#imgyzm")) {
				String yzm = GetVerifyCode.getYZM(client, "");
				params = params.replace("#imgyzm", yzm);
			}
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
			
		}else if(TYPE.equals("Param")) {
			if(REQUEST_TYPE.equals("GET")) {
				
			}else if(REQUEST_TYPE.equals("POST")) {
				
			}else{
				System.out.println("����ʽ��ȡ����"+REQUEST_TYPE);
			}
		}else {
			System.out.println("�õ����ݽ�����ʽ��ȡ����"+TYPE);
		}
		
		
		return responseString;
	}*/
	
}
