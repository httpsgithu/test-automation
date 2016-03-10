package org.lantern.orchestration;

import java.util.ArrayList;

import org.lantern.data.TestCaseData;
import org.lantern.data.TestModuleData;

public class TestCaseManager {
	
	TestModuleManager moduleManager;
	
	public TestCaseManager(){
		moduleManager = new TestModuleManager();
	}

	public boolean runTestCases(ArrayList<TestCaseData> testCases){
		boolean sResult = true;
		boolean tcResult = true;
		
		for(int i = 0 ;i <testCases.size();i++){
			tcResult = runTestCase(testCases.get(i));
			
			if(!tcResult){
				sResult = false;
			}
		}
		
		return sResult;
	}
	
	boolean runTestCase(TestCaseData tcData ){
		
		boolean cResult = true;
		boolean mResult = true;
		
		ArrayList<TestModuleData> modules = (ArrayList<TestModuleData>)tcData.getContent();
		
		for(int i = 0 ;i <modules.size();i++){
			mResult = moduleManager.runModule(modules.get(i));
			
			if(!mResult){
				cResult = false;
			}
		}
		tcData.setResult(cResult);
		return cResult;
		
	}
}
