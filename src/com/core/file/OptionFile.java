package com.core.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import com.gargoylesoftware.htmlunit.javascript.host.Set;



/** 
* @ClassName: OptionFile 
* @Description: 操作文件类
* @author qiaojiafei 
* @date 2015年10月22日 下午2:02:12 
*  
*/
public class OptionFile {
	public static void main(String args[]) {
		System.out.println(getExcel("D:/03excel.xls",1,2,2));
		System.out.println(getExcel("D:/07excel.xlsx",1,2,2));
		//System.out.println(getLocatorArray("D:/03excel.xls",1).length);
		
	//	System.out.println(readWord("D:/work/项目/交易C/创意阳光/创意阳关ui和模版/创意阳光/彩虹计划协议_V1.0.doc"));
	}
	//static Log log = new Log(OptionFile.class);
	/** 
	* @Title: getPropertiesValue 
	* @Description: 获取properties文件内容值，路径是死的
	* @param filename
	* @param key
	* @return
	* @return String
	* @throws 
	*/
	
	/** 
	* @Title: setExcel 
	* @Description: 设置excel，包括excel03和07
	* @param path
	* @param sheet
	* @param row
	* @param col
	* @param value
	* @return void
	* @throws 
	*/
	public static void setExcel(String path, int index, int rowNum, int colNum,String value) {
		File file = new File(path);
		String cellValue = "";
		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		try {
			FileInputStream in = new FileInputStream(file);
			if(path.endsWith(".xls")) {
				wb = new HSSFWorkbook(in);
				sheet = wb.getSheetAt(index-1);
				
				row = sheet.getRow(rowNum-1);
				cell = row.createCell(colNum-1);//目标列的索引
				HSSFRichTextString val = new HSSFRichTextString(value);
				cell.setCellValue(val);
			}else if (path.endsWith(".xlsx")) {
				wb = new XSSFWorkbook(in);
				sheet = wb.getSheetAt(index-1);
				
				row = sheet.getRow(rowNum-1);
				cell = row.createCell(colNum-1);//目标列的索引
				XSSFRichTextString val = new XSSFRichTextString(value);
				cell.setCellValue(val);
			}
			
			//cell = row.getCell(colNum-1);
			
			OutputStream out = new FileOutputStream(file);//获取文件输出流
			wb.write(out);//将内容写到excel
			out.close();
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/** 
	* @Title: getExcel 
	* @Description: 读取excel，包括excel03和07
	* @param path
	* @param index
	* @param rowNum
	* @param colNum
	* @return
	* @return String
	* @throws 
	*/
	public static String getExcel(String path,int index,int rowNum,int colNum) {
		File file = new File(path);
		String cellValue = "";
		Workbook wb = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		try {
			FileInputStream in = new FileInputStream(file);
			if(path.endsWith(".xls")) {
				wb = new HSSFWorkbook(in);
				sheet = wb.getSheetAt(index-1);
			}else if (path.endsWith(".xlsx")) {
				wb = new XSSFWorkbook(in);
				sheet = wb.getSheetAt(index-1);
			}
			row = sheet.getRow(rowNum-1);
			cell = row.getCell(colNum-1);
			if(cell!=null){
			   switch(cell.getCellType()) {
				   case Cell.CELL_TYPE_STRING:
					   cellValue = cell.getStringCellValue().trim();
					   break;
				   case Cell.CELL_TYPE_NUMERIC:
					   if(HSSFDateUtil.isCellDateFormatted(cell)) {
						   Date date = cell.getDateCellValue();
                           if (date != null) {
                        	   cellValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
                           } else {
                        	   cellValue = "";
                           }
					   }else {
						   cellValue = new DecimalFormat("###.###").format(cell.getNumericCellValue());
                        }
                        break;
				   case Cell.CELL_TYPE_FORMULA:


                       if (!cell.getStringCellValue().equals("")) {
                    	   cellValue = cell.getStringCellValue();
                       } else {
                    	   cellValue = cell.getNumericCellValue() + "";
                       }
                       break;

                   case Cell.CELL_TYPE_BLANK:
                       break;

                   case Cell.CELL_TYPE_ERROR:
                	   cellValue = "";
                       break;

                   case HSSFCell.CELL_TYPE_BOOLEAN:

                	   cellValue = (cell.getBooleanCellValue() == true ? "Y": "N");
                       break;
                       
                   default:
                	   cellValue = "";
			   }
		   }
			   in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return cellValue;
	
	}
	
	/** 
	* @Title: getExcel 
	* @Description: 获取excel的值
	* @param path
	* @param index
	* @param rowNum
	* @param colNum
	* @return
	* @return String
	* @throws 
	*/
	/*
	public static String getExcel(String path,int index,int rowNum,int colNum) {
		log.debug("读取excel："+path+"，SHEET:"+index+"，行："+rowNum+"，列："+colNum);
		File file = new File(path);
		String cellValue = "";
		int rowN = rowNum-1;//将excel的行数-1
		
		Row row = null;
		Cell cell= null;
		HSSFCell hf = null;
	
		try {
		   FileInputStream in = new FileInputStream(file);
		   HSSFWorkbook wb = new HSSFWorkbook(in);   //EXCEL03
		   HSSFSheet sheet = wb.getSheetAt(index-1);//sheet页，index从0开始	   
		   row = sheet.getRow(rowN-1);       //取得第几行,从0开始
		   cell = row.getCell(colNum-1);        //取得行的第3列,从0开始
		   if(cell!=null){
			   switch(cell.getCellType()) {
				   case Cell.CELL_TYPE_STRING:
					   cellValue = cell.getStringCellValue().trim();
					   break;
				   case Cell.CELL_TYPE_NUMERIC:
					   if(HSSFDateUtil.isCellDateFormatted(cell)) {
						   Date date = cell.getDateCellValue();
                           if (date != null) {
                        	   cellValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
                           } else {
                        	   cellValue = "";
                           }
					   }else {
						   cellValue = new DecimalFormat("###.###").format(cell.getNumericCellValue());
                        }
                        break;
				   case HSSFCell.CELL_TYPE_FORMULA:


                       if (!cell.getStringCellValue().equals("")) {
                    	   cellValue = cell.getStringCellValue();
                       } else {
                    	   cellValue = cell.getNumericCellValue() + "";
                       }
                       break;

                   case HSSFCell.CELL_TYPE_BLANK:
                       break;

                   case HSSFCell.CELL_TYPE_ERROR:
                	   cellValue = "";
                       break;

                   case HSSFCell.CELL_TYPE_BOOLEAN:

                	   cellValue = (cell.getBooleanCellValue() == true ? "Y": "N");
                       break;
                       
                   default:
                	   cellValue = "";
			   }
		   }
		   in.close();
	   }catch (Exception e) {
			  e.printStackTrace();
	   }  
	   	return cellValue;
	}
	*/
	public static String getPropertiesValue(String filename, String key) {
		String s = null;
		InputStream in = null;
		Properties props = new Properties();
		String s1 = null;
	//	log.debug("Get the properties file: "+filename+",key="+key);
		try {
			in = OptionFile.class.getClassLoader().getResourceAsStream(filename);
			props.load(in);
			s = props.getProperty(key);
			if(s!=null){
				s1 = new String(s.getBytes("ISO-8859-1"),"UTF-8");
			}else{
				//log.error("传入的值在propertis文件中不存在，值="+key);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
			return s1;
	}
	
	/** 
	* @Title: readProperties 
	* @Description: TODO读取readProperties文件，通过路径
	* @param filePath
	* @param key
	* @return
	* @return String
	* @throws 
	*/
	public static String readProperties(String filePath, String key) {
	    Properties props = new Properties();
	    String s = null;
	    String s1 = null;
	       try {
	        InputStream in = new BufferedInputStream (new FileInputStream(filePath));
	        props.load(in);
	        s = props.getProperty(key);
			if(s!=null){
				s1 = new String(s.getBytes("ISO-8859-1"),"UTF-8");
			}else{

			}
	       } catch (Exception e) {
	        e.printStackTrace();
	       }
	       return s1;
	   }
	
	/** 
	* @Title: getExcelRowCount 
	* @Description: 获取excel的行数
	* @param path
	* @param index
	* @return
	* @return int
	* @throws 
	*/
	public static int getExcelRowCount(String path, int index) {
		int rowcount = 0;
		File file = new File(path);
		Workbook wb = null;
		Sheet sheet = null;
		//Row row = null;
		//Cell cell = null;
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			if(path.endsWith(".xls")) {
				wb = new HSSFWorkbook(in);
				sheet = wb.getSheetAt(index-1);
			}else if (path.endsWith(".xlsx")) {
				wb = new XSSFWorkbook(in);
				sheet = wb.getSheetAt(index-1);
			}
			//HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(in));
			//Sheet sheet = wb.getSheetAt(index);
			Row header = sheet.getRow(0);
			rowcount = sheet.getLastRowNum()+1;
			
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return rowcount;
	}
	/** 
	* @Title: getExcelPriority 
	* @Description: 获取excel的优先级集合
	* @param path
	* @param index
	* @param p
	* @return
	* @return List<Integer>
	* @throws 
	*/
	public static List<Integer> getExcelPriority(String path, int index, String p) {
		int rowcount = 0;
		int rowstart = 4;
		String value = "";
		List<Integer> list = new ArrayList<Integer>();
		
		File file = new File(path);
		Workbook wb = null;
		Sheet sheet = null;
		//Row row = null;
		//Cell cell = null;
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			if(path.endsWith(".xls")) {
				wb = new HSSFWorkbook(in);
				sheet = wb.getSheetAt(index-1);
			}else if (path.endsWith(".xlsx")) {
				wb = new XSSFWorkbook(in);
				sheet = wb.getSheetAt(index-1);
			}
			//HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(in));
			//Sheet sheet = wb.getSheetAt(index);
			Row header = sheet.getRow(0);
			rowcount = sheet.getLastRowNum()+1;
			//System.out.println("---------------该sheet页共有行数rowcount："+rowcount);
			if(p.equals("ALL") || p.equals("P1") || p.equals("P2") || p.equals("P3")){
				for(int i=rowstart; i<=rowcount; i++) {
					value = getExcel(path, index, i, 2);
					//传入的优先级如果是P1,则执行P1；传入的优先级如果是P2，则执行P1+P2;传入的是P3，则执行P1+P2+P3；传入的是ALL，则执行所有用例
					if(p.equals("ALL")) {
						list.add(i);
					}else if(p.equals("P1")) {
						if(value.equals("P1")) {
							list.add(i);
						}
					}else if(p.equals("P2")) {
						if(value.equals("P1") || value.equals("P2")) {
							list.add(i);
						}
					}else if(p.equals("P3")) {
						if(value.equals("P1") || value.equals("P2") || value.equals("P3")) {
							list.add(i);
						}
					}
					/*
					 * 只执行传入的优先级或者执行所有的用例
					if(p.equals("ALL") || p.equals("P1") || p.equals("P2") || p.equals("P3")){
						for(int i=rowstart; i<=rowcount; i++) {
							value = getExcel(path, index, i, 2);
							//传入的优先级如果是P1,则执行P1；传入的优先级如果是P2，则执行P1+P2;传入的是P3，则执行P1+P2+P3；传入的是ALL，则执行所有用例
							if(p.equals("ALL")) {
								list.add(i);
							}else if(p.equals("P1")) {
								if(value.equals("P1")) {
									list.add(i);
								}
							}else if(p.equals("P2")) {
								if(value.equals("P2")) {
									list.add(i);
								}
							}else if(p.equals("P3")) {
								if(value.equals("P3")) {
									list.add(i);
								}
							}
						}
					}
					*/
				}
			}else {
				System.out.println("从excel读取数据时，传入的用例优先级错误"+p);
			}
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return list;
	}
}