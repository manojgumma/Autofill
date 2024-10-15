package com.enjaz.webautofill.controller;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.enjaz.webautofill.driverfactory.WebDriverBase;
import com.enjaz.webautofill.driverfactory.WebDriverFactory;
import com.enjaz.webautofill.driverfactory.WebDriverType.WebDriverBrowseType;
import com.enjaz.webautofill.service.FillEnjazDetails;
import com.enjaz.webautofill.service.ReadMofaDetails;
import com.enjaz.webautofill.service.SettingsDetails;
import com.enjaz.webautofill.util.PropertyReader;
import com.enjaz.webautofill.valueobject.PreapprovalVO;
import com.enjaz.webautofill.valueobject.ResponseVO;
import com.enjaz.webautofill.valueobject.SettingsVO;
import com.google.gson.Gson;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 16-04-2015
 * 
 * @author Ramesh
 * 
 */


@Controller
public class AutofillController {
	//public String context = ServletContext.getInitParameter("name");
	private static final Logger logger = LoggerFactory
			.getLogger(AutofillController.class);
	static WebDriver driver;
	static WebDriver chromeDriver;
	static WebDriver mofaChromeDriver;

	@Autowired
	private PropertyReader propertyReader;
	@Autowired
	private ReadMofaDetails readMofaDetails;
	@Autowired
	private FillEnjazDetails fillEnjazDetails;
	@Autowired
	private SettingsDetails settingsDetails;

	private WebDriverBase driverBase = null;
//	EventFiringWebDriver eDriver = null;
	@Autowired
	ServletContext context; 
	String winHandleBefore =null;
	private String version = "";//context.getInitParameter("version");
	
	//HashMap< String ,PaymentDetails> paymentElements = new HashMap< String,PaymentDetails> ();
	
	//HashMap<String, Integer> applicantTab = new HashMap<String, Integer>();
	
	HashMap<String, String> applicantIdMap = new HashMap<String, String>();
	
	//HashMap<String, String> eNumberMap = new HashMap<String, String>();
	
	/**
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String initGet(Model model) {

		logger.info("Welcome init driver (GET) page..!");
		this.AutofillVersion();
		// logger.error("Error log check...");
		//payfees();
		return "init";
	}

	/**
	 * Initiate auto-fill
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws MalformedURLException
	 */
	public static boolean isDriverOpen(WebDriver driver) {
        try {
            Set<String> windowHandles = driver.getWindowHandles();
            return !windowHandles.isEmpty();
        } catch (Exception e) {
            return false;
        }
	}
//	@RequestMapping(value = "/init", method = RequestMethod.POST)
//	public String initPost(Model model, HttpServletRequest request,
//			HttpServletResponse response) throws MalformedURLException {
//		try {
//			long startTime,endTime;
//			this.AutofillVersion();
//			logger.info("Welcome to init (POST) page ...!");
//			/*String version=(String) request.getParameter("Version").toString();
//			logger.error("Version-"+version);*/
//			//String version = 
//			logger.error("AutoFill-Version-->"+version);
//			//logger.info("AutoFill-Version-->"+version);
//			
//			if (null == driver || !isDriverOpen(driver)) {
//
//				logger.debug("chrome driver going to start ...!");
//				startTime = System.currentTimeMillis();
//				driverBase = WebDriverFactory
//						.getWebDriver(WebDriverBrowseType.FIREFOX);
//
//				driver = driverBase.getWebDriver();
//				driver.manage().window().maximize();
//				//driver.manage().timeouts().implicitlyWait(10, TimeUnit.MINUTES);
//				endTime = System.currentTimeMillis();
//				logger.info("Time taken to open firefox(in ms) : "+(endTime-startTime));
//				//eDriver = new EventFiringWebDriver(driver);
//			//	OverrideClass oc = new OverrideClass();
//				//eDriver.register(oc);
//				startTime = System.currentTimeMillis();
//				driver.get(settingsDetails.readConfig().getOpsys_url());
//				endTime = System.currentTimeMillis();
//				logger.info("Time taken to load Opsys URL in  firefox(in ms) : "+(endTime-startTime));
//				
//				logger.debug("Firefox driver started on this URL: "
//						+ settingsDetails.readConfig().getOpsys_url());
//				//for handling the alert box
//				//this.handleAlertBox();
//				
//				 //Edit Autofill plugin files to update the Enjaz URL 
//				//this.changeURLinPlugin(new File(".").getCanonicalPath() + "\\chromedriver_win32\\autofill\\manifest.json", settingsDetails.readConfig());
//				//this.changeURLinPlugin(new File(".").getCanonicalPath() + "\\chromedriver_win32\\autofill\\eventPage.js", settingsDetails.readConfig());
//			}
//			
//			/*
//			//for new payment mode testing purpose starts here
//			this.changeURLinPlugin(new File(".").getCanonicalPath() + "\\chromedriver_win32\\autofill\\manifest.json", settingsDetails.readConfig());
//			this.changeURLinPlugin(new File(".").getCanonicalPath() + "\\chromedriver_win32\\autofill\\eventPage.js", settingsDetails.readConfig());
//			
//			//for chrome driver
//			driverBase = WebDriverFactory
//					.getWebDriver(WebDriverBrowseType.CHROME);
//			try {
//				if (!driverBase.isServiceRunning()) {
//					driverBase.startService();
//				}
//
//			} catch (IOException e) {
//				logger.error("Start Service: ", e);
//				e.printStackTrace();
//			}
//			
//			if(null == chromeDriver){
//				
//				chromeDriver = driverBase.getWebDriver();
//				chromeDriver.manage().window().maximize();
//				endTime = System.currentTimeMillis();
//				
//				startTime = System.currentTimeMillis();
//				chromeDriver.get("https://enjazit.com.sa/Enjaz/Main");
//				endTime = System.currentTimeMillis();
//				
//			}
//			
//			SettingsVO settingVO = new SettingsVO();
//			settingVO.setEnj_user_name("edxbdxb");
//			settingVO.setEnj_password("Vfs2017R!");
//			
//			fillEnjazDetails.fillVisaDetails(chromeDriver, settingVO);
//			fillEnjazDetails.printFunctionlaity(chromeDriver, "English");
//			
//			fillEnjazDetails.SinglePaymentAfterPaymentPost(chromeDriver, "123", "");
//			
//			
//			readMofaDetails.mofaPageLangSwitch(chromeDriver);
//			SettingsVO settingVO = new SettingsVO();
//			settingVO.setLetterType("VisaNumber");
//			settingVO.setLetterNo("7003295990");
//			settingVO.setSponser_id("2358239321");
//			
//			readMofaDetails.subValidateInvitation(chromeDriver,settingVO);
//			
//			readMofaDetails.readInvitationDetails(driver,chromeDriver, chromeDriver.getWindowHandle());*/
//			/*List<PreapprovalVO> preApproval =  readMofaDetails.readPreApprovalDetails(driver, chromeDriver, chromeDriver.getWindowHandle());
//			readMofaDetails.partialApprovalDetails(chromeDriver);*/
//			
//			//for firefox driver
//			/*driverBase = WebDriverFactory
//					.getWebDriver(WebDriverBrowseType.FIREFOX);
//			try {
//				if (!driverBase.isServiceRunning()) {
//					driverBase.startService();
//				}
//
//			} catch (IOException e) {
//				logger.error("Start Service: ", e);
//				e.printStackTrace();
//			}
//			
//			if(null == driver){
//				
//				driver = driverBase.getWebDriver();
//				driver.manage().window().maximize();
//				endTime = System.currentTimeMillis();
//				
//				startTime = System.currentTimeMillis();
//				driver.get("http://10.101.4.25:8080/VFS_Etimad/opsys/auth/login");
//				endTime = System.currentTimeMillis();
//				
//			}*/
//			
//			
//			//for new payment mode testing purpose ends here
//		} catch (Exception ex) {
//			logger.error("Init Post on chrome driver ", ex);
//		}
//
//		return "init";
//	}

