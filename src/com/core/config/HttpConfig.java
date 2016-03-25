package com.core.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;

import com.core.util.OptionFile;

/** 
 * @author QiaoJiafei 
 * @version ����ʱ�䣺2016��3��4�� ����11:46:45 
 * ��˵�� 
 */
public class HttpConfig {
	private int sheet;
	private int PARAM_COUNT;
	//�õ�����ʽ��POST or GET
	private String REQUEST_TYPE;
	//�õ����ݽ�����ʽ,Json or Param
	private String TYPE;
	private HttpClient client;
	
	public HttpConfig(HttpClient client, int sheet) {
		this.sheet = sheet;
		this.PARAM_COUNT = Integer.parseInt(OptionFile.getExcel("./excel/testresultnew.xls", sheet, 1, 4));
		//�õ�����ʽ��POST or GET
		this.REQUEST_TYPE = OptionFile.getExcel("./excel/testresultnew.xls", sheet, 1, 6);
		//�õ����ݽ�����ʽ,Json or Param
		this.TYPE = OptionFile.getExcel("./excel/testresultnew.xls", sheet, 1, 8);
		this.client = client;
		
	}

	public int getSheet() {
		return sheet;
	}

	public void setSheet(int sheet) {
		this.sheet = sheet;
	}
	public int getPARAM_COUNT() {
		return PARAM_COUNT;
	}
	public void setPARAM_COUNT(int pARAM_COUNT) {
		PARAM_COUNT = pARAM_COUNT;
	}
	public String getREQUEST_TYPE() {
		return REQUEST_TYPE;
	}
	public void setREQUEST_TYPE(String rEQUEST_TYPE) {
		REQUEST_TYPE = rEQUEST_TYPE;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public HttpClient getClient() {
		return client;
	}
	public void setClient(HttpClient client) {
		this.client = client;
	}
	
    
    public static RequestConfig _getRequestConfig(){
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        return requestConfig;
    }
}
