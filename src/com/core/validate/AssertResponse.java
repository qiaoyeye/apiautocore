package com.core.validate;

import org.apache.poi.hssf.extractor.ExcelExtractor;

import com.core.constant.ExcelHeaders;
import com.core.execute.Execute;
import com.core.util.OptionFile;

/** 
 * 验证接口响应结果
 * @author QiaoJiafei 
 * @version 创建时间：2016年3月4日 下午2:29:55 
 * 类说明 
 */
public class AssertResponse {
	private int code;
	private String responseString;
	private int sheet;
	private int caseNum;
	private Execute execute;
	
	public int getCode() {
		return code;
	}

	public String getResponseString() {
		return responseString;
	}	
	
	public AssertResponse() {}
	
	public AssertResponse(Execute execute, int sheet, int caseNum) {
		this.execute = execute;
		this.sheet = sheet;
		this.caseNum = caseNum;
		this.code = execute.getStatusCode();
		this.responseString = execute.getResponseString();
	}
	
	/**
	 * 断言的逻辑方法
	 * */
	public void validate() {
		getJsonValue(responseString, "qq");
	}
	
	/**
	 * 得到json的某个值
	 * */
	private String getJsonValue(String json, String value) {
		return "";
	}
	
	/**
	 * 往excel存值
	 * */
	private void setExcel(ExcelHeaders headers, String value) {
		int colNum = getheaderColNum(headers);
		OptionFile.setExcel("./excel/testresultnew.xls", sheet, caseNum, colNum, value);		
	}
	
	/**
	 * 自动判断表头在excel的第几列
	 * */
	private int getheaderColNum(ExcelHeaders headers) {
		int colNum = 0;
		int startCol = 3;
		int paramcount = Integer.parseInt(OptionFile.getExcel("./excel/testresultnew.xls", sheet, 1, 4));
		
		switch (headers) {
		case StatusCode:
			colNum = startCol+paramcount+1;
			break;

		case ResponseJson:
			colNum = startCol+paramcount+2;
			break;
			
		case ResultCode:
			colNum = startCol+paramcount+3;
			break;
			
		case Result:
			colNum = startCol+paramcount+5;
			break;
			
		case RUN:
			colNum = startCol+paramcount+6;
			break;

		default:
			break;
		}
		return colNum;
	}
}
