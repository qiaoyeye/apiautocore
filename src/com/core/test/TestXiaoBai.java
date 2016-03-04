package com.core.test;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.Test;

import com.core.data.MyStaticProvider;
import com.core.execute.Execute;

/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年3月2日 下午12:04:53 
 * 类说明 
 */
public class TestXiaoBai {
	HttpClient client = HttpClients.createDefault();
	public int sheet = 10;
	
	@Test(dataProvider="testdp",dataProviderClass = MyStaticProvider.class)
	public void testxb(Object caseNum) {
				
		//Execute.execute(client, 3);
		System.out.println(caseNum);
		int rowNum = (int)caseNum;
		Execute execute = new Execute(sheet);
		execute.executor(client, rowNum);
	}
}
