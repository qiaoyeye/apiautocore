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
* @Description: �����ļ���
* @author qiaojiafei 
* @date 2015��10��22�� ����2:02:12 
*  
*/
public class OptionFile {
	public static void main(String args[]) {
		System.out.println(getExcel("D:/03excel.xls",1,2,2));
		System.out.println(getExcel("D:/07excel.xlsx",1,2,2));
		//System.out.println(getLocatorArray("D:/03excel.xls",1).length);
		
	//	System.out.println(readWord("D:/work/��Ŀ/����C/��������/��������ui��ģ��/��������/�ʺ�ƻ�Э��_V1.0.doc"));
	}
	//static Log log = new Log(OptionFile.class);
	/** 
	* @Title: getPropertiesValue 
	* @Description: ��ȡproperties�ļ�����ֵ��·��������
	* @param filename
	* @param key
	* @return
	* @return String
	* @throws 
	*/
	
	/** 
	* @Title: setExcel 
	* @Description: ����excel������excel03��07
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
				cell = row.createCell(colNum-1);//Ŀ���е�����
				HSSFRichTextString val = new HSSFRichTextString(value);
				cell.setCellValue(val);
			}else if (path.endsWith(".xlsx")) {
				wb = new XSSFWorkbook(in);
				sheet = wb.getSheetAt(index-1);
				
				row = sheet.getRow(rowNum-1);
				cell = row.createCell(colNum-1);//Ŀ���е�����
				XSSFRichTextString val = new XSSFRichTextString(value);
				cell.setCellValue(val);
			}
			
			//cell = row.getCell(colNum-1);
			
			OutputStream out = new FileOutputStream(file);//��ȡ�ļ������
			wb.write(out);//������д��excel
			out.close();
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/** 
	* @Title: getExcel 
	* @Description: ��ȡexcel������excel03��07
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
	* @Description: ��ȡexcel��ֵ
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
		log.debug("��ȡexcel��"+path+"��SHEET:"+index+"���У�"+rowNum+"���У�"+colNum);
		File file = new File(path);
		String cellValue = "";
		int rowN = rowNum-1;//��excel������-1
		
		Row row = null;
		Cell cell= null;
		HSSFCell hf = null;
	
		try {
		   FileInputStream in = new FileInputStream(file);
		   HSSFWorkbook wb = new HSSFWorkbook(in);   //EXCEL03
		   HSSFSheet sheet = wb.getSheetAt(index-1);//sheetҳ��index��0��ʼ	   
		   row = sheet.getRow(rowN-1);       //ȡ�õڼ���,��0��ʼ
		   cell = row.getCell(colNum-1);        //ȡ���еĵ�3��,��0��ʼ
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
				//log.error("�����ֵ��propertis�ļ��в����ڣ�ֵ="+key);
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
	* @Description: TODO��ȡreadProperties�ļ���ͨ��·��
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
	* @Description: ��ȡexcel������
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
	* @Description: ��ȡexcel�����ȼ�����
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
			//System.out.println("---------------��sheetҳ��������rowcount��"+rowcount);
			if(p.equals("ALL") || p.equals("P1") || p.equals("P2") || p.equals("P3")){
				for(int i=rowstart; i<=rowcount; i++) {
					value = getExcel(path, index, i, 2);
					//��������ȼ������P1,��ִ��P1����������ȼ������P2����ִ��P1+P2;�������P3����ִ��P1+P2+P3���������ALL����ִ����������
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
					 * ִֻ�д�������ȼ�����ִ�����е�����
					if(p.equals("ALL") || p.equals("P1") || p.equals("P2") || p.equals("P3")){
						for(int i=rowstart; i<=rowcount; i++) {
							value = getExcel(path, index, i, 2);
							//��������ȼ������P1,��ִ��P1����������ȼ������P2����ִ��P1+P2;�������P3����ִ��P1+P2+P3���������ALL����ִ����������
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
				System.out.println("��excel��ȡ����ʱ��������������ȼ�����"+p);
			}
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return list;
	}
}