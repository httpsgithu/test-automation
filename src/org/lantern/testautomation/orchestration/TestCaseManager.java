package org.lantern.testautomation.orchestration;

import java.util.ArrayList;

import org.lantern.testautomation.data.TestCaseData;
import org.lantern.testautomation.data.TestModuleData;
import org.lantern.testautomation.result.TestResults;

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
			TestModuleData module = modules.get(i);
			mResult = moduleManager.runModule(module);
			
			TestResults.getInstance().setResult(tcData.getName(), module.getName(), mResult);
			
			if(!mResult){
				cResult = false;
			}
		}
		tcData.setResult(cResult);
		return cResult;
		
	}
}
