package org.lantern.testautomation.result;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.hslf.model.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestResults {
	
	static private TestResults obj = null;
	private String timeInString = null;
	private ArrayList<String> testCaseName;
	private ArrayList<String> testModuleName;
	private ArrayList<String> testResult;

	private TestResults() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date();
		timeInString = dateFormat.format(date);
		
		testCaseName = new ArrayList<String>();
		testModuleName = new ArrayList<String>();
		testResult = new ArrayList<String>();
	}
	
	public static TestResults getInstance(){
		if(obj == null){
			obj = new TestResults();
		}
		return obj;
	}
	
	public void setResult(String testcaseName , String moduleName , boolean result){
		testCaseName.add(testcaseName);
		testModuleName.add(moduleName);
		testResult.add(String.valueOf(result));
	}
	
	
	public void writeResultsToExcel(){
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("result");
		
		for(int i=0; i<testResult.size();i++){
			Row row = sheet.createRow(i);
			Cell cell0 = row.createCell(0);
			Cell cell1 = row.createCell(1);
			Cell cell2 = row.createCell(2);
			
			cell0.setCellValue(testCaseName.get(i));
			cell1.setCellValue(testModuleName.get(i));
			cell2.setCellValue(testResult.get(i));
			
			if(testResult.get(i).equalsIgnoreCase("false")){
				CellStyle style = wb.createCellStyle();
				style.setFillBackgroundColor(IndexedColors.RED.getIndex());
				cell2.setCellStyle(style);
			}
		}
		
        // Write the output to a file
        String filename = "test_result_"+timeInString+".xlsx";
        FileOutputStream out;
		try {
			File resultFile = new File(filename);
			if(!resultFile.exists()) {
				resultFile.createNewFile();
			} 
			out = new FileOutputStream(filename);
			wb.write(out);
        	out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
