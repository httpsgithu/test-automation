package org.lantern.testautomation.datareader;

import org.lantern.testautomation.data.TestCaseData;
import org.lantern.testautomation.data.TestModuleData;
import org.lantern.testautomation.data.TestScenarioData;

public class FrameworkTestData {

	public static TestScenarioData prepareData(){
		TestScenarioData tsData = new TestScenarioData("Lantern Testing");
		
		TestCaseData tcData1 = new TestCaseData("tc1");
		
		TestModuleData tmData1 = new TestModuleData("Module1");
		tmData1.addToContentWithKey("username", "siva");
		tmData1.addToContentWithKey("password", "sima");
		
		tcData1.addToContent(tmData1);
		
		TestModuleData tmData2 = new TestModuleData("Module2");
		tmData2.addToContentWithKey("username", "subra");
		tmData2.addToContentWithKey("password", "mani");
		tcData1.addToContent(tmData2);
		
		tsData.addToContent(tcData1);
		
		TestCaseData tcData2 = new TestCaseData("tc2");
		
		TestModuleData tmData3 = new TestModuleData("Module1");
		tmData1.addToContentWithKey("username", "siva");
		tmData1.addToContentWithKey("password", "sima");
		tcData2.addToContent(tmData3);
		
		tsData.addToContent(tcData2);
		
		return tsData;
	}
}
