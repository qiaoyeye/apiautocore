package com.core.test;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

import com.core.data.MyStaticProvider;
import com.core.execute.Execute;
import com.core.util.Login;

/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年1月25日 下午3:39:59 
 * 类说明 
 */
public class TestSetPaypwd {
	HttpClient client = HttpClients.createDefault();
	public int sheet = 8;
	
	@Test(dataProvider="testdp",dataProviderClass = MyStaticProvider.class)
	public void setpaypwd(Object caseNum) {
		Login login = new Login();
		login.getResponseByPostByJson(client, 3);
		//Execute.execute(client, 3);
		System.out.println("caseNum"+caseNum);
		int rowNum = (int)caseNum;
		Execute execute = new Execute(sheet);
		execute.execute(client, rowNum);
	}
}
