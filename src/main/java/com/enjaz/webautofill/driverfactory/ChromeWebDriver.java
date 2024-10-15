package com.enjaz.webautofill.driverfactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.enjaz.webautofill.driverfactory.WebDriverType.WebDriverBrowseType;

public class ChromeWebDriver extends WebDriverBase {

	private static ChromeDriverService service;

	public ChromeWebDriver() {
		super(WebDriverBrowseType.CHROME);
	}

	public void startService() throws IOException {
//		String driverPath = new File(".").getCanonicalPath() + "\\chromedriver_win32\\chromedriver.exe";
//		System.setProperty("webdriver.chrome.driver", driverPath);
		
		//ChromeOptions option = new ChromeOptions();
		//option.addExtensions(new File("C:\\Users\\DDR\\Desktop\\autofill.crx"));
		
		service = new ChromeDriverService.Builder()
//				.usingDriverExecutable(new File(driverPath))
				// .usingDriverExecutable(new
				// File("C:\\Ramesh\\Softwares\\Autofill\\chromedriver_win32\\chromedriver.exe"))
				.usingAnyFreePort().build();
		service.start();

	}

	public void stopService() {
		service.stop();
	}

	@Override
	public WebDriver getWebDriver(String launchType) {

		// URL local = new URL("http://localhost:9515");
		//return new ChromeDriver();
		ChromeOptions options = new ChromeOptions();
		ChromeDriver  ChromeDriver;
		try {
			
			//SettingsVO settingVO = settingsDetails.readConfig();
			
			String autofillPath = new File(".").getCanonicalPath() + "\\chromedriver_win32\\autofill";
			options.addArguments("load-extension="+autofillPath);
			options.addArguments("disable-infobars");
			options.addArguments("--disable-notifications");
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
	        
			if(launchType.equalsIgnoreCase("Auto-Download")) {
				ChromeDriver = new ChromeDriver(options);
				return ChromeDriver;
			}else {
				String driverPath = new File(".").getCanonicalPath() + "\\chromedriver_win32\\chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", driverPath);
				ChromeDriver = new ChromeDriver(options);
				return ChromeDriver;
			}
			//options.addExtensions(new File(new File(".").getCanonicalPath() + "\\chromedriver_win32\\autofill.crx"));
		} catch (Exception e) {
			e.printStackTrace();
			 
			if(launchType.equalsIgnoreCase("Auto-Download")) {
				ChromeDriver = new ChromeDriver(options);
				return ChromeDriver;
			}else {
				String driverPath = null;
				try {
					driverPath = new File(".").getCanonicalPath() + "\\chromedriver_win32\\chromedriver.exe";
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.setProperty("webdriver.chrome.driver", driverPath);
				ChromeDriver = new ChromeDriver(options);
				return ChromeDriver;
			}
		}
//		options.addArguments("--disable-web-security");
//		options.addArguments("--allow-running-insecure-content");
//		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		
//		return new RemoteWebDriver(service.getUrl(),
//				capabilities);
//return new RemoteWebDriver(capabilities);
//		return new RemoteWebDriver(service.getUrl(),
//			capabilities);
		
		
	}

	@Override
	public boolean isServiceRunning() {
		if (service != null) {
			return service.isRunning();
		}
		return false;
	}

}
