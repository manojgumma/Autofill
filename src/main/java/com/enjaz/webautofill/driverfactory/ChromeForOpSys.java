package com.enjaz.webautofill.driverfactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enjaz.webautofill.driverfactory.WebDriverType.WebDriverBrowseType;
import com.enjaz.webautofill.service.SettingsDetails;
import com.enjaz.webautofill.util.PropertyReader;
import com.enjaz.webautofill.valueobject.SettingsVO;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class ChromeForOpSys extends WebDriverBase {

	private static final Logger logger = LoggerFactory.getLogger(ChromeForOpSys.class);

	public ChromeForOpSys() {
		super(WebDriverBrowseType.CHROMEFOROPSYS);

	}
	
	@Autowired
	private PropertyReader propertyReader;
	
	@Override
	public WebDriver getWebDriver(String launchType) throws IOException {
		ChromeDriver chromeDriver;
		ChromeOptions options = new ChromeOptions();
		
		
		try {
//				logger.info("Opening chrome browser with local ChromeDriver ");
//			String driverPath = new File(".").getCanonicalPath() + "\\chromedriver_win32\\chromedriver.exe";
			
//			System.setProperty("webdriver.chrome.driver", driverPath);
			options.addArguments("disable-infobars");
			options.addArguments("--disable-notifications");
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
			options.addArguments("--disable-notifications");
			options.addArguments("ignore-certificate-errors");
			PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
			for (PrintService printer : printServices) {
				String printerName = printer.getName().replace(" ", "_");

				if (printerName.toUpperCase().contains("ZEBRA") || printerName.toUpperCase().contains("EPSON")
						|| printerName.toUpperCase().contains("BIXOLON")) {
					options.addArguments("print.margins=0"); // This is a simplified assumption
					options.addArguments("print.headerleft=''");
					options.addArguments("print.headerright=''");
					options.addArguments("print.footerleft=''");
					options.addArguments("print.footercenter=''");
					options.addArguments("print.footerright=''");

				}
			}
			
			if(launchType.equalsIgnoreCase("Auto-Download")) {
				chromeDriver = new ChromeDriver(options);
				return chromeDriver;
			}else {
				String driverPath = new File(".").getCanonicalPath() + "\\chromedriver_win32\\chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", driverPath);
				chromeDriver = new ChromeDriver(options);
				return chromeDriver;
			}
			
		} catch (Exception e) {

			e.printStackTrace();
//				logger.info("Opening chrome browser with Webdriver Manager ");
			if(launchType.equalsIgnoreCase("Auto-Download")) {
				chromeDriver = new ChromeDriver(options);
				return chromeDriver;
			}else {
				String driverPath = new File(".").getCanonicalPath() + "\\chromedriver_win32\\chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", driverPath);
				chromeDriver = new ChromeDriver(options);
				return chromeDriver;
			}

		}

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
	public boolean isServiceRunning() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
