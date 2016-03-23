package org.lantern.testautomation.orchestration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.lantern.testautomation.data.TestCaseData;
import org.lantern.testautomation.data.TestScenarioData;
import org.lantern.testautomation.data.TestScenarioExecutionData;
import org.lantern.testautomation.datareader.ExcelReader;
import org.lantern.testautomation.datareader.FrameworkTestData;
import org.lantern.testautomation.result.TestResults;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TestScenario {
	
	TestScenarioData sData;
	TestCaseManager testCaseManager;
	
	ExcelReader xlReader;

	 @Before
	  public void setUp() throws Exception {
		  
		 testCaseManager = new TestCaseManager();
		 xlReader = new ExcelReader();
		 xlReader.loadDataFromFile("Book1.xlsx");
		 sData = xlReader.getTestCaseList4Execution();
	  }

	  @Test
	  public void testLantern() throws Exception {
		  
		  ArrayList<TestCaseData> testCases;
		  if(sData == null){
			  return;
		  }
		  
		  testCases = (ArrayList<TestCaseData>) sData.getContent();
		  boolean result = testCaseManager.runTestCases(testCases);
		  sData.setResult(result);
	  }

	  @After
	  public void tearDown() throws Exception {
		  TestResults.getInstance().writeResultsToExcel();
	  }
}
