package com.core.test;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

import com.core.config.HttpConfig;
import com.core.config.InitClient;
import com.core.data.IStaticProvider;
import com.core.execute.Execute;
import com.core.util.Execute_od;

/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年1月20日 下午6:27:38 
 * 类说明 
 */
public class TestUsernameIsTrue {
	HttpClient client = HttpClients.createDefault();
	int sheet = 5;
	
	@Test(dataProvider="testdp",dataProviderClass = IStaticProvider.class)
	public void istrue(Object caseNum) {
		//Execute.execute(client, 3);
		HttpConfig config = new HttpConfig(InitClient.defaultclient(), sheet);
		//Execute.execute(client, 3);
		System.out.println(caseNum);
		int rowNum = (int)caseNum;
		Execute execute = new Execute(config);
		execute.executor(rowNum);
	}
}
