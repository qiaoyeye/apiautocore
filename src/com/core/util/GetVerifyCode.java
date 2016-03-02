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
 * @version ����ʱ�䣺2016��1��14�� ����10:37:30 
 * ��˵�� 
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
	        //newһ���ļ�������������ͼƬ��Ĭ�ϱ��浱ǰ���̸�Ŀ¼
	        File imageFile = new File("D:/ddd/yzm.jpg");
	        //���������
	        FileOutputStream outStream = new FileOutputStream(imageFile);
	        //д������
	        outStream.write(data);
	        //�ر������
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
	            System.out.print("yzm.txt������");
	        }
        }catch(Exception e) {
        	e.printStackTrace();
        }
        System.out.println("��֤�룺"+s);
        return s;
    //������
    }
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //����һ��Buffer�ַ���
        byte[] buffer = new byte[1024];
        //ÿ�ζ�ȡ���ַ������ȣ����Ϊ-1������ȫ����ȡ���
        int len = 0;
        //ʹ��һ����������buffer������ݶ�ȡ����
        while( (len=inStream.read(buffer)) != -1 ){
            //���������buffer��д�����ݣ��м����������ĸ�λ�ÿ�ʼ����len�����ȡ�ĳ���
            outStream.write(buffer, 0, len);
        }
        //�ر�������
        inStream.close();
        //��outStream�������д���ڴ�
        return outStream.toByteArray();
    }
}
