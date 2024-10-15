package com.enjaz.webautofill.driverfactory;


import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import com.enjaz.webautofill.driverfactory.WebDriverType.WebDriverBrowseType;

public abstract class WebDriverBase {
	
	public WebDriverBrowseType browser = null;
	
	public WebDriverBase(WebDriverBrowseType browser)
	{
		this.browser =  browser;
	}
	
	public WebDriverBrowseType getWebDriverType()
	{
	 return this.browser;
	}
	public void setWebDriverType(WebDriverBrowseType browser)
	{
		this.browser = browser;
	}
	
	public abstract void startService() throws IOException;
	public abstract void stopService() throws IOException;
	public abstract boolean isServiceRunning();
	public abstract WebDriver getWebDriver(String launchType) throws MalformedURLException, IOException;
	
}