	@RequestMapping(value = "/init", method = RequestMethod.POST)
	public ResponseEntity<String> initPost(Model model, HttpServletRequest request,
	        HttpServletResponse response) throws MalformedURLException {
	    try {
	        long startTime, endTime;
	        logger.info("Welcome to init (POST) page ...!");
	        if (null == driver || !isDriverOpen(driver)) {
	            logger.debug("chrome driver going to start ...!");
	            startTime = System.currentTimeMillis();
//	            driverBase = WebDriverFactory.getWebDriver(WebDriverBrowseType.FIREFOX);
	            driverBase = WebDriverFactory.getWebDriver(WebDriverBrowseType.CHROMEFOROPSYS);
	            driver = driverBase.getWebDriver(settingsDetails.readConfig().getLaunchOpsys());
	            driver.manage().window().maximize();
	            endTime = System.currentTimeMillis();
	            logger.info("Time taken to open firefox(in ms) : " + (endTime - startTime));
	            startTime = System.currentTimeMillis();
	            driver.get(settingsDetails.readConfig().getOpsys_url());
	            endTime = System.currentTimeMillis();
	            logger.info("Time taken to load Opsys URL in firefox(in ms) : " + (endTime - startTime));

	            logger.debug("Firefox driver started on this URL: "
	                    + settingsDetails.readConfig().getOpsys_url());
		        return new ResponseEntity<>("Opsys launched successfully", HttpStatus.OK);

	        } else if (isDriverOpen(driver)) {
	        	System.out.println("OpSys has already been launched. No further action is required.");
	        	driver.get(settingsDetails.readConfig().getOpsys_url());
		        return new ResponseEntity<>("OpSys has already been launched. No further action is required.", HttpStatus.ALREADY_REPORTED);
	        } else {
	        	return new ResponseEntity<>("Error in launching Opsys", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } catch (Exception ex) {
	        logger.error("Init Post on chrome driver ", ex);
	        return new ResponseEntity<>("Error in launching Opsys", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
//	@RequestMapping(value = "/init/maximize", method = RequestMethod.POST)
//		public String initMaximizePost(){
//		driver.manage().window().maximize();
//        return "Window maximized";
//		}
	
	
	/**
	 *  To handle Alert box
	 *  if alert box is present in firefox, it will accept & proceed further. 
	 */
	@Async
	private void handleAlertBox(){
		boolean flag = false;
		while(!flag){
			try{
				Thread.sleep(100);
				if(null!=driver){
					Alert alert = driver.switchTo().alert();
					alert.accept();
				}else
					flag = true;
			}catch(Exception e){
				//throws exception if alert box is not present
			}
		}
	}
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String viewSettings(Model model,
			@ModelAttribute("Success") String successMsg) {
		try {
			logger.info(" GET Settings");
			if (!successMsg.equals("")) {

				model.addAttribute("Success", successMsg);
				model.addAttribute("msgClass", "green");
			}
			SettingsVO settingVO = settingsDetails.readConfig();
			model.addAttribute("settings", settingVO);
		} catch (Exception e) {
			logger.error("View Settings: ", e);
		}
		return "settings";
	}

	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public String settingsDetails(@ModelAttribute SettingsVO settingVO,
			Model model, RedirectAttributes redirectAttributes) {
		logger.info("Welcome to settings updation");
		try {
			settingsDetails.createConfig(settingVO);
			/* Edit Autofill plugin files to update the Enjaz URL */
			//this.changeURLinPlugin(new File(".").getCanonicalPath() + "\\chromedriver_win32\\autofill\\manifest.json", settingVO);
			//this.changeURLinPlugin(new File(".").getCanonicalPath() + "\\chromedriver_win32\\autofill\\eventPage.js", settingVO);
		} catch (Exception e) {
			logger.error("SetttngsDetails: ", e);
			model.addAttribute("Success", "Failed to update");
			model.addAttribute("msgClass", "red");
			return "settings";
		}
		redirectAttributes.addFlashAttribute("Success",
				"Settings updated successfully");
		return "redirect:/settings";
	}
	/**
	 * 
	 * @param settingVO
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/openmofa", method = RequestMethod.POST)
	public @ResponseBody List<PreapprovalVO> openMofaForm(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody SettingsVO settingVO) throws InterruptedException {
		//String parentWindowHandle = "";
		logger.info("Welcome Reading Mofa details ");
		long startTime,endTime;
		SettingsVO settingConfig = settingsDetails.readConfig();
		if (null != settingConfig.getMofaURL()) {
			settingVO.setMofaURL(settingConfig.getMofaURL());
		} else {
			settingVO.setMofaURL(propertyReader.getValidate_url());
		}

		List<PreapprovalVO> preApproval = null;
		WebDriver mofaDriver = null;
		try {
			startTime = System.currentTimeMillis();
			if (mofaChromeDriver == null) {
				mofaChromeDriver = getChromeDriver();
				endTime = System.currentTimeMillis();
				logger.info("Time taken to open chrome browser (in ms) "+(endTime-startTime));
			}
			try {
				 mofaChromeDriver.getTitle();
			} catch (Exception e) {
				mofaChromeDriver = getChromeDriver();
				endTime = System.currentTimeMillis();
				logger.info("Time taken to open chrome browser (in ms) "+(endTime-startTime));
			}
			
			if (mofaChromeDriver != null) {
				logger.info("Initiate on this URL: "
						+ settingConfig.getMofaURL());
				startTime = System.currentTimeMillis();
				mofaChromeDriver.manage().window().maximize();
				mofaChromeDriver.manage().timeouts()
						.implicitlyWait(Duration.ofMinutes(10));
				// JavascriptExecutor jse = (JavascriptExecutor) driver;

				// jse.executeScript("function createDoc(){var w = window.open(''); }; createDoc();");
				mofaChromeDriver.findElement(By.cssSelector("body")).sendKeys(
						Keys.CONTROL + "t");
				ArrayList<String> tabs = new ArrayList<String>(
						mofaChromeDriver.getWindowHandles());
				int tabCount = tabs.size() - 1;

				if (tabCount < 0) {
					tabCount = 0;
				} else if (tabCount == 0) {
					tabCount = 1;
				}

				mofaChromeDriver.switchTo().window(tabs.get(tabCount));
				endTime = System.currentTimeMillis();
				logger.info("Time taken to open tab in chrome browser (in ms) "+(endTime-startTime));
				startTime = System.currentTimeMillis();
				mofaChromeDriver.get(propertyReader.getValidate_url());
				endTime = System.currentTimeMillis();
				logger.info("Time taken to load the URL "+propertyReader.getValidate_url()+" (in ms) "+(endTime-startTime));
				readMofaDetails.validateInvitationfrmOpenMofa(mofaChromeDriver,
						settingVO);

			}
		} catch (Exception e) {
			try {
				startTime = System.currentTimeMillis();
				mofaDriver = getChromeDriver();
				endTime = System.currentTimeMillis();
				logger.info("Time taken to open chrome browser (in ms) "+(endTime-startTime));
				mofaDriver.manage().window().maximize();
				// mofaDriver.manage().timeouts().implicitlyWait(10,
				// TimeUnit.MINUTES);
				readMofaDetails.validateInvitationfrmOpenMofa(mofaDriver,
						settingVO);
			} catch (Exception e1) {
				logger.error("Openmofa >>", e1);

				throw new InterruptedException("Unable to Open mofa");
			}

		}

		return preApproval;
	}

	/**
	 * 
	 * @param settingVO
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/verifyletter", method = RequestMethod.POST)
	public @ResponseBody String verifyLetter(HttpServletRequest request,
			HttpServletResponse response, @RequestBody SettingsVO settingVO)
			throws InterruptedException {
		String status = "";
		WebDriver mofaDriver = null;
		long startTime,endTime;
		SettingsVO settingConfig = settingsDetails.readConfig();
		if (null != settingConfig.getMofaURL()
				&& !settingConfig.getMofaURL().equals("")) {
			settingVO.setMofaURL(settingConfig.getMofaURL());
		} else {
			settingVO.setMofaURL(propertyReader.getValidate_url());
		}

		try {
			if (mofaDriver == null) {
				startTime = System.currentTimeMillis();
				mofaDriver = getChromeDriver();
				mofaDriver.manage().window().maximize();
				endTime = System.currentTimeMillis();
				logger.info("verifyLetter: Time taken to open chrome browser (in ms) "+(endTime-startTime));
				
			}
		} catch (Exception e) {

		}

		if (null != mofaDriver) {
			try {

				
				if (settingVO.getLetterType().equalsIgnoreCase(propertyReader.getSearchType())) {
					readMofaDetails.validateInvitation(mofaDriver, settingVO);
					status = readMofaDetails.validateInvitationMofaLetters(mofaDriver);
				} else {
					//status = readMofaDetails.validatePreapprovalMofaLetters(mofaDriver);
					status = verifyPreapprovalMofaLetters(mofaDriver, settingVO);
				}
				
				preApprovalStatus="";
				mofaDriver.quit();
				logger.info("verifyletter into Opsys Page, completed");
			} catch (Exception e) {
				logger.error("verifyLetter >>", e);

			}
		}
		return status;
	}

	private static int validateAttempt = 0;
	
	private String preApprovalStatus="";
	
	private String verifyPreapprovalMofaLetters(WebDriver mofaDriver, SettingsVO settingVO) throws InterruptedException,NoSuchElementException{
		
		String result = "";
		
		
		logger.info("Inside validateInvitation Page " + new Gson().toJson(settingVO));
	
		System.out.println("Inside validateInvitation Page....");
		long startTime,endTime;
		try {
							
			logger.info("Initiate on this URL: "+ settingVO.getMofaURL());
			 
			startTime = System.currentTimeMillis();
			mofaDriver.get(settingVO.getMofaURL());
			mofaDriver.manage().window().maximize();
			endTime = System.currentTimeMillis();
			
			logger.info("loading is completed on this URL: "+ settingVO.getMofaURL() +" & Time taken to load URL(in ms) : "+(endTime - startTime));
			
			startTime = System.currentTimeMillis();
//			readMofaDetails.mofaPageLangSwitch(mofaDriver);
			endTime = System.currentTimeMillis();
//			WebDriverWait wait = new WebDriverWait(driver, 10);
//	        WebElement closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/div/app-survey/div/div/div/div[1]/div/img")));
//			closeButton.click();
			try{
			if (settingVO.getMofaURL().equals("https://visa.mofa.gov.sa/")) {
				readMofaDetails.ifOldWebsite(mofaDriver, settingVO);
			}else {
			  WebElement TrackApplicationButton = mofaDriver.findElement(By.xpath("/html/body/app-root/div/app-header/div/div/div/div[2]/button[1]"));
			  TrackApplicationButton.click();
			  Thread.sleep(2000);
			
				WebElement letter = mofaDriver.findElement(By.cssSelector("[formControlName='applicationNumber']"));
				WebElement sponser = mofaDriver.findElement(By.cssSelector("[formControlName='passportNumber']"));
				letter.sendKeys(Keys.chord(Keys.CONTROL, ""), settingVO.getLetterNo()); //// check
				sponser.sendKeys(settingVO.getSponser_id());
//				WebElement trackSubmit = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
//						"/html/body/div[2]/div[2]/div/mat-dialog-container/app-application-tracker/div/form/div/button")));
//				trackSubmit.click();
				WebDriverWait wait = new WebDriverWait(mofaDriver, Duration.ofMinutes(2));
				WebElement view = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"mat-dialog-0\"]/app-application-tracker/div[3]/div[1]/button")));
				view.click();
				Set<String> windowHandles = mofaDriver.getWindowHandles();
				for (String handle : windowHandles) {
					mofaDriver.switchTo().window(handle);
				}
				final String previousURL = mofaDriver.getCurrentUrl();
				WebDriverWait driverWait = new WebDriverWait(mofaDriver, Duration.ofMinutes(2));
				  ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
			          public Boolean apply(WebDriver d) {
			            if( !d.getCurrentUrl().equalsIgnoreCase(previousURL)){
			            	return true;
			            }else{
			            	
			            	String searchResult = d.findElement(By.xpath("//*[@id='dlgMessageContent']")).getText();
			            	if(searchResult.contains("There is no result found.")){
			            		test("Disapproved");
			            		return true;
			            	}
			            	return false;
			            }
			          }
			    };

		    //   driverWait.until(e);
		      
		    
			
			if(preApprovalStatus.equals("")){
		    	   
		    	result = readMofaDetails.validatePreapprovalMofaLetters(mofaDriver);
		    }else{
		    	result = preApprovalStatus;
		    }
		    	   
		      
			}}catch(NoSuchElementException ne){
				if(validateAttempt <= 2)
				{
					verifyPreapprovalMofaLetters(mofaDriver,settingVO);
				}
				validateAttempt++;
				logger.error("validateInvitation >>", ne);
			}
		       
		return result;
		} catch (Exception e) {
		
		logger.error("validateInvitation Failed", e);
		throw new InterruptedException(e.getMessage());
		}
	}

	
	private void test (String status) {
		ResponseVO response = new ResponseVO();
		response.setStatus(status);
		preApprovalStatus=new Gson().toJson(response);
	}
	
	/**
	 * 
	 * @param settingVO
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/readmofa", method = RequestMethod.POST)
	public @ResponseBody List<PreapprovalVO> readFromMofa(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody SettingsVO settingVO) throws InterruptedException {
		String parentWindowHandle = "";
		
	//	logger.info("Origin "+request.getHeaders("Origin"));
		
		logger.info("Welcome Reading Mofa details ");
		System.gc();
		Runtime.getRuntime().freeMemory();
		List<PreapprovalVO> preApproval = null;
		SettingsVO settingConfig = settingsDetails.readConfig();
		String settingMofaURL = settingConfig.getMofaURL();
		if (null != settingMofaURL && !settingMofaURL.equals("")) {
			settingVO.setMofaURL(settingConfig.getMofaURL());
		} else {
			settingVO.setMofaURL(propertyReader.getValidate_url());
		}
		if (null != driver) {
			try {
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.MINUTES);
				long startTime,endTime;
				
				startTime = System.currentTimeMillis();
				if (null == mofaChromeDriver) {
					mofaChromeDriver = getChromeDriver();					
				}
				try {
					 mofaChromeDriver.getTitle();
				} catch (Exception e) {
					mofaChromeDriver = getChromeDriver();
				
				}
				parentWindowHandle = mofaChromeDriver.getWindowHandle();

				mofaChromeDriver.switchTo().window(parentWindowHandle);
				mofaChromeDriver.manage().window().maximize();
								
				mofaChromeDriver.manage().timeouts()
						.implicitlyWait(10, TimeUnit.MINUTES);
				endTime = System.currentTimeMillis();
				logger.error("Time taken to open chrome browser (in ms) "+(endTime-startTime));
				
				
				startTime = System.currentTimeMillis();
				readMofaDetails.validateInvitation(mofaChromeDriver, settingVO);
				endTime = System.currentTimeMillis();
				logger.info("Validate invitation "+(endTime-startTime));
				JavascriptExecutor javascript = (JavascriptExecutor) mofaChromeDriver;	
				String inProgressMessage = "var html='<div id=\"inProgressEle\" style=\"padding: 10px; width: 100%; color: red;text-align: center;\"> "+propertyReader.getReadmsg()+" </div>';$('body').prepend(html)";
				javascript.executeScript(inProgressMessage);
				
				startTime = System.currentTimeMillis();
				
				if (settingVO.getLetterType().equalsIgnoreCase(
						propertyReader.getSearchType())) {
					readMofaDetails.readInvitationDetails(driver,
							mofaChromeDriver, parentWindowHandle);
				} else {
					preApproval = readMofaDetails.readPreApprovalDetails(
							driver, mofaChromeDriver, parentWindowHandle);
					endTime = System.currentTimeMillis();
				}
				logger.info("Read MOFA "+(endTime-startTime));
				
				javascript.executeScript("document.getElementById(\"inProgressEle\").remove()");
				logger.info("Fill into Opsys Page, completed");
			} catch (Exception e) {
				logger.error("Unable to fill Mofa details", e);
				throw new InterruptedException("Unable to fill Mofa details");
			}
		}
		return preApproval;
	}

	@RequestMapping(value = "/readapproval", method = RequestMethod.POST)
	public @ResponseBody List<PreapprovalVO> readPreApproval(
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody SettingsVO settingVO) throws InterruptedException {

		logger.info("Welcome Reading approval details");
		List<PreapprovalVO> preApproval = null;
		SettingsVO settingConfig = settingsDetails.readConfig();
		String settingMofaURL = settingConfig.getMofaURL();

		if (null != settingMofaURL && !settingMofaURL.equals("")) {
			settingVO.setMofaURL(settingConfig.getMofaURL());
		} else {
			settingVO.setMofaURL(propertyReader.getValidate_url());
		}

		try {
			mofaChromeDriver = getChromeDriver();

			if (null != mofaChromeDriver) {

				readMofaDetails.validateInvitation(mofaChromeDriver, settingVO);
				preApproval = readMofaDetails
						.partialApprovalDetails(mofaChromeDriver);

				logger.info("Fill into Opsys Page, completed");
			} else {
				logger.error("Unable to find browser session");
				throw new InterruptedException("Unable to find browser session");
			}
		} catch (Exception e) {

			logger.error("Unable to fill Mofa details", e);
			throw new InterruptedException("Unable to fill Mofa details");
		}
		return preApproval;
	}

	/**
	 * 
	 * @param settingVO
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/exportenjaz", method = RequestMethod.POST)
	public void fillEnjazDetails(@RequestBody SettingsVO settingVO,
			HttpServletRequest request) throws Exception {
		logger.info("Welcome fillEnjazDetails POST home! ");
		String visaCompanyURL = "";
		String EnjazMainURL = "";
		String EnjazLoginURL = "";
		String parentWindowHandle = "";
		System.gc();
		// Get Chrome Driver for Enjaz
		SettingsVO _setting = settingsDetails.readConfig();
		
		if (null != settingVO.getEnjazPaymentPassword()
				&& !settingVO.getEnjazPaymentPassword().isEmpty()){
			settingVO.setEnzPayPassword(settingVO.getEnjazPaymentPassword());
			_setting.setServiceEnjazPaymentPassword(settingVO.getEnjazPaymentPassword());
		}
		else{
			settingVO.setEnzPayPassword(_setting.getEnzPayPassword());
			_setting.setServiceEnjazPaymentPassword("");
		}
		
		settingsDetails.createConfig(_setting);
		
		if (null != settingVO.getEnjazUserName()
				&& !settingVO.getEnjazUserName().equals(""))
			settingVO.setEnj_user_name(settingVO.getEnjazUserName());
		else
			settingVO.setEnj_user_name(_setting.getEnj_user_name());

		if (null != settingVO.getEnjazPassword()
				&& !settingVO.getEnjazPassword().equals(""))
			settingVO.setEnj_password(settingVO.getEnjazPassword());
		else
			settingVO.setEnj_password(_setting.getEnj_password());

		

		settingVO.setPrintLang(_setting.getPrintLang());
		if (null != _setting.getMofaURL() && !_setting.getMofaURL().equals("")) {
			settingVO.setMofaURL(_setting.getMofaURL());
		} else {
			settingVO.setMofaURL(propertyReader.getValidate_url());
		}
		if (null != _setting.getEnjazURL()
				&& !_setting.getEnjazURL().equals("")) {
			visaCompanyURL = _setting.getEnjazURL()+"SmartForm/TraditionalApp";
			EnjazMainURL = _setting.getEnjazURL() + "Enjaz/Main";
			EnjazLoginURL = _setting.getEnjazURL()+"Account/Login/enjazcompany";

			URI uri = new URI(visaCompanyURL);
		    visaCompanyURL = uri.normalize().toString();
			uri = new URI(EnjazMainURL);
			EnjazMainURL = uri.normalize().toString();
			uri = new URI(EnjazLoginURL);
			EnjazLoginURL = uri.normalize().toString();
			settingVO.setVisa_url(visaCompanyURL);
			settingVO.setMainPage(EnjazMainURL);
			settingVO.setLogin_url(EnjazLoginURL);
		} else {
			settingVO.setVisa_url(propertyReader.getVisa_url());
			settingVO.setMainPage(propertyReader.getMainPage());
			settingVO.setLogin_url(propertyReader.getLogin_url());
		}

		try {
			logger.info("Welcome fillEnjazDetails POST home! ");
			long startTime,endTime;
			startTime = System.currentTimeMillis();
			fillEnjazDetails.setFireboxDriver(driver);

			driverBase = WebDriverFactory
					.getWebDriver(WebDriverBrowseType.CHROME);
			try {
				if (!driverBase.isServiceRunning()) {
					driverBase.startService();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Start Service: ", e);
				e.printStackTrace();
			}

			endTime = System.currentTimeMillis();

			logger.info("Total elapsed time in execution of get the chromedriver is :"
					+ (endTime - startTime));

			// driver.manage().timeouts().implicitlyWait(10, TimeUnit.MINUTES);

			if (null == chromeDriver) {
				startTime = System.currentTimeMillis();
				chromeDriver = driverBase.getWebDriver(settingsDetails.readConfig().getLaunchOpsys());
				chromeDriver.manage().window().maximize();
				endTime = System.currentTimeMillis();
				logger.info("Total elapsed time to open chrome (in ms):"+ (endTime - startTime));
				
				startTime = System.currentTimeMillis();
//				chromeDriver.get(settingVO.getLogin_url());
				endTime = System.currentTimeMillis();
				logger.info("Time taken to load "+settingVO.getVisa_url()+" (in ms)"+ (endTime - startTime));
				fillEnjazDetails.fillVisaDetails(chromeDriver, settingVO);
				logger.info("Initiate on this URL : " + settingVO.getVisa_url());
			//	chromeDriver.get(settingVO.getVisa_url());
				logger.info("Successfully loaded on this URL : "
						+ settingVO.getVisa_url());
				// System.console().printf("call populateDetails");
				fillEnjazDetails.populateDetails(chromeDriver, settingVO,
						request);
			} else {
				try {
					startTime = System.currentTimeMillis();
					parentWindowHandle = chromeDriver.getWindowHandle();

					chromeDriver.switchTo().window(parentWindowHandle);
					JavascriptExecutor jse = (JavascriptExecutor) chromeDriver;

					jse.executeScript("function createDoc(){var w = window.open(''); }; createDoc();");
				/*	chromeDriver.findElement(By.cssSelector("body")).sendKeys(
							Keys.CONTROL + "t");*/

					ArrayList<String> tabs = new ArrayList<String>(
							chromeDriver.getWindowHandles());
					int tabCount = tabs.size() - 1;

					if (tabCount < 0) {
						tabCount = 0;
					} else if (tabCount == 0) {
						tabCount = 1;
					}
					// testing by an

					// chromeDriver.manage().deleteAllCookies();
					// end an
					chromeDriver.switchTo().window(tabs.get(tabCount));					
					endTime = System.currentTimeMillis();
					logger.info("Total elapsed time to open chrome tab (in ms):"+ (endTime - startTime));
					logger.info("Initiate on this URL : "
							+ settingVO.getVisa_url());
					startTime = System.currentTimeMillis();
					chromeDriver.get(settingVO.getVisa_url());
					endTime = System.currentTimeMillis();
					logger.info("Successfully loaded on this URL : "
							+ settingVO.getVisa_url() +"Time taken to load the url : "+(endTime - startTime));

					//fillEnjazDetails.fillVisaDetails(chromeDriver, settingVO);
					chromeDriver.get(settingVO.getVisa_url());
					fillEnjazDetails.populateDetails(chromeDriver, settingVO,
							request);

				} catch (Exception ex) {
					logger.error("Unable to open the new chrome tab", ex);
					startTime = System.currentTimeMillis();
					chromeDriver = driverBase.getWebDriver(settingsDetails.readConfig().getLaunchOpsys());
					chromeDriver.manage().window().maximize();
					endTime = System.currentTimeMillis();
					logger.info("Total elapsed time to open chrome (in ms):"+ (endTime - startTime));
					startTime = System.currentTimeMillis();
//					chromeDriver.get(settingVO.getMofaURL());
//					chromeDriver.get(settingVO.getLogin_url());
					endTime = System.currentTimeMillis();
					logger.info("Successfully loaded on this URL : "+ settingVO.getVisa_url() +"Time taken to load the url : "+(endTime - startTime));

					fillEnjazDetails.fillVisaDetails(chromeDriver, settingVO);
					chromeDriver.get(settingVO.getVisa_url());
					// System.console().printf("call populateDetails");
					fillEnjazDetails.populateDetails(chromeDriver, settingVO,
							request);
				}

			}
			
			//to store the applicant id in the map for the purpose of tab switch
			applicantIdMap.put(chromeDriver.getWindowHandle(), settingVO.getApplicant_id());
			
			logger.info("Fill into Enjaz Page, completed");
		} catch (Exception e) {

			logger.error("Unable to fill Enjaz details", e);
			throw new InterruptedException("Unable to fill Enjaz details");
		}

	}

	private void alertAccept(WebDriver driver, String parent) {
		try {
			// if (driver.getWindowHandles().size() == 2) {
			for (String window : driver.getWindowHandles()) {
				if (!window.equals(parent)) {
					driver.switchTo().window(window).close();
					System.out.println("Modal dialog found");
					break;
				}
			}
			// }

			Alert alert = driver.switchTo().alert();
			if (alert != null) {
				alert.accept();
				alert.dismiss();
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());

		}
	}

	private WebDriver getChromeDriver() {
		WebDriver driver = null;
		try {
			driverBase = WebDriverFactory
					.getWebDriver(WebDriverBrowseType.CHROME);
			try {
				if (!driverBase.isServiceRunning()) {
					driverBase.startService();
				}

			} catch (IOException e) {
				logger.error("Start Service: ", e);
				e.printStackTrace();
			}

			driver = driverBase.getWebDriver(settingsDetails.readConfig().getLaunchOpsys());

		} catch (Exception e) {
			logger.error("getChromeDriver >>", e.getMessage());

		}
		return driver;
	}

	private void AutofillVersion(){
		try{
			 //Read Version from version.txt file
			 String autofillPath = System.getenv("AutofillLogXMLPath");
			 File file = new File(autofillPath+"\\version.txt");
			 logger.debug("Version.txt file path : "+autofillPath+"\\version.txt");
			 if(file.exists() && !file.isDirectory()) { 
				 BufferedReader br = null;
				 try {
					 br = new BufferedReader(new FileReader(file));
				     StringBuilder sb = new StringBuilder();
				     String line = br.readLine();
				     while (line != null) {
				         sb.append(line);
				         line = br.readLine();
				     }
				     String versionInFile = sb.toString();
				     if(null!=versionInFile && !versionInFile.isEmpty())
//				    	 versionInFile=versionInFile.substring(0, versionInFile.lastIndexOf('.'));
				     //Compare the latest version
				     logger.debug("version in Version.txt file path : "+versionInFile);
				     this.version = versionInFile;
				     context.setAttribute("version", version);
				 } catch(Exception ex){
					 logger.info("Exception occured while get version info from version.txt file - "+ex);
				}finally {
					if(null!=br)
						br.close();
				 }
			 }
			}catch(Exception ex){
				logger.info("Exception in initGet() function - "+ex);
			}
		//	logger.error("Error log check...");
		//	payfees();
	}
	private void payfees() {
		// Pay visa fees click
		try {

			driverBase = WebDriverFactory
					.getWebDriver(WebDriverBrowseType.CHROME);
			try {
				if (!driverBase.isServiceRunning()) {
					driverBase.startService();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Start Service: ", e);
				e.printStackTrace();
			}
			chromeDriver = driverBase.getWebDriver(settingsDetails.readConfig().getLaunchOpsys());

			String PayFeesURL = "file:///C:/Users/rames_000/Desktop/Enjaz%20Information%20Technology-filpage.html";
			PayFeesURL = "https://www.google.co.in/";
			chromeDriver.get(PayFeesURL);
			chromeDriver.manage().window().maximize();
			String jscript ="document.onmousedown = function (e) { return false; }";
			JavascriptExecutor javascript = (JavascriptExecutor) chromeDriver;
			javascript.executeScript(jscript);
			
			 jscript ="document.onmousedown = null";
		
			javascript.executeScript(jscript);
			
		}catch(Exception e){
			logger.error("pay fees erro:", e);
			
		}

	}

//	public void initChrome() {
//		try {
//			// Optional, if not specified, WebDriver will search your path for
//			// chromedriver.
//			URL local = new URL("http://localhost:9515");
//			WebDriver driver = new RemoteWebDriver(local,
//					DesiredCapabilities.chrome());
//			// open the browser and go to JavaTutorial Network Website
//			driver.get("http://google.com");
//			// find the search button on the page
//			WebElement searchButton = driver.findElement(By
//					.className("search-submit"));
//			// create an action handler
//			Actions actions = new Actions(driver);
//			// use the action handler to move the cursor to given element
//			actions.moveToElement(searchButton).perform();
//			// wait until the search field is presented on the webpage and
//			// create an
//			// element
//			WebElement searchField = (new WebDriverWait(driver, 10))
//					.until(ExpectedConditions.presenceOfElementLocated(By
//							.name("s")));
//			// puts the text "java" into the search field
//			searchField.sendKeys("java");
//			// submit the search (submit the form)
//			searchField.submit();
//			// wait 5 seconds and close the browser
//			Thread.sleep(5000);
//	
//			driver.quit();
//		} catch (Exception ex) {
//			logger.error(ex.getMessage());
//
//		}
//	}

	@RequestMapping(value = "/enjazsave/{tabid}/{enumber}/{process}", method = RequestMethod.GET)
	public String enjazSave(Model model, @PathVariable int tabid, @PathVariable String enumber,	 @PathVariable String process, HttpServletRequest request) {
		try {
			ArrayList<String> tabs2 = new ArrayList<String>(
					chromeDriver.getWindowHandles());
			chromeDriver.switchTo().window(tabs2.get(tabid));
			// System.out.println("Window Handle id: "+chromeDriver.getWindowHandle());
			// System.out.println(request.getSession().getAttribute(driver.getWindowHandle()));
			//WebElement webElement = chromeDriver.findElement(By
				//	.className("success-msg"));
			//String applicationNumber = webElement.getText();
			String applicationNumber =	enumber;
			if (applicationNumber != null && !applicationNumber.trim().equals("")) {
				
				String filtered = applicationNumber.replaceAll("[^0-9,]", "");
				String[] numbers = filtered.split(",");
				applicationNumber = numbers[0];
				
				if(process.equalsIgnoreCase("yes")){//This flow is for visa type applicable with insurance.  Here just switch the window from chrome to firefox
					//applicationNumber = applicationNumber.substring(0, applicationNumber.length()-1);
					//to minimize the chrome window
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ALT);
					robot.keyPress(KeyEvent.VK_SPACE);
					robot.delay(700);
					robot.keyPress(KeyEvent.VK_N);

					robot.keyRelease(KeyEvent.VK_N);
					robot.keyRelease(KeyEvent.VK_SPACE);
					robot.keyRelease(KeyEvent.VK_ALT);
					// End time

					//to maximize the firefox window
			    	driver.manage().window().maximize();
			    	
					// to switch to the firefox window
					String windowHandle = driver.getWindowHandle();
					driver.switchTo().window(windowHandle);
					
					return "";
				}

				SettingsVO settingConfig = settingsDetails.readConfig();
				
				fillEnjazDetails.AfterSaveProcess(chromeDriver, applicationNumber, settingConfig);
			} else {
				logger.error("Application number is empty from enjaz save..");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return "";
	}
	
	/*@RequestMapping(value = "/enjazsaveapplication/{tabid}/{eNumber}", method = RequestMethod.GET)
	public String enjazSaveApplication(Model model, @PathVariable int tabid, @PathVariable String eNumber, HttpServletRequest request) {
		try {
			ArrayList<String> tabs2 = new ArrayList<String>(chromeDriver.getWindowHandles());
			chromeDriver.switchTo().window(tabs2.get(tabid));
			
			String eNo =	chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[1]/td[2]")).getText();
			String transNumber = chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[2]/td[2]")).getText();
			String transType = chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[3]/td[2]")).getText();
			String totalAmount = chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[6]/td[2]")).getText();
			String receiptNo =  chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[7]/td[2]")).getText();

			System.out.println("ENumber : "+eNo);
			System.out.println("Transaction Number : "+transNumber);
			System.out.println("Transaction Type: "+transType);
			System.out.println("Total Amount: "+totalAmount);
			System.out.println("Receipt Number: "+receiptNo);
			
			PaymentDetails payment = new PaymentDetails();
			payment.setApplicationNumber(eNo);
			payment.setTransactionNumber(transNumber);
			payment.setTransactionType(transType);
			payment.setTotalAmount(totalAmount);
			payment.setReceiptNumber(receiptNo);
			
			paymentElements.put(transType, payment);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return "";
	}*/
	
	
	/*@RequestMapping(value = "/enjazsavemedicalservice/{tabid}/{eNumber}", method = RequestMethod.GET)
	public String enjazSaveMedicalService(Model model, @PathVariable int tabid, @PathVariable String eNumber, HttpServletRequest request) {
		try {
			ArrayList<String> tabs2 = new ArrayList<String>(chromeDriver.getWindowHandles());
			chromeDriver.switchTo().window(tabs2.get(tabid));
			
			String eNo =	chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[1]/td[2]")).getText();
			String transNumber = chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[2]/td[2]")).getText();
			String transType = chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[3]/td[2]")).getText();
			String totalAmount = chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[5]/td[2]")).getText();
			String receiptNo =  chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[6]/td[2]")).getText();

			System.out.println("ENumber : "+eNo);
			System.out.println("Transaction Number : "+transNumber);
			System.out.println("Transaction Type: "+transType);
			System.out.println("Total Amount: "+totalAmount);
			System.out.println("Receipt Number: "+receiptNo);
			
			PaymentDetails payment = new PaymentDetails();
			payment.setApplicationNumber(eNo);
			payment.setTransactionNumber(transNumber);
			payment.setTransactionType(transType);
			payment.setTotalAmount(totalAmount);
			payment.setReceiptNumber(receiptNo);
			
			paymentElements.put(transType, payment);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return "";
	}*/
	
	/*@RequestMapping(value = "/enjazsavemedical/{tabid}/{eNumber}", method = RequestMethod.GET)
	public String enjazSaveMedical(Model model, @PathVariable int tabid, @PathVariable String eNumber, HttpServletRequest request) {
		try {
			ArrayList<String> tabs2 = new ArrayList<String>(chromeDriver.getWindowHandles());
			chromeDriver.switchTo().window(tabs2.get(tabid));
			
			String eNo =	chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[1]/td[2]")).getText();
			String transNumber = chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[2]/td[2]")).getText();
			String transType = chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[3]/td[2]")).getText();
			String totalAmount = chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[5]/td[2]")).getText();
			String receiptNo =  chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[6]/td[2]")).getText();

			System.out.println("ENumber : "+eNo);
			System.out.println("Transaction Number : "+transNumber);
			System.out.println("Transaction Type: "+transType);
			System.out.println("Total Amount: "+totalAmount);
			System.out.println("Receipt Number: "+receiptNo);
			
			PaymentDetails payment = new PaymentDetails();
			payment.setApplicationNumber(eNo);
			payment.setTransactionNumber(transNumber);
			payment.setTransactionType(transType);
			payment.setTotalAmount(totalAmount);
			payment.setReceiptNumber(receiptNo);
			
			paymentElements.put(transType, payment);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return "";
	}*/
	
	/*@RequestMapping(value = "/enjazsavenew/{tabid}/{eNumber}", method = RequestMethod.GET)
	public String enjazSavenew(Model model, @PathVariable int tabid, @PathVariable String eNumber, HttpServletRequest request) {
		try {
			ArrayList<String> tabs2 = new ArrayList<String>(chromeDriver.getWindowHandles());
			chromeDriver.switchTo().window(tabs2.get(tabid));
			System.out.println("e no : "+eNumber);
			
			String eNo =	chromeDriver.findElement(By.xpath("//table[2]/tbody/tr[1]/td[2]")).getText();
			String transNumber = chromeDriver.findElement(By.xpath("//table[2]/tbody/tr[2]/td[2]")).getText();
			String transType = chromeDriver.findElement(By.xpath("//table[2]/tbody/tr[3]/td[2]")).getText();
			String totalAmount = chromeDriver.findElement(By.xpath("//table[2]/tbody/tr[5]/td[2]")).getText();
			String receiptNo =  chromeDriver.findElement(By.xpath("//table[2]/tbody/tr[6]/td[2]")).getText();
			
			String passportNumber = chromeDriver.findElement(By.xpath("//table[1]/tbody/tr[4]/td[2]")).getText();
			
			System.out.println("ENumber : "+eNo);
			System.out.println("Transaction Number : "+transNumber);
			System.out.println("Transaction Type: "+transType);
			System.out.println("Total Amount: "+totalAmount);
			System.out.println("Receipt Number: "+receiptNo);
			
			PaymentDetails payment = new PaymentDetails();
			payment.setApplicationNumber(eNo);
			payment.setTransactionNumber(transNumber);
			payment.setTransactionType(transType);
			payment.setTotalAmount(totalAmount);
			payment.setReceiptNumber(receiptNo);
			
			paymentElements.put(transType, payment);
			
			PaymentDetails payDetails = paymentElements.get("Applications");
			
			System.out.println("Previous values==> "+payDetails.getApplicationNumber()+" : "+payDetails.getTransactionNumber()+" : "+payDetails.getTransactionType()+" : "+payDetails.getTotalAmount()+" : "+payDetails.getReceiptNumber());
			
			
			String applicationNumber =	eNo;
			if (applicationNumber != null && !applicationNumber.equals("")) {

				String filtered = applicationNumber.replaceAll("[^0-9,]", "");
				String[] numbers = filtered.split(",");
				applicationNumber = numbers[0];
				
				System.out.println("application Number is: "+applicationNumber);
				SettingsVO settingConfig = settingsDetails.readConfig();
				fillEnjazDetails.afterSaveProcessNewPayment(chromeDriver, settingConfig, applicationNumber, passportNumber, paymentElements);
			} else {
				logger.error("Application number is empty from enjaz save..");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return "";
	}*/
	
	
	//change the URL in Plugin code
    public void  changeURLinPlugin(String fileName,SettingsVO settingVO)
    {
    	logger.info("changeURLinPlugin() method"); 
 	    String line = null;
 	    StringBuffer output = new StringBuffer();
 	   FileReader fileReader=null;
 	  BufferedWriter out = null;
        try
        {
        	
        	String enjazURL = URLNormalize(settingVO.getEnjazURL());
        	 
            File file = new File(fileName);
            fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            while ((line = br.readLine()) != null)
            {
            	if (line.contains("matches")){
            		
                    //line =  "\"matches\": [\""+settingVO.getEnjazURL()+"Login\", \""+settingVO.getMofaURL()+"\",\""+settingVO.getEnjazURL()+"VisaCompany\",\""+enjazURL+"VisaPerson/ViewVisaFeesTransaction\",\""+enjazURL+"VisaPerson/HealthInsuranceServiceTransaction\",\""+enjazURL+"VisaPerson/ViewTransaction\",\""+enjazURL+"VisaPerson/HealthInsuranceTransaction\",\""+enjazURL+"VisaPerson/HealthInsuranceCompaniesList\"],";
            		line = "\"matches\":[\""+settingVO.getEnjazURL()+"Login\", \""+settingVO.getMofaURL()+"\",\""+settingVO.getEnjazURL()+"VisaCompany\",\""+settingVO.getEnjazURL()+"HealthInsurance\",\""+settingVO.getEnjazURL()+"HealthInsuranceCompaniesList\",\""+settingVO.getEnjazURL()+"SmartForm\\HealthInsurance\",\""+settingVO.getEnjazURL()+"SmartForm\\HealthInsuranceInfo\",\""+settingVO.getEnjazURL()+"SmartForm\\TraditionalApp\"],";
            	}else if(line.contains("/TraditionalApp") && line.contains("request.text=='blink'")){
            		
                	line=" if(request.text=='blink' && (sender.tab.url!='"+settingVO.getEnjazURL()+"VisaCompany') ){";
            	
            	}else if(line.contains("/TraditionalApp") && line.contains("textmsg!=null && textmsg!='blink'")){
            		
                	line ="}else if(textmsg!=null && textmsg!='blink' && sender.tab.url =='"+settingVO.getEnjazURL()+"VisaCompany'){";
            	
            	}else if(line.contains("/HealthInsuranceCompaniesList") && line.contains("textmsg!=null && textmsg!='blink'")){
            		
                	line ="}else if(textmsg!=null && textmsg!='blink' && sender.tab.url =='"+settingVO.getEnjazURL()+"HealthInsuranceCompaniesList'){";
            	
            	}else if(line.contains("HealthInsurance") && line.contains("textmsg!=null && textmsg!='blink'")){
            		
                	line ="}else if(textmsg!=null && textmsg!='blink' && sender.tab.url =='"+settingVO.getEnjazURL()+"HealthInsurance'){";
            	
            	}else if(line.contains("HealthInsuranceCompaniesList") && line.contains("request.company!=null && request.amount!=null")){
            		
            		line ="}else if(request.company!=null && request.amount!=null && sender.tab.url =='"+settingVO.getEnjazURL()+"HealthInsuranceCompaniesList'){";
            	}
            	
                output.append(line+"\n");
            }
            fileReader.close();
            FileWriter fw = new FileWriter(file);
            out = new BufferedWriter(fw);
            out.write(output.toString());
            out.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }finally{
        	//Close the reader and writer
        	try{
	        	if(fileReader!=null)
	        		fileReader.close();
	        	else if (out!=null)
	        		out.close();
	        } catch (Exception ex)
	        {
	            ex.printStackTrace();
	            logger.error(ex.getMessage());
	        }
        	
        }
    }
    
    
    //for remove Enjaz word from the url
    private String URLNormalize(String url) throws URISyntaxException{
		
		URI uri = new URI(url.replaceAll("Enjaz", ""));
		return uri.normalize().toString();
	}
    
    
  //To fill the application number or Enumber in opsys page
    @RequestMapping(value = "/fillapplicationno/{tabid}/{enumber}", method = RequestMethod.GET)
    public String fillApplicationNumber(Model model, @PathVariable int tabid, @PathVariable String enumber, HttpServletRequest request) {
    	System.out.println("enumber"+enumber);
       try {
    	   ArrayList<String> tabs = new ArrayList<String>(
					chromeDriver.getWindowHandles());
			chromeDriver.switchTo().window(tabs.get(tabid));
			
              if(chromeDriver !=null){ 
            	fillEnjazDetails.fillENumber(chromeDriver, enumber);  
              }
              
       } catch (Exception e) {
              logger.error("Error while filling E-number",e.getMessage());
       }

       return "";
    }
    
    //To populate the insurance company name and insurance fee in opsys page
    @RequestMapping(value = "/fillinsurancedetails/{tabid}/{insurance}/{amount}", method = RequestMethod.GET)
    public String fillInsuranceDetails(Model model, @PathVariable int tabid, @PathVariable String insurance, @PathVariable String amount, HttpServletRequest request) {
	   try {
		  
		   ArrayList<String> tabs = new ArrayList<String>(
					chromeDriver.getWindowHandles());
			chromeDriver.switchTo().window(tabs.get(tabid));
		   
			String fee = amount.replaceAll("point", ".");
			String exactAmount = fee.replaceAll("[\\s$]", "");
			System.out.println(tabid + " : " + insurance + " : " + exactAmount);

			if (driver != null && chromeDriver != null) {
				
				String applicantId = applicantIdMap.get(chromeDriver.getWindowHandle());
				fillEnjazDetails.populateInsuranceDetails(driver, insurance, exactAmount, applicantId);
	          }
	          
		   } catch (Exception e) {
	              logger.error("Error while filling medical insurance company name and fee details",e.getMessage());
	       }

       return "";
    }
    
    
    //To switch from opsys(firefox) window to chrome tab based on the applicant id
    @RequestMapping(value = "/payenjaz", method = RequestMethod.POST)
	public void payEnjazDetails(@RequestBody SettingsVO settingVO,	HttpServletRequest request) throws Exception {
    	
		String parentWindowHandle = "";
		
    	try {
			logger.info("Welcome Pay Enjaz!!! ");

			long startTime,endTime;
			startTime = System.currentTimeMillis();
			fillEnjazDetails.setFireboxDriver(driver);
			

			driverBase = WebDriverFactory.getWebDriver(WebDriverBrowseType.CHROME);
			try {
				if (!driverBase.isServiceRunning()) {
					driverBase.startService();
				}

			} catch (IOException e) {
				logger.error("Start Service: ", e);
				e.printStackTrace();
			}

			endTime = System.currentTimeMillis();

			logger.info("Total elapsed time in execution of get the chromedriver for pay enjaz is :"	+ (endTime - startTime));

			SettingsVO settingConfig = settingsDetails.readConfig();
			
			if(settingVO.getApplicant_id() !=null && settingVO.getE_number() !=null && settingVO.getPassport_no() !=null){
				
				settingConfig.setApplicant_id(settingVO.getApplicant_id());
				settingConfig.setE_number(settingVO.getE_number());
				settingConfig.setPassport_no(settingVO.getPassport_no());
				
			}
			
			if (null == chromeDriver) {
				
				startTime = System.currentTimeMillis();
				chromeDriver = driverBase.getWebDriver(settingsDetails.readConfig().getLaunchOpsys());
				chromeDriver.manage().window().maximize();
				endTime = System.currentTimeMillis();
				logger.info("Total elapsed time to open chrome for pay enjaz(in ms):"+ (endTime - startTime));
				
				startTime = System.currentTimeMillis();
				String payeeFeesURL="";
				payeeFeesURL=settingConfig.getEnjazURL()+propertyReader.getPayeeFeesURL();
				chromeDriver.get(payeeFeesURL);
				endTime = System.currentTimeMillis();
				logger.info("Time taken to load "+payeeFeesURL+" (in ms)"+ (endTime - startTime));
				
				fillEnjazDetails.fillVisaDetails(chromeDriver, settingConfig);
				logger.info("Initiate on this URL : " + payeeFeesURL);
				chromeDriver.get(payeeFeesURL);

				//For page language switch from Arabic to English
				//pageLanguageSwitch(chromeDriver);
				
				String currentUrl = chromeDriver.getCurrentUrl();
				
				if (!currentUrl.equalsIgnoreCase(payeeFeesURL)){
					chromeDriver.get(payeeFeesURL);
					currentUrl = chromeDriver.getCurrentUrl();
				}
				
				logger.info("Successfully loaded on this URL : "+payeeFeesURL);
				
				fillEnjazDetails.AfterSaveProcessForInsurance(chromeDriver, settingConfig);
			} else {
				try {
					startTime = System.currentTimeMillis();
					
					//to minimize firefox
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ALT);
					robot.keyPress(KeyEvent.VK_SPACE);
					robot.delay(700);
					robot.keyPress(KeyEvent.VK_N);

					robot.keyRelease(KeyEvent.VK_N);
					robot.keyRelease(KeyEvent.VK_SPACE);
					robot.keyRelease(KeyEvent.VK_ALT);
					// End time
					
					//switch to chrome window
					parentWindowHandle = chromeDriver.getWindowHandle();

					chromeDriver.switchTo().window(parentWindowHandle);
					JavascriptExecutor jse = (JavascriptExecutor) chromeDriver;

					jse.executeScript("function createDoc(){var w = window.open(''); }; createDoc();");
				/*	chromeDriver.findElement(By.cssSelector("body")).sendKeys(
							Keys.CONTROL + "t");*/

					ArrayList<String> tabs = new ArrayList<String>(chromeDriver.getWindowHandles());
					int tabCount = tabs.size() - 1;

					if (tabCount < 0) {
						tabCount = 0;
					} else if (tabCount == 0) {
						tabCount = 1;
					}

					chromeDriver.switchTo().window(tabs.get(tabCount));					
					endTime = System.currentTimeMillis();
					logger.info("Total elapsed time to open chrome tab (in ms):"+ (endTime - startTime));
					
					String payeeFeesURL="";
					payeeFeesURL=settingConfig.getEnjazURL()+propertyReader.getPayeeFeesURL();
					
					logger.info("Initiate on this URL : "+ payeeFeesURL);
					startTime = System.currentTimeMillis();
					
					chromeDriver.get(payeeFeesURL);
					endTime = System.currentTimeMillis();
					logger.info("Successfully loaded on this URL : "+ payeeFeesURL +"Time taken to load the url : "+(endTime - startTime));
					System.out.println("Initiate on this URL : "+ payeeFeesURL);
					//fillEnjazDetails.fillVisaDetails(chromeDriver, settingConfig);
					//For page language switch
					//readMofaDetails.pageLangSwitch(chromeDriver);
					String currentUrl = chromeDriver.getCurrentUrl();
					System.out.println(currentUrl);
					if (currentUrl.contains("https://identity.ksavisa.sa/")
							|| currentUrl.equalsIgnoreCase(settingConfig.getMofaURL())) {
						logger.info("Session expired....");
						fillEnjazDetails.fillVisaDetails(chromeDriver, settingConfig);
						chromeDriver.get(payeeFeesURL);
					}
					chromeDriver.get(payeeFeesURL);
					if (!currentUrl.equalsIgnoreCase(payeeFeesURL)){
						chromeDriver.get(payeeFeesURL);
						currentUrl = chromeDriver.getCurrentUrl();
					}
					
					fillEnjazDetails.AfterSaveProcessForInsurance(chromeDriver, settingConfig);

				} catch (Exception ex) {
					logger.error("Error while processing pay enjaz ",ex.getMessage());
					
					startTime = System.currentTimeMillis();
					chromeDriver = driverBase.getWebDriver(settingsDetails.readConfig().getLaunchOpsys());
					chromeDriver.manage().window().maximize();
					endTime = System.currentTimeMillis();
					logger.info("Total elapsed time to open chrome for pay enjaz(in ms):"+ (endTime - startTime));
					
					startTime = System.currentTimeMillis();
					String payeeFeesURL="";
					payeeFeesURL=settingConfig.getEnjazURL()+propertyReader.getPayeeFeesURL();
					chromeDriver.get(payeeFeesURL);
					endTime = System.currentTimeMillis();
					logger.info("Time taken to load "+payeeFeesURL+" (in ms)"+ (endTime - startTime));
					
					fillEnjazDetails.fillVisaDetails(chromeDriver, settingConfig);
					logger.info("Initiate on this URL : " + payeeFeesURL);
					chromeDriver.get(payeeFeesURL);

					//For page language switch from Arabic to English
					//pageLanguageSwitch(chromeDriver);
					
					String currentUrl = chromeDriver.getCurrentUrl();
					
					if (!currentUrl.equalsIgnoreCase(payeeFeesURL)){
						chromeDriver.get(payeeFeesURL);
						currentUrl = chromeDriver.getCurrentUrl();
					}
					
					logger.info("Successfully loaded on this URL : "+payeeFeesURL);
					
					fillEnjazDetails.AfterSaveProcessForInsurance(chromeDriver, settingConfig);
				
				}

			}
			
			logger.info("Pay Enjaz, completed");
		} catch (Exception e) {
			logger.error("Error in Pay Enjaz", e);
			throw new InterruptedException("Error in Pay Enjaz");
		}
    	
    }
    
  //For page language switch from Arabic to English
    private void pageLanguageSwitch(WebDriver driver){
    	
    	//For page language switch from Arabic to English
		//String jscript = "$('.langswitch').click(function(){ window.location.href = $('.langswitch').attr('href')}).trigger('click')";
    	
		if(driver.findElement(By.id("en-US")).isDisplayed()) {
			driver.findElement(By.id("en-US")).click();
			System.out.println("Language Changed as ENGB");
		}else {
			System.out.println("Language not Changed as it already ENGB");
		}
		
//    	String jscript = "var curLanguage = $('.language').html();if (curLanguage == 'English') {window.location = $('.language').attr('href')}";
//		JavascriptExecutor javascript = (JavascriptExecutor) driver;
//		javascript.executeScript(jscript);
    	
    }
    


}
