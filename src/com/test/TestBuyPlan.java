package com.test;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

import com.data.MyStaticProvider;
import com.util.Execute;
import com.util.Login;

/**
 * @author QiaoJiafei
 * @version ����ʱ�䣺2016��1��25�� ����3:47:40 ��˵��
 */
public class TestBuyPlan {
	HttpClient client = HttpClients.createDefault();
	int sheet = 9;

	@Test(dataProvider = "testdp", dataProviderClass = MyStaticProvider.class)
	public void setpaypwd(Object caseNum) {
		Login login = new Login();
		login.getResponseByPostByJson(client, 3);
		// Execute.execute(client, 3);
		System.out.println("caseNum" + caseNum);
		int rowNum = (int) caseNum;
		Execute execute = new Execute(sheet);
		execute.execute(client, rowNum);
	}

}
