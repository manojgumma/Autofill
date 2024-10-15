package com.enjaz.webautofill.driverfactory;

import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import com.enjaz.webautofill.driverfactory.WebDriverType.WebDriverBrowseType;

public class IEWebDriver extends WebDriverBase{

	public IEWebDriver(WebDriverBrowseType browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void startService() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopService() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WebDriver getWebDriver(String launchType) throws MalformedURLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isServiceRunning() {
		// TODO Auto-generated method stub
		return false;
	}

}
