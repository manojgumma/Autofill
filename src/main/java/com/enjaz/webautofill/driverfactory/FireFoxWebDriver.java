package com.enjaz.webautofill.driverfactory;

import java.io.File;
import java.io.IOException;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.enjaz.webautofill.driverfactory.WebDriverType.WebDriverBrowseType;

public class FireFoxWebDriver extends WebDriverBase{
	
	public FireFoxWebDriver() {
		super(WebDriverBrowseType.FIREFOX);
		
	}
	
	@Override
	public WebDriver getWebDriver(String launchType) throws IOException {
		// TODO Auto-generated method stub
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(
				CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR,
				UnexpectedAlertBehaviour.IGNORE);
		String driverPath = new File(".").getCanonicalPath()+ "\\chromedriver_win32\\geckodriver.exe";
		System.setProperty("webdriver.gecko.driver",  driverPath );
		
		FirefoxDriver fireboxdriver;
		
		try {
		FirefoxProfile profile = new FirefoxProfile();
		FirefoxOptions options = new FirefoxOptions();
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		for (PrintService printer : printServices) {
			String printerName=printer.getName().replace(" ","_");
			
			if(printerName.toUpperCase().contains("ZEBRA") || printerName.toUpperCase().contains("EPSON") || printerName.toUpperCase().contains("BIXOLON")) {
			profile.setPreference("print.printer_"+printerName+".print_margin_bottom", "0");
			profile.setPreference("print.printer_"+printerName+".print_margin_left","0");
			profile.setPreference("print.printer_"+printerName+".print_margin_right", "0");
			profile.setPreference("print.printer_"+printerName+".print_margin_top", "0");
			profile.setPreference("print.printer_"+printerName+".print_headercenter", "");
			profile.setPreference("print.printer_"+printerName+".print_headerleft", "");
			profile.setPreference("print.printer_"+printerName+".print_headerright", "");
			profile.setPreference("print.printer_"+printerName+".print_footercenter", "");
			profile.setPreference("print.printer_"+printerName+".print_footerleft", "");
			profile.setPreference("print.printer_"+printerName+".print_footerright", "");
			}
			
		}
		options.setProfile(profile);
		fireboxdriver = new FirefoxDriver(options);
		}catch(Exception e) {
			e.printStackTrace();
			fireboxdriver = new FirefoxDriver();
		}

	
		
				
		return fireboxdriver;
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
