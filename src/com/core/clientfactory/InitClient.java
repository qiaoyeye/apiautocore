package com.core.clientfactory;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/** 
 * @author QiaoJiafei 
 * @version ����ʱ�䣺2016��3��2�� ����11:34:05 
 * ��˵�� 
 */
public class InitClient {

    public static CloseableHttpClient _createSSLClientDefault() {
        try {
            return HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();

    }
    
    private static RequestConfig _getRequestConfig(){
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        return requestConfig;
    }
}
