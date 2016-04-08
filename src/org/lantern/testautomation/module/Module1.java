package org.lantern.testautomation.module;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.*;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import java.util.HashMap;

public class Module1 extends TestModule {
	@SuppressWarnings("deprecation")
	public boolean runModule(HashMap<String,String> parameters){
		//TODO : Add module code here
		
		WebDriver driver = new FirefoxDriver();
		selenium = new WebDriverBackedSelenium(driver, "http://www.slack.com/");
		//selenium.start();
		selenium.waitForPageToLoad("5000");
		selenium.open("/");
		selenium.click("link=Sign in");
		selenium.type("id=domain",parameters.get("domainName="));
		selenium.click("id=submit_team_domain");
		// open | /?gws_rd=ssl | 
		//selenium.waitForPageToLoad("5000");
		//selenium.close();
		//
		driver.close();
	    // type | id=lst-ib | guitar classes
		return true;
	}
}
