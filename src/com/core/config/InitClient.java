package com.core.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;

/** 
 * @author QiaoJiafei 
 * @version ����ʱ�䣺2016��3��2�� ����11:34:05 
 * ��˵�� 
 */
public class InitClient {

    /** 
    * ����ssl��client
    * @Title: _createSSLClientDefault 
    * @Description: TODO
    * @return
    * @return HttpClient
    * @throws 
    */
    public static HttpClient _createSSLClientDefault() {
        try {
            return HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();

    }
    
    /** 
    * ����������client
    * @Title: defaultclient 
    * @Description: TODO
    * @return
    * @return HttpClient
    * @throws 
    */
    public static HttpClient defaultclient() {
    	return HttpClients.createDefault();
    }

}
