package com.enjaz.webautofill;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserTest {
	static WebDriver driver;
	/*@Test
	public void test() {
		System.out.println("Welcome");
		// fail("Not yet implemented");
		if(null == driver) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.MINUTES);
			System.out.println("Firefox driver started " + driver.toString());
			System.out.println("==>"+driver.getWindowHandle() +"===="+ driver.manage().toString());
		} else {
			System.out.println("===>> " + driver.toString());
			System.out.println("==>"+driver.getWindowHandle()+"===="+ driver.manage().toString());
		}
	}*/

}
