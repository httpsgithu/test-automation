package org.lantern.testautomation.data;

public class BaseData {

	protected Object content;
	protected boolean result;
	protected String name;
	
	public void addToContent(Object item){
		
	}
	
	public void addToContentWithKey(Object key , Object value){
		
	}
	
	public Object getContent(){
		return content;
	}
	
	public void setResult(boolean result){
		this.result = result;
	}
	
	public String getName(){
		return name;
	}
}
