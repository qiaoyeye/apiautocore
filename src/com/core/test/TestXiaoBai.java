package com.core.test;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

import com.core.config.HttpConfig;
import com.core.config.InitClient;
import com.core.data.IStaticProvider;
import com.core.execute.Execute;
import com.core.execute.Execute;
import com.sun.org.apache.xml.internal.security.Init;

/** 
 * @author QiaoJiafei 
 * @version ����ʱ�䣺2016��3��2�� ����12:04:53 
 * ��˵�� 
 */
public class TestXiaoBai {
	//HttpClient client = HttpClients.createDefault();
	public int sheet = 10;
	
	@Test(dataProvider="testdp",dataProviderClass = IStaticProvider.class)
	public void testxb(Object caseNum) {
		HttpConfig config = new HttpConfig(InitClient.defaultclient(), sheet);
		//Execute.execute(client, 3);
		System.out.println(caseNum);
		int rowNum = (int)caseNum;
		Execute execute = new Execute(config);
		execute.executor(rowNum);
	}
}
