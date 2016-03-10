package org.lantern.datareader;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lantern.data.TestCaseData;
import org.lantern.data.TestModuleData;
import org.lantern.data.TestScenarioData;
import org.lantern.module.TestModule;

public class ExcelReader {
	
	public TestScenarioData loadDataFromFile(String filePath){
		TestScenarioData sData = null;
		
		sData = new TestScenarioData("Lantern Test Automation");
		
		 try
	        {
	            FileInputStream file = new FileInputStream(new File(filePath));
	 
	            //Create Workbook instance holding reference to .xlsx file
	            XSSFWorkbook workbook = new XSSFWorkbook(file);
	 
	            //Get first/desired sheet from the workbook
	            XSSFSheet sheet = workbook.getSheetAt(0);
	 
	            //Iterate through each rows one by one
	            Iterator<Row> rowIterator = sheet.iterator();
	            
	            //First load the test cases from the 1st Sheet
	            loadTestCasesToRun(sData, rowIterator);
	            
	            
	            
	            //Get second sheet from the workbook
	            sheet = workbook.getSheetAt(1);
	 
	            loadTestCaseDefinitions(sData,sheet);
	            
	            file.close();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
		return sData;
	}
	
	boolean loadTestCasesToRun(TestScenarioData sData ,Iterator<Row> rowIterator){
		
		int columnCounter;
		String testCaseName;
		
		//skip header
		Row row = rowIterator.next();
		
		while (rowIterator.hasNext())
        {
			TestCaseData cData;
			columnCounter = 1;
            testCaseName = null;
            
            row = rowIterator.next();
            //For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();
            
            while (cellIterator.hasNext())
            {
                Cell cell = cellIterator.next();
                
                //Check for testcase name
                if(columnCounter == 1){
                	testCaseName = cell.getStringCellValue();
                }
                
                //check whether to run
                if(columnCounter == 2){
                	String run = cell.getStringCellValue();
                	
                	if(run.equalsIgnoreCase("y") || run.equalsIgnoreCase("yes")){
                		cData = new TestCaseData(testCaseName);
                		sData.addToContent(cData);
                	}
                }
                columnCounter++;
            }
        }
		return true;
	}
	
	boolean  loadTestCaseDefinitions(TestScenarioData sData ,XSSFSheet sheet){
	
		final int TESTCASE_COLUMN = 0;
		String tcName;
		Iterator<Cell> cellIterator;
		
		
		ArrayList<TestCaseData> tcList = (ArrayList<TestCaseData>) sData.getContent();
		
		for(int i=0; i < tcList.size();i++){
			
			//Start iteration from top of the sheet to search for test case definition
            Iterator<Row> rowIterator = sheet.iterator();

			//skip header
			Row row = rowIterator.next();
			
			TestCaseData cData = tcList.get(i);
			
			tcName = cData.getName();
			while (rowIterator.hasNext())
	        {
				row = rowIterator.next();
	            Cell cell = row.getCell(TESTCASE_COLUMN);
	            if(tcName.equalsIgnoreCase(cell.getStringCellValue())){
	                loadModuleDefinitions(cData, rowIterator , row);
	                break;
	            }
	            
				
	        }
		}
		return true;
	}
	
	Row loadModuleDefinitions(TestCaseData cData,Iterator<Row> rowIterator , Row row){
		
		final int MODULE_COLUMN = 1;
		
		ArrayList<TestModuleData> tmList;
		TestModuleData mData;
		
		int columnCounter;
		
		//Get module array list
		tmList = (ArrayList<TestModuleData>) cData.getContent();
		
		//Now get the module in that row
		Cell cell = row.getCell(MODULE_COLUMN);
		
		
		if(cell.getStringCellValue() != null || (!cell.getStringCellValue().isEmpty())){
			mData = new TestModuleData(cell.getStringCellValue());
			tmList.add(mData);
			
			row = loadModuleParameters(mData, rowIterator , row);
			
			if(row == null){
				// End of file
				return null;
			}
			//Second Column
			String moduleName = row.getCell(MODULE_COLUMN).getStringCellValue();
			if(moduleName != null && moduleName != ""){
				return 	 loadModuleDefinitions(cData, rowIterator, row);

			}else{
				return row;
			}
		}else{
			//If no name is found either no module is found or parameter are already loaded
			//No modules for test case or parameters loaded for module
			return row;
		}

	}
	
	Row loadModuleParameters(TestModuleData mData ,Iterator<Row> rowIterator, Row row ){
		final int PARAM_KEY_COLUMN = 2;
		final int PARAM_VALUE_COLUMN = 3;
		
		HashMap<String,String> params;
		
		Cell cell = row.getCell(PARAM_KEY_COLUMN);
		
		if(cell == null){
			if(rowIterator.hasNext()){
				return rowIterator.next();
			}
			else{
				// End of file
				return null;
			}
		}
		
		if(  cell.getStringCellValue() == null || cell.getStringCellValue().isEmpty()){
			// No parameter for the module. Move to next row & return
			if(rowIterator.hasNext()){
				return rowIterator.next();
			}
			else{
				// End of file
				return null;
			}
		}
		
		params = (HashMap<String,String>) mData.getContent();
		
		while(cell.getStringCellValue() != null || (!cell.getStringCellValue().isEmpty())){
			params.put(row.getCell(PARAM_KEY_COLUMN).getStringCellValue(), row.getCell(PARAM_VALUE_COLUMN).getStringCellValue());
			if(rowIterator.hasNext()){
				row = rowIterator.next();
				cell = row.getCell(PARAM_KEY_COLUMN);
				if(cell == null){
					break;
				}
			}
			else{
				break;
			}
		}
		
		return row;
	}

}
