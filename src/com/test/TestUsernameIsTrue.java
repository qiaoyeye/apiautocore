package com.test;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

import com.data.MyStaticProvider;
import com.util.Execute;
import com.util.Execute_od;

/** 
 * @author QiaoJiafei 
 * @version ����ʱ�䣺2016��1��20�� ����6:27:38 
 * ��˵�� 
 */
public class TestUsernameIsTrue {
	HttpClient client = HttpClients.createDefault();
	int sheet = 5;
	
	@Test(dataProvider="testdp",dataProviderClass = MyStaticProvider.class)
	public void istrue(Object caseNum) {
		//Execute.execute(client, 3);
		System.out.println(caseNum);
		int rowNum = (int)caseNum;
		Execute ce = new Execute(sheet);
		ce.execute(client, rowNum);
	}
}
