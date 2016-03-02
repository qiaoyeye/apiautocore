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
 * @version 创建时间：2016年1月14日 下午4:06:10 
 * 类说明 
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
		    	Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
		        newuri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
		        newuri = newuri.replace("http://", "http://gmsdtech:111111@");
		        System.out.println("重定向URL："+newuri);
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
			        if(s.contains("债权审核通过操作成功")){
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
