package org.lantern.testautomation.data;

import java.util.ArrayList;

public class TestScenarioData extends BaseData {

	public TestScenarioData(String name){
		this.name = name;
		content = new ArrayList<TestCaseData>();
	}
	
	public void addToContent(Object item){
		TestCaseData tcData = (TestCaseData) item;
		ArrayList<TestCaseData> tsContent = (ArrayList<TestCaseData>) content;
		
		tsContent.add(tcData);
		
	}
}
