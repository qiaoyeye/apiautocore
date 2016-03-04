package com.core.test;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.core.config.HttpConfig;
import com.core.config.InitClient;
import com.core.data.IStaticProvider;
import com.core.execute.Execute;
import com.core.util.Execute_od;
import com.core.util.OptionFile;

/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年1月14日 下午12:15:40 
 * 类说明 
 */
public class TestLogin {
	HttpClient client = HttpClients.createDefault();
	public int sheet = 3;
	
	@Test(dataProvider="testdp",dataProviderClass = IStaticProvider.class)
	public void login(Object caseNum) {
				
		//Execute.execute(client, 3);
		HttpConfig config = new HttpConfig(InitClient.defaultclient(), sheet);
		//Execute.execute(client, 3);
		System.out.println(caseNum);
		int rowNum = (int)caseNum;
		Execute execute = new Execute(config);
		execute.executor(rowNum);
	}
	
}
