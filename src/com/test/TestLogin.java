package com.test;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.data.MyStaticProvider;
import com.data.StaticProvider;
import com.file.OptionFile;
import com.util.Execute;
import com.util.Execute_od;

/** 
 * @author QiaoJiafei 
 * @version ����ʱ�䣺2016��1��14�� ����12:15:40 
 * ��˵�� 
 */
public class TestLogin {
	HttpClient client = HttpClients.createDefault();
	public int sheet = 3;
	
	@Test(dataProvider="testdp",dataProviderClass = MyStaticProvider.class)
	public void login(Object caseNum) {
				
		//Execute.execute(client, 3);
		System.out.println(caseNum);
		int rowNum = (int)caseNum;
		Execute execute = new Execute(sheet);
		execute.execute(client, rowNum);
	}
	
}
