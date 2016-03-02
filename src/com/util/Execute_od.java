package com.util;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.file.OptionFile;

/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年1月14日 上午10:39:53 
 * 类说明 老写法，不建议使用
 */
public class Execute_od {
	
	int sheet;
	int responseCode = 0;
	//测试用例的参数数据从第几列开始
	final int CASESTART = 4;
	//得到参数请求个数
	final int PARAM_COUNT = Integer.parseInt(OptionFile.getExcel("./excel/testresultnew.xls", sheet, 1, 4));
	//得到请求方式，POST or GET
	final String REQUEST_TYPE = OptionFile.getExcel("./excel/testresultnew.xls", sheet, 1, 6);
	//得到数据交换方式,Json or Param
	final String TYPE = OptionFile.getExcel("./excel/testresultnew.xls", sheet, 1, 8);
	public Execute_od() {
		// TODO Auto-generated constructor stub
		sheet = 1;
	}
	public Execute_od(int sheet) {
		// TODO Auto-generated constructor stub
		this.sheet = sheet;
	}
	
	public static void main(String args[]) {
		HttpClient client = HttpClients.createDefault();
		//execute(client, 3);
		Execute_od ecExecute = new Execute_od(3);
		System.out.println(ecExecute.responseCode);
	}
	
	public void execute(HttpClient client, int sheet, int row) {
		String priority = OptionFile.readProperties("./conf/Config.properties", "priority");
/*		if(priority.equals("0")){
			case_count = 1;
		}else if(priority.equals("1")){
			case_count = OptionFile.getExcelRowCount("./excel/testresultnew.xls", sheet) - 3;
		}else {
			System.out.println("用例执行优先级错误"+priority);
		}*/
		
			System.out.println(getResponse(client, sheet, row));
		
	}
		
	public String getResponse(HttpClient client, int sheet, int RowNum) {
	
		String responseString = "";
		String params = "";
		
		String url = OptionFile.readProperties("./conf/Config.properties", "IP")+OptionFile.getExcel("./excel/testresultnew.xls", sheet, 2, 2);
		System.out.println("Get api url:"+url);
				
		
		
		//System.out.println("用例个数："+case_count);
		
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
			
		}else if(TYPE.equals("Param")) {
			if(REQUEST_TYPE.equals("GET")) {
				
			}else if(REQUEST_TYPE.equals("POST")) {
				
			}else{
				System.out.println("请求方式获取错误："+REQUEST_TYPE);
			}
		}else {
			System.out.println("得到数据交换方式获取错误："+TYPE);
		}
		
		
		return responseString;
	}
	
}
