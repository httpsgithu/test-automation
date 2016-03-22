package org.lantern.testautomation.datareader;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lantern.testautomation.data.TestCaseData;
import org.lantern.testautomation.data.TestModuleData;
import org.lantern.testautomation.data.TestScenarioData;
import org.lantern.testautomation.data.TestScenarioExecutionData;

public class ExcelReader {
	
	private TestScenarioData sData = null;
	private TestScenarioExecutionData xData = null;
	private ArrayList<String> testCaseNameList = null;

	
	public boolean loadDataFromFile(String filePath){
		
		sData = new TestScenarioData("Lantern Test Automation");
		xData = new TestScenarioExecutionData("Lantern Test Execution");
		
		 try
	        {
	            FileInputStream file = new FileInputStream(new File(filePath));
	 
	            //Create Workbook instance holding reference to .xlsx file
	            XSSFWorkbook workbook = new XSSFWorkbook(file);
	 
	            //Get first/desired sheet from the workbook
	            XSSFSheet sheet = workbook.getSheetAt(0);
	            
	            //First load the test cases from the 1st Sheet
	            xData = loadTestCasesToRun(sheet);


	            //Get second sheet from the workbook
	            sheet = workbook.getSheetAt(1);
	            sData = loadScenarioDefinitions(sheet);
	            
	            file.close();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
		return true;
	}
	
	TestScenarioExecutionData loadTestCasesToRun(XSSFSheet sheet ){
		
		int columnCounter;
		String testCaseName;
		
		testCaseNameList = new ArrayList<String>();
		
		TestScenarioExecutionData xData = new TestScenarioExecutionData("Lantern Test Execution");
		
        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        
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
                		xData.addToContent(cData);
                		
                		testCaseNameList.add(testCaseName);
                	}
                }
                columnCounter++;
            }
        }
		return xData;
	}
	public TestScenarioData loadScenarioDefinitions(XSSFSheet sheet){
		
		TestScenarioData currentScenario = null;
		TestCaseData currentTestCase = null;
		TestModuleData currentModule = null;
		
		 try
	        {
	 
	            //Iterate through each rows one by one
	            Iterator<Row> rowIterator = sheet.iterator();
	            
	            //Instantiate Scenario Array List
	            currentScenario = new TestScenarioData("Lantent Test Automation");
	            
	            //Ignore header
	            rowIterator.next();
	            
	            while (rowIterator.hasNext())
	            {
	                Row row = rowIterator.next();
	                //For each row, iterate through all the columns
	                Iterator<Cell> cellIterator = row.cellIterator();
	                 
	                // load TestCase
	                while (cellIterator.hasNext())
	                {
	                    Cell cell = cellIterator.next();

	                    switch(cell.getColumnIndex()){
		                    case 0:
		                    	currentTestCase = new TestCaseData(cell.getStringCellValue());
		                    	currentScenario.addToContent(currentTestCase);
		                    	break;
		                    case 1:
		                    	currentModule = new TestModuleData(cell.getStringCellValue());
		                    	currentTestCase.addToContent(currentModule);
		                    	break;
		                    case 2:
		                    	String parameterName = cell.getStringCellValue();
		                    	String parameterValue = cellIterator.next().getStringCellValue();
		                    	currentModule.addToContentWithKey(parameterName, parameterValue);
		                    	break;
		              
	                    }
	                }	                
	            }
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
		 return currentScenario;
	}

	public TestScenarioData getTestCaseList4Execution() {
		TestScenarioData sxData = new TestScenarioData("Test Execution");
		ArrayList<TestCaseData> tcDefinitions = (ArrayList<TestCaseData>) sData.getContent();
		
		for(int i=0;i<tcDefinitions.size();i++){
			TestCaseData tcData = tcDefinitions.get(i);
			if(testCaseNameList.contains(tcData.getName())){
				sxData.addToContent(tcData);
			}
		}
		return sxData;
	}
}
