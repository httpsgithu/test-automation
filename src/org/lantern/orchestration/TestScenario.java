package org.lantern.orchestration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.lantern.data.TestCaseData;
import org.lantern.data.TestScenarioData;
import org.lantern.datareader.ExcelReader;
import org.lantern.datareader.FrameworkTestData;

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
		 
		 //sData = FrameworkTestData.prepareData();
		 sData = xlReader.loadDataFromFile("c:/Users/sun/desktop/Book1.xlsx");
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

	  }
}
