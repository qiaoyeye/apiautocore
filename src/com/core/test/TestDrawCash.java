package com.core.test;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

import com.core.config.HttpConfig;
import com.core.config.InitClient;
import com.core.data.IStaticProvider;
import com.core.execute.Execute;
import com.core.util.Login;

/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年1月25日 下午4:02:08 
 * 类说明 提现申请 
 */
public class TestDrawCash {
	HttpClient client = HttpClients.createDefault();
	int sheet = 7;
	
	@Test(dataProvider="testdp",dataProviderClass = IStaticProvider.class)
	public void setpaypwd(Object caseNum) {
		HttpConfig config = new HttpConfig(InitClient.defaultclient(), sheet);
		Login login = new Login(config);
		login.getResponseByPostByJson(3);
		//Execute.execute(client, 3);
		System.out.println(caseNum);
		int rowNum = (int)caseNum;
		Execute execute = new Execute(config);
		execute.executor(rowNum);
	}
}
