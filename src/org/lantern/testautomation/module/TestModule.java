package org.lantern.testautomation.module;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.*;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class TestModule extends SeleneseTestNgHelper{
	
	//@SuppressWarnings("deprecation")
	public TestModule(){
		
	}
	//@SuppressWarnings("deprecation")
	/*public void setUp() throws Exception {
       // setUp("http://www.google.com/", "*firefox");
             // We instantiate and start the browser
      }*/
	public boolean runModule(HashMap<String,String> parameters){
		return true;
	}
}
