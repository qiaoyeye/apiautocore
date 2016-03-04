package com.core.data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.core.ienum.Priority;
import com.core.util.OptionFile;

/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年1月14日 下午2:13:10 
 * 类说明 
 */
public class IStaticProvider {
		
	@DataProvider(name="testdp")
	public static Iterator<Object[]> createData(Method method, ITestContext context) throws Exception{
		
		//使用java反射机制，自动获取运行的测试类，并获得该类的成员变量的值
		String testclass = method.getDeclaringClass().getName();
		System.out.println("数据驱动得到的class："+testclass);
		Class clzz = Class.forName(testclass);
		Object object = clzz.newInstance();
		Field field = clzz.getDeclaredField("sheet");
		String testsheet = field.get(object).toString();
		System.out.println("数据驱动得到的sheet："+testsheet);
		
		int case_count = 0;
		int case_start = 4;
		int sheet = Integer.parseInt(testsheet);
		Priority p = null;
		final String P_FROM_PROPERTY = OptionFile.readProperties("./conf/Config.properties", "priority");
		System.out.println("获取到的优先级为："+P_FROM_PROPERTY);
		/*
		 * 只执行传入的优先级用例
		if(P_FROM_PROPERTY.equals("P1")) {
			p = Priority.P1;
		}else if(P_FROM_PROPERTY.equals("P2")) {
			p = Priority.P2;
		}else if(P_FROM_PROPERTY.equals("P3")) {
			p = Priority.P3;
		}else if(P_FROM_PROPERTY.equals("ALL")) {
			p = Priority.ALL;
		}else {
			System.out.println("数据提供类获取到的用例执行优先级错误："+P_FROM_PROPERTY);
		}
		
		List<Object[]> list = new ArrayList<Object[]>();
		switch (p) {
		case P1:
			
			break;
		case P2:
			
			break;
		case P3:
			
			break;
		case ALL:
			
			break;

		default:
			break;
		}
		
		*/
		
		//---------------读取excel并获取执行的用例--------------
		
		List<Integer> listtemp = OptionFile.getExcelPriority("./excel/testresultnew.xls", sheet, P_FROM_PROPERTY);
		System.out.println("-------------listtemp用例总共:"+listtemp.size());
		List<Object[]> list = new ArrayList<Object[]>();
		for(Integer itg:listtemp) {
			list.add(new Object[]{itg});
		}
		return list.iterator();
		
		//-----------------------------
/*	
 * 老写发，如果用例优先是0，则执行第一条，如果用例优先级是1，则执行所有
 * 	if(P_FROM_PROPERTY.equals("0")){
			case_count = 1;
		}else if(P_FROM_PROPERTY.equals("1")){
			case_count = OptionFile.getExcelRowCount("./excel/testresultnew.xls", 2) - 3;
		}else {
			System.out.println("用例执行优先级错误"+P_FROM_PROPERTY);
		}
		Integer arr[][] = new Integer[case_count][1];
		
		if(case_count>0) {
			//int array [][] = new int[1][case_count];
			for(int i=0;i<arr.length;i++) {
				//array[0][i] = case_start+i;
				arr[i][0] = Integer.valueOf(case_start+i);
				//return arr;
			}
		}
		else {
			System.out.println("用例的执行个数错误："+case_count);
		}
		return arr;*/

	}
}
