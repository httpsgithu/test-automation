package org.lantern.data;

import java.util.ArrayList;

public class TestCaseData extends BaseData {

	public TestCaseData(String name){
		this.name = name;
		content = new ArrayList<TestModuleData>();
	}
	
	public void addToContent(Object item){
		TestModuleData tmData = (TestModuleData) item;
		ArrayList<TestModuleData> tcContent = (ArrayList<TestModuleData>) content;
		
		tcContent.add(tmData);
		
	}
}
