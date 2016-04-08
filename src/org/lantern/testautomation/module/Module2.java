package org.lantern.testautomation.module;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class Module2 extends TestModule {
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
		driver.close();
		return true;
	}
}
