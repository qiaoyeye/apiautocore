package com.core.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;

/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年3月2日 上午11:34:05 
 * 类说明 
 */
public class InitClient {

    /** 
    * 创建ssl的client
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
    * 创建正常的client
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
