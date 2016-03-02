package com.core.data;

import org.testng.annotations.DataProvider;

import com.core.file.OptionFile;

/** 
 * @author QiaoJiafei 
 * @version 创建时间：2016年1月14日 下午2:13:10 
 * 类说明  老写发，不建议使用
 */
public class StaticProvider {
	
	@DataProvider(name="testdp")
	public static Object[][] createData() {
		
		int case_count = 0;
		int case_start = 4;
		String priority = OptionFile.readProperties("./conf/Config.properties", "priority");
				
		if(priority.equals("0")){
			case_count = 1;
		}else if(priority.equals("1")){
			case_count = OptionFile.getExcelRowCount("./excel/testresultnew.xls", 2) - 3;
		}else {
			System.out.println("用例执行优先级错误："+priority);
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
		return arr;

	}
}
