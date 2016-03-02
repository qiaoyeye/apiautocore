package com.core.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.apache.xmlbeans.impl.xb.xmlconfig.ConfigDocument.Config;
import org.openqa.selenium.io.FileHandler;

import com.core.file.OptionFile;

/** 
 * @author QiaoJiafei 
 * @version 创建时间：2015年11月17日 下午2:51:24 
 * 类说明 
 */
public class TestRegist {
	static HttpClient client=null;
	static String s = "";
	final String CONTENT_TYPE_TEXT_JSON = "text/json";
	public static void main(String args[]) {
		HttpClient client = HttpClients.createDefault();	
		TestRegist tRegist = new TestRegist();
		try {
			//tRegist.getYZM(client);
			for(int i=2;i<3;i++){
				String s = tRegist.regist(client, i);
				int count=0;
				boolean flag = false;
				if(!s.contains("成功")) {
					flag = true;
					System.out.println("----------------------------第"+i+"行不成功-----------------------");
				}
				while(flag){
					System.out.println("----------------------------再次尝试第"+i+"行-----------------------");
					s = tRegist.regist(client, i);
					if(s.contains("成功")) {
						flag = false;
						System.out.println("----------------------------尝试成功-----------------------");
					}
					count++;
					if(count == 2){
						System.out.println("----------------------------尝试"+count+"次不成功，不再尝试---------------------");
						flag = false;
						//continue;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getYZM(HttpClient client) throws Exception{
        
        String s="";
        
        String imgurl = "http://172.16.30.208:8092/svc/image.jsp";
        HttpGet ht = new HttpGet(imgurl);
        HttpResponse response = null;
        response = client.execute(ht);
        HttpEntity entity = response.getEntity();
        InputStream inStream = entity.getContent();
        byte[] data = readInputStream(inStream);
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File("D:/ddd/yzm.jpg");
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据
        outStream.write(data);
        //关闭输出流
        outStream.close();
        
        Runtime rt = Runtime.getRuntime();
        rt.exec("cmd.exe /C  tesseract.exe D:\\ddd\\yzm.jpg  D:\\ddd\\yzm -1 ");
        Thread.sleep(1000);
        File file = new File("D:\\ddd\\yzm.txt");
        if(file.exists()) {
            FileHandler fh = new FileHandler();
            s = fh.readAsString(file).trim();
           // System.out.println("========="+s);
        } else {
            System.out.print("yzm.txt不存在");
        }
        System.out.println("验证码："+s);
        return s;
    //打开链接
    }
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }
	
	public String regist(HttpClient client,int rowNum) throws Exception {
		String url = OptionFile.readProperties("./conf/Config.properties", "IP")+OptionFile.getExcel("./excel/testresultnew.xls", 2, rowNum, 2);
		String yzm = getYZM(client);
		System.out.println("api url="+url);
/*		String usn = OptionFile.getExcel("D:\\work\\other\\6万个自然人.xls", 1, rowNum, 5);
		String idc = OptionFile.getExcel("D:\\work\\other\\6万个自然人.xls", 1, rowNum, 8);
		String realn = OptionFile.getExcel("D:\\work\\other\\6万个自然人.xls", 1, rowNum, 2);*/
		
		/*String usn = "13700000007";
		String idc = "410882199412150474";
		String realn = "米型";
		String js = "{\"userName\":"+"\""+usn+"\""+",\"password\":\"123456\",\"confirmPwd\":\"123456\",\"idCard\":"+"\""+idc+"\""+",\"randomChar\":\"685432\",\"validateChar\":\"728026\",\"verifyCode\":"+"\""+yzm+"\""+",\"realName\":"+"\""+realn+"\""+"}";
		*/
		StringBuffer sb = new StringBuffer("{");
		int totol = 8;
		for(int i=0;i<totol;i++) {
			
			sb.append("\"").append(OptionFile.getExcel("./excel/testresultnew.xls", 2, 3, i+4)).append("\":").append("\"").append(OptionFile.getExcel("./excel/testresultnew.xls", 2, 4, i+4)).append("\",");
		}	
		String js = sb.deleteCharAt(sb.lastIndexOf(",")).append("}").toString();
		if(js.contains("#imgyzm")) {
			js = js.replace("#imgyzm", yzm);
		}
		System.out.println(js);
		
		HttpPost httpPost = new HttpPost(url);
        
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        //httpPost.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8"); 
        //httpPost.addHeader(name, value);
        //httpPost.addHeader("Content-Type", APPLICATION_JSON);
        
        StringEntity se = new StringEntity(js,"utf-8");
        //se.setContentType(CONTENT_TYPE_TEXT_JSON);
        //se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        httpPost.setEntity(se);
        System.out.println(httpPost.getAllHeaders().toString());
        
        HttpResponse response2 = null;
        
        response2 = client.execute(httpPost);
        HttpEntity entity2 = null;
		entity2 = response2.getEntity();
		
		String s2 = EntityUtils.toString(entity2, "UTF-8");
		System.out.println(s2);
		return s2;

	}
	
	
	
	public void login(){
		String url = "http://172.16.30.208:8092/svc/login";
		String js = "{\"username\":\"13800000001\",\"password\":\"123456\"}";
		
		//client = HttpClients.createDefault();
		DefaultHttpClient client = new DefaultHttpClient(
				new PoolingClientConnectionManager());
        
		try {
			HttpPost httpPost = new HttpPost(url);
            
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            
            //httpPost.addHeader("Content-Type", APPLICATION_JSON);
            
            StringEntity se = new StringEntity(js);
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            //se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(se);
            
            CloseableHttpResponse response2 = null;
            
            response2 = client.execute(httpPost);
            HttpEntity entity2 = null;
			entity2 = response2.getEntity();
			String s2 = EntityUtils.toString(entity2, "UTF-8");
			System.out.println(s2);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	
}
