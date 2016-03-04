package com.core.test;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

import com.core.data.MyStaticProvider;
import com.core.execute.Execute;
import com.core.util.Login;

/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年1月25日 下午2:44:46 
 * 类说明 
 */
public class TestChongZhi {
	HttpClient client = HttpClients.createDefault();
	int sheet = 6;
	
	@Test(dataProvider="testdp",dataProviderClass = MyStaticProvider.class)
	public void chongzhi(Object caseNum) {
		Login login = new Login();
		login.getResponseByPostByJson(client, 3);
		//Execute.execute(client, 3);
		System.out.println("caseNum"+caseNum);
		int rowNum = (int)caseNum;
		Execute execute = new Execute(sheet);
		execute.executor(client, rowNum);
	}
}
