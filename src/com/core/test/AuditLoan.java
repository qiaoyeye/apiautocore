package com.core.test;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/** 
 * @author QiaoJiafei 
 * @version ����ʱ�䣺2016��1��14�� ����4:06:10 
 * ��˵�� 
 */
public class AuditLoan {
	public static void main(String args[]) {
		test11();
	}

	public static void test11(){
		String s = "";
		int applyId = 40;
				
		HttpClient client = HttpClients.createDefault();
				
		String loginurl = "http://gmsdtech:111111@172.16.30.208:8093/login.do?username=admin&password=admin";
		
		try {
			HttpPost post = new HttpPost(loginurl);
			HttpResponse response = client.execute(post);
			
			int responseCode = response.getStatusLine().getStatusCode();
		    if (responseCode == 302 || responseCode==301) {
		    	String newuri="";
		    	Header header = response.getFirstHeader("location"); // ��ת��Ŀ���ַ���� HTTP-HEAD �е�
		        newuri = header.getValue(); // �������ת��ĵ�ַ�����������ַ���������룬�Ա�õ���ת�����Ϣ��ɶ��
		        newuri = newuri.replace("http://", "http://gmsdtech:111111@");
		        System.out.println("�ض���URL��"+newuri);
			    post = new HttpPost(newuri);
			    response = client.execute(post);
			    /*HttpEntity entity = response.getEntity();
				System.out.println(EntityUtils.toString(entity, "UTF-8"));*/
		    }
			
			for(int i=applyId;i<2040;i++){
				//String url = "http://172.16.30.244:8090/gm_product_site/assignmentApply/auth?"+"applyId="+i+"&isPass=true";
				String url = "http://172.16.30.208:8093/assignmentapply/authDo.do?"+"applyId="+i+"&isPass=true";
				System.out.println(url);
				post = new HttpPost(url);
				post.setHeader("Content-Type", "application/x-www-form-urlencoded");
				
				response = client.execute(post);
				HttpEntity	entity = response.getEntity();
			        s = EntityUtils.toString(entity, "UTF-8");
			        if(s.contains("ծȨ���ͨ�������ɹ�")){
			        	System.out.println(s);
			        }else {
			        	System.out.println("-----------------------------"+s+i);
			        }
			        //System.out.println(s);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
