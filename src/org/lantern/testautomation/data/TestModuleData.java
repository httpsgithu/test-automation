package org.lantern.testautomation.data;

import java.util.HashMap;

public class TestModuleData extends BaseData {

	public TestModuleData(String name){
		this.name = name;
		content = new HashMap<String, String>();
	} 
	
	public void addToContentWithKey(Object key , Object value){
		HashMap<String, String> parameters  = (HashMap<String, String>) this.content;
		parameters.put((String)key, (String) value);
	}
}
