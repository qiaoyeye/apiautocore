package com.core.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.openqa.selenium.io.FileHandler;

/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年1月14日 上午10:37:30 
 * 类说明 
 */
public class GetVerifyCode {
	public static String getYZM(HttpClient client, String url){
        
        String s="";
        
        String imgurl = "http://172.16.30.208:8092/svc/image.jsp";
        HttpGet ht = new HttpGet(imgurl);
        HttpResponse response = null;
        try {
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
        }catch(Exception e) {
        	e.printStackTrace();
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
}
