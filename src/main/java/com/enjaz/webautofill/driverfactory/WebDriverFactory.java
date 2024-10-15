package com.enjaz.webautofill.driverfactory;

import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;

import com.enjaz.webautofill.driverfactory.WebDriverType.WebDriverBrowseType;

public class WebDriverFactory {

	
	public static WebDriverBase getWebDriver(WebDriverBrowseType webDriverType) throws MalformedURLException
	{
		WebDriverBase webDriverBase= null;
		switch(webDriverType)
		{
			case FIREFOX: 
				 webDriverBase = new FireFoxWebDriver();
				
				break;
			case IE:
				break;
			case CHROME:
				 webDriverBase = new ChromeWebDriver();
				
				break;
			case CHROMEFOROPSYS:
				webDriverBase = new ChromeForOpSys();
		}
		
		return webDriverBase;
	}

}
