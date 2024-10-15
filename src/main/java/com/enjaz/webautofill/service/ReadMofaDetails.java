package com.enjaz.webautofill.service;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjaz.webautofill.util.PropertyReader;
import com.enjaz.webautofill.util.Util;
import com.enjaz.webautofill.valueobject.PreapprovalVO;
import com.enjaz.webautofill.valueobject.ResponseVO;
import com.enjaz.webautofill.valueobject.SettingsVO;
import com.google.gson.Gson;

/**
 * 17-04-2015
 * @author Prabakaran
 *
 */
@Service
public class ReadMofaDetails {

	private static final Logger logger = LoggerFactory.getLogger(ReadMofaDetails.class);
	private static int validattempt = 0;
	@Autowired
	private PropertyReader propertyReader;
	@Autowired
	private SettingsDetails settingsDetails;
	/**
	 * Validate Invitation details
	 * @param driver
	 * @throws InterruptedException
	 */
	public WebDriver validateInvitationfrmOpenMofa(WebDriver driver, SettingsVO settingVO) throws InterruptedException {
		logger.info("Inside validateInvitation Page " + new Gson().toJson(settingVO));
		
		System.out.println("Inside validateInvitation Page....");
		try {

			logger.info("loading is completed on this URL: " + settingVO.getMofaURL());
			pageLangSwitch(driver);

//			WebElement closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
//					By.xpath("/html/body/app-root/div/app-survey/div/div/div/div[1]/div/img")));
//			closeButton.click();
			if(settingVO.getMofaURL().equals("https://visa.mofa.gov.sa/")){
				return ifOldWebsite(driver, settingVO);
				}else {
					
			WebElement TrackApplicationButton = driver.findElement(By.xpath("/html/body/app-root/div/app-header/div/div/div/div[2]/button[1]"));
			TrackApplicationButton.click();
			Thread.sleep(2000);
			WebElement letter = driver.findElement(By.cssSelector("[formControlName='applicationNumber']"));
			WebElement sponser = driver.findElement(By.cssSelector("[formControlName='passportNumber']"));
			letter.sendKeys(Keys.chord(Keys.CONTROL, ""), settingVO.getLetterNo()); //// check
			sponser.sendKeys(settingVO.getSponser_id());
//			WebElement trackSubmit = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
//					"/html/body/div[2]/div[2]/div/mat-dialog-container/app-application-tracker/div/form/div/button")));
//			trackSubmit.click();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
			WebElement view = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"mat-dialog-0\"]/app-application-tracker/div[3]/div[1]/button")));
			view.click();
			Set<String> windowHandles = driver.getWindowHandles();
			for (String handle : windowHandles) {
				driver.switchTo().window(handle);
			}
			final String previousURL = driver.getCurrentUrl();
			WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofMinutes(2));
			ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					if (!d.getCurrentUrl().equalsIgnoreCase(previousURL)) {
						return true;
					} else {
						return false;
					}
				}
			};

//			String submitBtnClickEvent = "$(document).ready(function(){$('input.go-btn.fleft').click(function(){ alert('ramesh'	});});";
//			
//			JavascriptExecutor javascript = (JavascriptExecutor) driver;
//		
//		
//			javascript.executeScript(submitBtnClickEvent);
			
//			WebElement submit = driver.findElement(By
//					.cssSelector("input.go-btn.fleft"));
//			JavascriptExecutor javascript = (JavascriptExecutor) driver;
//			if (submit != null) {
//				WebElement warning = driver.findElement(By
//						.xpath("//div/b[contains(@class,'RedError')]"));
//				if (warning != null) {
//					String submitDisable = "document.getElementsByClassName('go-btn fleft')[0].setAttribute('disabled', '');";
//					javascript.executeScript(submitDisable);
//					String warningMsg = "document.getElementsByClassName('RedError')[0].innerHTML = '<b class=\"WarningMsg\" style=\"color:blue\">Page will reload in 10 sec. Please enter captcha before</b>';";
//					javascript.executeScript(warningMsg);
//					try {
//						Thread.sleep(1000L);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					warningMsg = "document.getElementsByClassName('RedError')[0].innerHTML = '';";
//					javascript.executeScript(warningMsg);
//				}
//				javascript.executeScript("document.getElementsByClassName('go-btn fleft')[0].removeAttribute('disabled');");
			//	submit.click();
			
			
		
		        
			/*	boolean sumitValue = false;
				try{
				
					do{
						Thread.sleep(1000L);
						String currentUrl = driver.getCurrentUrl();
			            					
						if (currentUrl.equalsIgnoreCase(settingVO.getMofaURL())) {
							sumitValue = true;
						} else
						{
							sumitValue = false;
						}
				
					}while(sumitValue);
				}catch(Exception e){
					sumitValue = false;
					
					e.printStackTrace();
				}
				*/
		
				
				
				logger.info("Submit Validate page");
			
				String currentUrl = driver.getCurrentUrl();
	            System.out.print("CurrentUrl:" + currentUrl); 
	            System.out.print("Previous URL:" + settingVO.getMofaURL()); 
	            logger.info("CurrentUrl:" + currentUrl + "   Previous URL:" + settingVO.getMofaURL());
				
				if (currentUrl.equalsIgnoreCase(settingVO.getMofaURL())) {
					System.out.println("Redierct to validation page");
					validateInvitation(driver, settingVO);
				} 
			//}
			return driver;
		} }catch (Exception e) {
			
			logger.error("validateInvitationOpoenMofa Failed", e);
			throw new InterruptedException(e.getMessage());
		}
	}
	public WebDriver subValidateInvitation(WebDriver driver, SettingsVO settingVO) throws InterruptedException {
		String currentUrl ="";
		try{
		
		String searchValue = "2";
		
		/*
		 * if(settingVO.getLetterType().equalsIgnoreCase("VisaNumber")){
		 * searchValue="2"; }else
		 * if(settingVO.getLetterType().equalsIgnoreCase("InvitationLetter")){
		 * searchValue="4"; }
		 */
	    WebElement letter = driver.findElement(By.cssSelector("[formControlName='applicationNumber']"));
	    WebElement sponser = driver.findElement(By.cssSelector("[formControlName='passportNumber']"));
		letter.sendKeys(Keys.chord(Keys.CONTROL, ""), settingVO.getLetterNo());  ////check
		sponser.sendKeys( settingVO.getSponser_id());
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
//		WebElement trackSubmit = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[2]/div/mat-dialog-container/app-application-tracker/div/form/div/button")));
//		trackSubmit.click();
//		WebElement trackView = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"mat-dialog-0\"]/app-application-tracker/div[3]/div[1]/button")));
		WebElement view = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"mat-dialog-0\"]/app-application-tracker/div[3]/div[1]/button")));
		view.click();
		Set<String> windowHandles = driver.getWindowHandles();
		for (String handle : windowHandles) {
			driver.switchTo().window(handle);
		}
		final String previousURL = driver.getCurrentUrl();
		WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofMinutes(2));
		  ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
	          public Boolean apply(WebDriver d) {
	            if( !d.getCurrentUrl().equalsIgnoreCase(previousURL))
	            {
	            	return true;
	            }else
	            {
	            	return false;
	            }
	          }
	        };

	      //  driverWait.until(e);
	/*	boolean sumitValue = false;
		try{
		
			do{
				Thread.sleep(1000L);
				 currentUrl = driver.getCurrentUrl();
	            					
				if (currentUrl.equalsIgnoreCase(settingVO.getMofaURL())) {
					sumitValue = true;
				} else
				{
					sumitValue = false;
				}
		
			}while(sumitValue);
		}catch(Exception e){
			sumitValue = false;
			
			e.printStackTrace();
		} */

		logger.info("Submit Validate page");
	
		//currentUrl = driver.getCurrentUrl();
       
		}
		catch(NoSuchElementException ne)
		{
			if(validattempt <= 2)
			{
				subValidateInvitation(driver,settingVO);
			}
			validattempt++;
			logger.error("validateInvitation >>", ne);
		}
		catch (Exception e) {
					
			logger.error("validateInvitation Failed", e);
			throw new InterruptedException(e.getMessage());
		}
		validattempt = 0;
	return driver;
		
	}
	
	public WebDriver validateInvitation(WebDriver driver, SettingsVO settingVO) throws InterruptedException {
		logger.info("Inside validateInvitation Page " + new Gson().toJson(settingVO));
	
		System.out.println("Inside validateInvitation Page....");
		long startTime,endTime;
		try {
							
			logger.info("Initiate on this URL: "+ settingVO.getMofaURL());
			 
			startTime = System.currentTimeMillis();
			driver.get(settingVO.getMofaURL());
//			driver.manage().window().maximize();
			endTime = System.currentTimeMillis();
			
			logger.info("loading is completed on this URL: "+ settingVO.getMofaURL() +" & Time taken to load URL(in ms) : "+(endTime - startTime));
			
//			WebDriverWait wait = new WebDriverWait(driver, 10);
//	        WebElement closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/div/app-survey/div/div/div/div[1]/div/img")));
//			closeButton.click();
		//	WebElement TrackApplicationButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/div/app-header/div/div/div/div[2]/button[1]")));
//		    WebElement TrackApplicationButton = driver.findElement(By.xpath("/html/body/app-root/div/app-header/div/div/div/div[2]/button[1]"));
//			TrackApplicationButton.click();
//			Thread.sleep(2000);
//			startTime = System.currentTimeMillis();
//			endTime = System.currentTimeMillis();
//			logger.info("Time taken to switch the lang to English(in ms ) :"+(endTime - startTime));
//			return subValidateInvitation(driver, settingVO);
		if (settingVO.getMofaURL().equals("https://visa.mofa.gov.sa/")) {
			return ifOldWebsite(driver, settingVO);
		} else {
			WebElement TrackApplicationButton = driver
					.findElement(By.xpath("/html/body/app-root/div/app-header/div/div/div/div[2]/button[1]"));
			TrackApplicationButton.click();
			Thread.sleep(2000);
			startTime = System.currentTimeMillis();
			endTime = System.currentTimeMillis();
			logger.info("Time taken to switch the lang to English(in ms ) :" + (endTime - startTime));
			return subValidateInvitation(driver, settingVO);
		}
			
			} catch (Exception e) {
			
			logger.error("validateInvitation Failed", e);
			throw new InterruptedException(e.getMessage());
			}
	}
			
//			String submitBtnClickEvent = "$(document).ready(function(){$('input.go-btn.fleft').click(function(){ alert('ramesh'	});});";
//			
//			JavascriptExecutor javascript = (JavascriptExecutor) driver;
//		
//		
//			javascript.executeScript(submitBtnClickEvent);
			
//			WebElement submit = driver.findElement(By
//					.cssSelector("input.go-btn.fleft"));
//			JavascriptExecutor javascript = (JavascriptExecutor) driver;
//			if (submit != null) {
//				WebElement warning = driver.findElement(By
//						.xpath("//div/b[contains(@class,'RedError')]"));
//				if (warning != null) {
//					String submitDisable = "document.getElementsByClassName('go-btn fleft')[0].setAttribute('disabled', '');";
//					javascript.executeScript(submitDisable);
//					String warningMsg = "document.getElementsByClassName('RedError')[0].innerHTML = '<b class=\"WarningMsg\" style=\"color:blue\">Page will reload in 10 sec. Please enter captcha before</b>';";
//					javascript.executeScript(warningMsg);
//					try {
//						Thread.sleep(1000L);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					warningMsg = "document.getElementsByClassName('RedError')[0].innerHTML = '';";
//					javascript.executeScript(warningMsg);
//				}
//				javascript.executeScript("document.getElementsByClassName('go-btn fleft')[0].removeAttribute('disabled');");
			//	submit.click();
			
			
			//User has to be submit the button
			
		
	
	
	/**
	 * Read Details from Invitation letter
	 * @param driver
	 * @throws Exception
	 */
	public SettingsVO readInvitationDetails(WebDriver driver, WebDriver mofaDriver, String parentWindowHandle) throws Exception {
		long startTime = 0,endTime =0;
		
		System.out.println("Entered into the readInvitation");
		
		logger.info("Read Invitation details");
		//read start time
		startTime = System.currentTimeMillis();
		String lastNames = "";
		
		SettingsVO settingsVO = new SettingsVO();
		/*WebElement element = mofaDriver.findElement(By.cssSelector("table.formtable.FR"));
		settingsVO.setVisitName(element.findElement(By.xpath("//tbody/tr[6]/td[2]")).getText());
		settingsVO.setSponser_name(element.findElement(By.xpath("//tbody/tr[2]/td[2]")).getText());
		settingsVO.setSponser_address(element.findElement(By.xpath("//tbody/tr[4]/td[2]")).getText());
		settingsVO.setSponser_phone(element.findElement(By.xpath("//tbody/tr[2]/td[4]")).getText());
		settingsVO.setSponser_id(element.findElement(By.xpath("//tbody/tr[3]/td[2]")).getText());
		settingsVO.setOccupation(element.findElement(By.xpath("//tbody/tr[7]/td[2]")).getText());
		settingsVO.setPurpose(element.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText());*/
		
		//NoOfEntry(in MOFA website it is عدد مرات الدخول)
		                                                         //*[@id="content"]/div/div[2]/div/div/div[2]/div[1]/div[10]/div[1]/font/font
		
		if(mofaDriver.findElement(By.xpath("//*[@id='tblDocumentVisaList']/tbody/tr/td[9]")).isDisplayed()){
			settingsVO.setNoOfEntry(mofaDriver.findElement(By.xpath("//*[@id='tblDocumentVisaList']/tbody/tr/td[9]")).getText());
			System.out.println("#################"+settingsVO.getNoOfEntry());
		}
		
		
		
		
		
		//visitor name(in MOFA website it is اسم المطلوب للزيارة ) 
		                                                         //*[@id="content"]/div/div[2]/div/div/div[2]/div[1]/div[7]/div[1]/font/font
		
		if(mofaDriver.findElement(By.xpath("//*[@id='tblDocumentVisaList']/tbody/tr/td[4]")).isDisplayed()){
			settingsVO.setVisitName(mofaDriver.findElement(By.xpath("//*[@id='tblDocumentVisaList']/tbody/tr/td[4]")).getText());
			System.out.println("#################"+settingsVO.getVisitName());
			}
		
		
		//sponsor name(in MOFA website it is اسم الشركة او صاحب العمل)
		                                                            //*[@id="content"]/div/div[2]/div/div/div[2]/div[1]/div[3]/div[1]/font/font
		//settingsVO.setSponser_name(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div/div/div[2]/div[1]/div[3]/div[1]")).getText());
		
		
		
			
		
		try {
			mofaDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		if(mofaDriver.findElement(By.xpath("//*[contains(text(), 'الطلب صالح لتاريخ')]")).isDisplayed()) {
			System.out.println("Valid date is displayed");
			
			mofaDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(120));
			if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[3]/div[1]/div[3]/div/label")).isDisplayed()){
				settingsVO.setSponser_name(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[3]/div[1]/div[3]/div/label")).getText());
				System.out.println("#################"+settingsVO.getSponser_name());
					
				}
			if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[3]/div[1]/div[5]/div[1]/label")).isDisplayed()) {
				settingsVO.setSponser_address(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[3]/div[1]/div[5]/div[1]/label")).getText());
				System.out.println(settingsVO.getSponser_address());
			}
			if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[3]/div[1]/div[5]/div[2]/label")).isDisplayed()){
				settingsVO.setSponser_phone(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[3]/div[1]/div[5]/div[2]/label")).getText());
				System.out.println(settingsVO.getSponser_phone());
		}
			if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[3]/div[1]/div[4]/div/label")).isDisplayed()){
				settingsVO.setSponser_id(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[3]/div[1]/div[4]/div/label")).getText());
				System.out.println(settingsVO.getSponser_id());	
			}
			if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[3]/div[1]/div[3]/div/label")).isDisplayed()){
				settingsVO.setPurpose(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[3]/div[1]/div[3]/div")).getText());
				System.out.println(settingsVO.getPurpose());
		}
			
		}}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("valid date is not displayed");
			if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[3]/div")).isDisplayed()){
				settingsVO.setSponser_name(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[3]/div")).getText());
				System.out.println("#################"+settingsVO.getSponser_name());
			}
			if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[5]/div[1]")).isDisplayed()){
				settingsVO.setSponser_address(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[5]/div[1]")).getText());
				//*[@id="content"]/div/div[2]/div[2]/div/div/div[3]/div[1]/div[5]/div[1]	
			}
			if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[5]/div[2]/label")).isDisplayed()){
				settingsVO.setSponser_phone(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[5]/div[2]/label")).getText());
				System.out.println(settingsVO.getSponser_phone());				
			}
			if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[4]/div")).isDisplayed()){
				settingsVO.setSponser_id(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[4]/div")).getText());
					
			}
			if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[3]/div")).isDisplayed()){
				settingsVO.setPurpose(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[3]/div")).getText());
				
		}	
		}
		
		if(mofaDriver.findElement(By.xpath("//*[@id='tblDocumentVisaList']/tbody/tr/td[8]")).isDisplayed()){
			settingsVO.setOccupation(mofaDriver.findElement(By.xpath("//*[@id='tblDocumentVisaList']/tbody/tr/td[8]")).getText());
			
				
		}
	
		logger.info("Details from MOFA captured");
		endTime = System.currentTimeMillis();
		logger.info("Read Invitation data "+(endTime - startTime));
	
		System.out.println("#################"+"Reading is completed");
		
		//for minimize window - start time
		  startTime = System.currentTimeMillis();
		// Fill into Opsys
		//mofaDriver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"w");
		//mofaDriver.switchTo().window(parentWindowHandle);
			/*Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL); 
			r.keyPress(KeyEvent.VK_W); 

			r.keyRelease(KeyEvent.VK_W);
			r.keyRelease(KeyEvent.VK_CONTROL); */
			
			/*r.keyPress(KeyEvent.VK_CONTROL); 
			r.keyPress(KeyEvent.VK_A); 
			
			r.keyRelease(KeyEvent.VK_A);
			r.keyRelease(KeyEvent.VK_CONTROL); */
		//	mofaDriver.quit();
			//mofaDriver.manage().window().setPosition(new Point(-2000, 0));
			Thread.sleep(50);
			 Robot robot = new Robot(); 
		        
		      robot.keyPress(KeyEvent.VK_ALT); 
		      robot.keyPress(KeyEvent.VK_SPACE); 
		      robot.delay(700);
		      robot.keyPress(KeyEvent.VK_N); 
		     
		      robot.keyRelease(KeyEvent.VK_N);
		      robot.keyRelease(KeyEvent.VK_SPACE);
		      robot.keyRelease(KeyEvent.VK_ALT); 
		      endTime = System.currentTimeMillis();//end time
		      logger.info("Minimize chrome window "+ (endTime-startTime));
			
		     //Autofill opsys - start time
			  startTime = System.currentTimeMillis();
	//Keydown disable while filling data
			String jscript ="document.onkeydown = function (e) { return false; }";
			JavascriptExecutor javascript = (JavascriptExecutor) driver;
			try{
			javascript.executeScript(jscript);
			
	//Mousedown disable while filling data
			jscript ="document.onmousedown = function (e) { return false; }";
			javascript.executeScript(jscript);	
			}catch(Exception e2){
				
			}
				
				
			String purpose = settingsVO.getPurpose();
			String visitName = settingsVO.getVisitName();
			JavascriptExecutor ff_javascript = (JavascriptExecutor) driver;
			String ffProgressMessage = "var html='';if(document.getElementById('inProgressEle')!=null){document.getElementById(\"inProgressEle\").innerHTML=\""+propertyReader.getWritemsg()+"\";}else{html='<div id=\"inProgressEle\" style=\"display:inline-block;color: red;padding-left:35%\"> "+propertyReader.getWritemsg()+"</div>';"+
										"var label =  document.getElementById('typing_sponsordetails');"+
										"label.innerHTML = label.innerHTML+html;}";
			ff_javascript.executeScript(ffProgressMessage);
			if(visitName != null && !visitName.equals(""))
			{ 
				try {
				driver.findElement(By.id("arabic_firstname")).clear();
				driver.findElement(By.id("arabic_secondname")).clear();
				driver.findElement(By.id("arabic_othername")).clear();
				driver.findElement(By.id("arabic_lastname")).clear();
				
				if(Util.isProbablyArabic(visitName))
				{
					String []names = visitName.split(" ");
					for(int i = 0;i<names.length;i++){
						if( i == 0) // first name
						{
							System.out.println(""+names[i]+"");
							driver.findElement(By.id("arabic_firstname")).clear();
					/*		driver.findElement(By.id("arabic_firstname")).sendKeys("'"+names[i]+"'");*/
							javascript = (JavascriptExecutor) driver;
							jscript = "var firstname = document.getElementById('arabic_firstname'); firstname.focus();firstname.value = '"+names[i]+"';";	
							javascript.executeScript(jscript);	
						}else if(i ==1){ // second name
							
							driver.findElement(By.id("arabic_secondname")).clear();
						/*	driver.findElement(By.id("arabic_secondname")).sendKeys(Keys.chord(Keys.CONTROL, ""),names[i]);*/
							javascript = (JavascriptExecutor) driver;
							jscript = "var secondname = document.getElementById('arabic_secondname'); secondname.focus();secondname.value = '"+names[i]+"';";	
							javascript.executeScript(jscript);	
							
							
						}else if(i == 2){ // arabic_othername
						
							/*driver.findElement(By.id("arabic_othername")).sendKeys(Keys.chord(Keys.CONTROL, ""),names[i]);*/
							javascript = (JavascriptExecutor) driver;
							jscript = "var othername = document.getElementById('arabic_othername'); othername.focus();othername.value = '"+names[i]+"';";	
							javascript.executeScript(jscript);	
							
							
						}else if(i > 2){
						//arabic_lastname
						lastNames += names[i];
						}
						}
				
				}
				if(lastNames != null && !lastNames.equals("")){
				
				/*driver.findElement(By.id("arabic_lastname")).sendKeys(Keys.chord(Keys.CONTROL, ""),lastNames);*/
					javascript = (JavascriptExecutor) driver;
					jscript = "var lastname = document.getElementById('arabic_lastname'); lastname.focus();lastname.value = '"+lastNames+"';";	
					javascript.executeScript(jscript);	
				}
				}catch(Exception ex) {
					System.out.println("Arabic field exception "+ ex.getLocalizedMessage());
				}
			}
			/*driver.findElement(By.id("profession")).clear();
			driver.findElement(By.id("profession")).sendKeys(Keys.chord(Keys.CONTROL, ""),settingsVO.getOccupation());
			driver.findElement(By.id("sponsorname")).clear();
			driver.findElement(By.id("sponsorname")).sendKeys(Keys.chord(Keys.CONTROL, ""),settingsVO.getSponser_name());
			driver.findElement(By.id("sponserAddress")).clear();
			driver.findElement(By.id("sponserAddress")).sendKeys(Keys.chord(Keys.CONTROL, ""),settingsVO.getSponser_address());
			driver.findElement(By.id("sponsorphone")).clear();
			driver.findElement(By.id("sponsorphone")).sendKeys(Keys.chord(Keys.CONTROL, ""),settingsVO.getSponser_phone());
			driver.findElement(By.id("purpose")).clear();
			driver.findElement(By.id("purpose")).sendKeys(Keys.chord(Keys.CONTROL, ""),purpose);
			driver.findElement(By.id("sponsorid")).clear();
			driver.findElement(By.id("sponsorid")).sendKeys(Keys.chord(Keys.CONTROL, ""),settingsVO.getSponser_id());
			driver.findElement(By.id("enjazNoOfEntries")).clear();
			driver.findElement(By.id("enjazNoOfEntries")).sendKeys(Keys.chord(Keys.CONTROL, ""),settingsVO.getNoOfEntry());
			*/		
			
			
			javascript = (JavascriptExecutor) driver;
			jscript = "var entryobj = document.getElementById('profession'); entryobj.focus();entryobj.value = '"+settingsVO.getOccupation()+"';";
			//javascript.executeScript(jscript);					
			
			jscript = jscript+"var entryobj1 = document.getElementById('sponsorname'); entryobj1.focus();entryobj1.value = '"+settingsVO.getSponser_name()+"';";
			//javascript.executeScript(jscript);
			
			
			jscript = jscript+"var entryobj2 = document.getElementById('sponserAddress'); entryobj2.focus();entryobj2.value = '"+settingsVO.getSponser_address()+"';";
			//javascript.executeScript(jscript);

		
			jscript = jscript+"var entryobj3 = document.getElementById('sponsorphone'); entryobj3.focus();entryobj3.value = '"+settingsVO.getSponser_phone()+"';";
			//javascript.executeScript(jscript);


			jscript = jscript+"var entryobj4 = document.getElementById('purpose'); entryobj4.focus();entryobj4.value = '"+purpose+"';";
			//javascript.executeScript(jscript);

			
			jscript = jscript+"var entryobj5 = document.getElementById('sponsorid'); entryobj5.focus();entryobj5.value = '"+settingsVO.getSponser_id()+"';";
			javascript.executeScript(jscript);			 
			
			
			try{
				jscript = "var entryobj = document.getElementById('enjazNoOfEntries'); entryobj.focus();entryobj.value = '"+settingsVO.getNoOfEntry()+"';";
				javascript.executeScript(jscript);
				jscript = "var obj = document.getElementById('enjazVisaType');obj.focus(); obj.value = '"+purpose+"'";
				javascript.executeScript(jscript);
				//Keydown enable
				jscript ="document.onkeydown = null";
				javascript.executeScript(jscript);	
				
				//mousedown enable
				jscript ="document.onmousedown = null";
				javascript.executeScript(jscript);
				}catch(Exception e3){
					logger.error("enjazVisaType >> ", e3);
				}
			ff_javascript.executeScript("document.getElementById(\"inProgressEle\").innerHTML='"+propertyReader.getCompletMsg()+"'");
			endTime = System.currentTimeMillis();//end time
			logger.info("Autofill opsys "+ (endTime-startTime));
			
			
		return settingsVO;
	
	}
	
	/**
	 * Read Details from Pre-Approval letter
	 * @param driver
	 * @throws Exception
	 */
	public List<PreapprovalVO> readPreApprovalDetails(WebDriver driver,WebDriver mofaDriver, String parentWindowHandle) throws Exception {
	
		long startTime = 0,endTime =0;
		logger.info("Read Pre-Approval details");
		//read start time
		startTime = System.currentTimeMillis();
		SettingsVO settingsVO = new SettingsVO();		
		
		/*WebElement element = mofaDriver.findElement(By.cssSelector("table.formtable"));
		settingsVO.setLetterNo(element.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText());
		settingsVO.setSponser_name(element.findElement(By.xpath("//tbody/tr[2]/td[2]")).getText());
		settingsVO.setSponser_address(mofaDriver.findElement(By.xpath("//tbody/tr[3]/td[2]")).getText());
		settingsVO.setOccupation(mofaDriver.findElement(By.xpath("//table[@class='gridstyle']/tbody/tr[2]/td[4]")).getText());
		settingsVO.setPurpose(mofaDriver.findElement(By.xpath("//table[@class='gridstyle']/tbody/tr[2]/td[1]")).getText());
		settingsVO.setNoOfEntry(getNoOfEntry(mofaDriver.findElement(By.xpath("//table[@class='gridstyle']/tbody/tr[2]/td[5]")).getText()));*/
		
		
		/*WebElement element = mofaDriver.findElement(By.cssSelector("div[class='control-display-label col-md-3  col-sm-3 col-xs-3']"));
		element.getSize();
		element.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();*/
		
		//Letter No(in MOFA website it is Visa Number)
		
		if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[2]/div[1]")).isDisplayed()) {
		settingsVO.setLetterNo(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[2]/div[1]")).getText());
		}
		
		//Sponsor Name(in MOFA website it is Application Requester Name)
		if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[3]/div")).isDisplayed()) {
		settingsVO.setSponser_name(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[3]/div")).getText());
		}
		
		//Sponsor Address(in MOFA website it is Address)
		if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[5]/div[1]")).isDisplayed()) {
		settingsVO.setSponser_address(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[5]/div[1]")).getText());
		}
		
		//To get Sponser_phone number
		if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[5]/div[2]")).isDisplayed()) {
		settingsVO.setSponser_phone(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[5]/div[2]")).getText());
		}
		//Occupation(in MOFA website it is Job Relation)
		                                                          //*[@id="tblDocumentVisaList"]/tbody/tr/td[7]
		if(mofaDriver.findElement(By.xpath("//*[@id='tblDocumentVisaList']/tbody/tr/td[8]")).isDisplayed()) {
		settingsVO.setOccupation(mofaDriver.findElement(By.xpath("//*[@id='tblDocumentVisaList']/tbody/tr/td[8]")).getText());
		}
		//Purpose(in MOFA website it is Visa Kind)
		     
		if(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[3]/div")).isDisplayed()) {
		settingsVO.setPurpose(mofaDriver.findElement(By.xpath("//*[@id='content']/div/div[2]/div[2]/div/div/div[2]/div[1]/div[3]/div")).getText());
		}
		//No of Entry(in MOFA website it is Number of Entries)
		                                                                      //*[@id="tblDocumentVisaList"]/tbody/tr/td[8]
		if(mofaDriver.findElement(By.xpath("//*[@id='tblDocumentVisaList']/tbody/tr/td[9]")).isDisplayed()) {
		settingsVO.setNoOfEntry(getNoOfEntry(mofaDriver.findElement(By.xpath("//*[@id='tblDocumentVisaList']/tbody/tr/td[9]")).getText()));
		}

		
		//System.out.println(settingsVO.getLetterNo()+"--"+settingsVO.getSponser_name()+"--"+settingsVO.getSponser_address()+"--"+settingsVO.getOccupation());
		
		List<PreapprovalVO> preApproval = partialApprovalDetails(mofaDriver);
		endTime = System.currentTimeMillis();
		logger.info("Read Pre approval data "+(endTime - startTime));
	
		//for minimize window - start time
		  startTime = System.currentTimeMillis();
		  Robot robot = new Robot(); 
	        
	      robot.keyPress(KeyEvent.VK_ALT); 
	      robot.keyPress(KeyEvent.VK_SPACE); 
	      robot.delay(700);
	      robot.keyPress(KeyEvent.VK_N); 
	      
	      robot.keyRelease(KeyEvent.VK_N);
	      robot.keyRelease(KeyEvent.VK_SPACE);
	      robot.keyRelease(KeyEvent.VK_ALT); 
	      endTime = System.currentTimeMillis();//end time
	      logger.info("Minimize chrome window "+ (endTime-startTime));
		
	     //Autofill opsys - start time
		  startTime = System.currentTimeMillis();
	     String purpose = settingsVO.getPurpose();
		JavascriptExecutor ff_javascript = (JavascriptExecutor) driver;
		String ffProgressMessage = "var html='';if(document.getElementById('inProgressEle')!=null){document.getElementById(\"inProgressEle\").innerHTML=\""+propertyReader.getWritemsg()+"\";}else{html='<div id=\"inProgressEle\" style=\"display:inline-block;color: red;padding-left:35%\"> "+propertyReader.getWritemsg()+"</div>';"+
									"var label =  document.getElementById('typing_sponsordetails');"+
									"label.innerHTML = label.innerHTML+html;}";
		ff_javascript.executeScript(ffProgressMessage);
		/*driver.findElement(By.id("sponsorname")).clear();
		driver.findElement(By.id("sponsorname")).sendKeys(settingsVO.getSponser_name());
		driver.findElement(By.id("sponserAddress")).clear();
		driver.findElement(By.id("sponserAddress")).sendKeys(settingsVO.getSponser_address());
		driver.findElement(By.id("profession")).clear();
		driver.findElement(By.id("profession")).sendKeys(settingsVO.getOccupation());
		driver.findElement(By.id("purpose")).clear();
		driver.findElement(By.id("purpose")).sendKeys(purpose);
		driver.findElement(By.id("enjazNoOfEntries")).clear();
		driver.findElement(By.id("enjazNoOfEntries")).sendKeys(settingsVO.getNoOfEntry());*/
		
		String jscript = "var obj = document.getElementById('sponsorname'); obj.focus();obj.value = '"+settingsVO.getSponser_name()+"';";
		//JavascriptExecutor javascript = (JavascriptExecutor) driver;
		//javascript.executeScript(jscript);
		jscript = jscript+"var obj1 = document.getElementById('sponserAddress'); obj1.focus();obj1.value = '"+settingsVO.getSponser_address()+"';";
		//setSponser_phone
		jscript = jscript+"var obj100 = document.getElementById('sponsorphone'); obj100.focus();obj100.value = '"+settingsVO.getSponser_phone()+"';";
		//javascript.executeScript(jscript);
		jscript = jscript+"var obj2 = document.getElementById('profession');obj2.focus(); obj2.value = '"+settingsVO.getOccupation()+"';";
		//javascript.executeScript(jscript);
		jscript = jscript+"var obj3 = document.getElementById('purpose'); obj3.focus();obj3.value = '"+purpose+"';";
		ff_javascript.executeScript(jscript);
		try{
		jscript = "var entryobj = document.getElementById('enjazNoOfEntries'); entryobj.focus();entryobj.value = '"+settingsVO.getNoOfEntry()+"';var obj = document.getElementById('enjazVisaType');obj.focus(); obj.value = '"+purpose+"'";
		ff_javascript.executeScript(jscript);
		}catch(Exception e){
			logger.error("enjazVisaType >> ", e);
		}
		
		/*JavascriptExecutor javascript = (JavascriptExecutor) driver;
		javascript.executeScript("document.getElementById('familyGridId').style.display='block';");
		driver.findElement(By.id("familyGridId")).clear();
		driver.findElement(By.id("familyGridId")).sendKeys(new Gson().toJson(preApproval));
		javascript.executeScript("document.getElementById('familyGridId').style.display='none';");*/
		//JavascriptExecutor js = (JavascriptExecutor) driver;  
		
		//ff_javascript.executeScript("familyGridId ="+new Gson().toJson(preApproval),element);
		ff_javascript.executeScript("window.familyGridId="+new Gson().toJson(preApproval));
		ff_javascript.executeScript("document.getElementById(\"inProgressEle\").innerHTML='"+propertyReader.getCompletMsg()+"'");
		endTime = System.currentTimeMillis();//end time
		logger.info("Autofill opsys "+ (endTime-startTime));
		return preApproval;
	}
	
	public List<PreapprovalVO> partialApprovalDetails(WebDriver driver) throws Exception {
		logger.info("Read preapproval grid details");
		String visitName = ""; 
		String lastNames ="";
		String [] names;
				
		WebElement element = driver.findElement(By.cssSelector("table.styled tbody"));
		List<WebElement> trElements = element.findElements(By.cssSelector("tr"));
		List<WebElement> trialElements = element.findElements(By.xpath("tr"));
		int trCount = trElements.size();
		List<PreapprovalVO> preApproval = null; 
		if(trCount >= 1) {
			preApproval = new ArrayList<PreapprovalVO>();
			for(int count=1; count <=trCount; count++) {
				PreapprovalVO approval = new PreapprovalVO();
				approval.setSponser_name(element.findElement(By.xpath("tr["+count+"]/td[4]")).getText());
				approval.setPurpose(element.findElement(By.xpath("tr["+count+"]/td[1]")).getText());
				approval.setEnjazNoOfEntries(getNoOfEntry(element.findElement(By.xpath("tr["+count+"]/td[9]")).getText()));
				approval.setOccupation(element.findElement(By.xpath("tr["+count+"]/td[8]")).getText());
				visitName = element.findElement(By.xpath("tr["+count+"]/td[4]")).getText();
			
				
				if(visitName != null && !visitName.equals("")){
					
						names = visitName.split(" ");
						
				
					for( int i=0; i<names.length; i++)
					{
						if(Util.isProbablyArabic(names[i]))
						{
							if(i ==0){
								approval.setFirstName(names[i]);
								
								
							}else if (i ==1){
								approval.setSecondName(names[i]);
								
								
							}else if(i == 2){
								approval.setOtherName(names[i]);
								
							}else if( i > 2){
								lastNames += names[i];
							}
						}
					}
					if(lastNames != null && !lastNames.equals("")){
						if(Util.isProbablyArabic(lastNames)){
							approval.setLastName(lastNames);
						}
					}
				 
				}
		
				preApproval.add(approval);
			}
		}
		
		return preApproval;
	}
	
	/**
	 * Method used to switch page language to English
	 * @param driver
	 * @return
	 */
	public String pageLangSwitch(WebDriver driver) {
		logger.info("Switch page language");
		try {
			SettingsVO settingConfig = settingsDetails.readConfig();
			WebElement linkElement = driver.findElement(By.xpath("//a[contains(@class,'language')]"));
			if (linkElement != null) {
				String currentLang = linkElement.getText();
				System.out.println("current Language: " + currentLang);
				if (settingConfig.getMofaLang().equalsIgnoreCase("English")) {
					if (currentLang.equalsIgnoreCase("E")) {
						if (driver.findElement(By.id("en-US")).isDisplayed()) {
							JavascriptExecutor executor = (JavascriptExecutor) driver;
							executor.executeScript("arguments[0].scrollIntoView(true);",
									driver.findElement(By.id("en-US")));
							executor.executeScript("arguments[0].click();", driver.findElement(By.id("en-US")));
							System.out.println("Language Changed to English");
						}
					} else {
						System.out.println("Language not changed as it is already English");
					}
				} else if (settingConfig.getMofaLang().equalsIgnoreCase("Arabic")) {
					if (currentLang.equalsIgnoreCase("ع")) {
						if (driver.findElement(By.id("ar-SA")).isDisplayed()) {
							JavascriptExecutor executor = (JavascriptExecutor) driver;
							executor.executeScript("arguments[0].scrollIntoView(true);",
									driver.findElement(By.id("ar-SA")));
							executor.executeScript("arguments[0].click();", driver.findElement(By.id("ar-SA")));
							System.out.println("Language Changed to Arabic");
						}
					} else {
						System.out.println("Language not changed as it is already Arabic");
					}
				}
			}
		} catch (ElementClickInterceptedException e) {
			e.printStackTrace();
			logger.error("Page Lang Switch Failed" + e);
		} catch (Exception langException) {
			langException.printStackTrace();
			logger.error("Page Lang Switch Failed" + langException);
		}
		return "";
	}
	

	public String mofaPageLangSwitch(WebDriver driver) {
		
		logger.info("Switch page language");
		try{
			
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[contains(@class,'language')]")));
			WebElement linkElement = driver.findElement(By.xpath("//a[contains(@class,'language')]"));
			if (linkElement != null) {
				String currentLang = linkElement.getText();
				System.out.println("current Language: " + currentLang);
				
					if (currentLang.equalsIgnoreCase("E")) {
						if (driver.findElement(By.id("en-US")).isDisplayed()) {
							JavascriptExecutor executor1 = (JavascriptExecutor) driver;
							executor1.executeScript("arguments[0].scrollIntoView(true);",
									driver.findElement(By.id("en-US")));
							executor1.executeScript("arguments[0].click();", driver.findElement(By.id("en-US")));
							System.out.println("Language Changed to English");
						}
					}
			}
		}catch(Exception langException){
			logger.error("Page Lang Switch Failed",langException);
		}
		
		return "";
	}
	
	
	private String getNoOfEntry(String _noOfEntry){
		
		/*if(_noOfEntry !=null && !_noOfEntry.equalsIgnoreCase(""))
		{
			String[] noOfEntryArry =  _noOfEntry.split("-");
			_noOfEntry = noOfEntryArry[0];
			if(Character.isWhitespace(_noOfEntry.charAt(0)))
			{
				_noOfEntry = _noOfEntry.substring(1);
			}
		}*/// commenting above statement as per rajesh request. 
		return _noOfEntry;
	}
	
	public String validateInvitationMofaLetters(WebDriver driver)
	{
		ResponseVO response = new ResponseVO();
		try{
			/*WebElement printheadLS =	driver.findElements(By.xpath("//table[contains(@class, 'formtable')]")).get(1);
			List<WebElement> lstValidateElements = printheadLS.findElements(By.xpath(".//td"));
			for (WebElement webElement : lstValidateElements) {
			   String innerValue = webElement.getText();
			   
			   if(innerValue != null && !innerValue.equals(""))
			   {
				   response.setStatus("Approved");
				
				   logger.info("validateInvitationMofaLetters: Approved");
				   break;
			   }else{
				   response.setStatus("Disapproved");
				   logger.info("validateInvitationMofaLetters: Disapproved");
			   }
			}*/
			//For validating the invitation letter
			String letterStatus =	driver.findElement(By.xpath("//*[@id='content']/div/div[2]/div/div/div[2]/div[1]/div[12]/div[2]/span")).getText();
			
			if(letterStatus !=null && !letterStatus.equals("")){
				response.setStatus("Approved");
				 logger.info("validateInvitationMofaLetters: Approved");
			}else{
				response.setStatus("Disapproved");
				 logger.info("validateInvitationMofaLetters: Disapproved");
			}
		
		}catch(Exception e){
			logger.error("validateInvitationMofaLetters >>",e);
			
		}
		return new Gson().toJson(response);
	}
	
	/**
	 * Method used to validate from cashier
	 */
	public String validatePreapprovalMofaLetters(WebDriver driver)
	{
		/*String checkValue = "\u0627\u0644\u0631\u062C\u0627\u0621 \u0637\u0628\u0627\u0639\u0629 \u0627\u0644\u0645\u0633\u062A\u0646\u062F \u0648\u0645\u0631\u0627\u062C\u0639\u0629 \u0645\u0645\u062B\u0644\u064A\u0629 \u0627\u0644\u0645\u0645\u0644\u0643\u0629 \u0644\u0625\u0635\u062F\u0627\u0631 \u062A\u0623\u0634\u064A\u0631\u0629 \u0627\u0644\u062F\u062E\u0648\u0644";
	
		//String checkValue = Util.convertFromUTF8(actualValue);
		ResponseVO response = new ResponseVO();
		//remove
		WebElement printheadLS =	driver.findElement(By.className("printhead"));
		try{
		    byte[] utf8 = checkValue.getBytes("UTF-8");
			checkValue = new String(utf8, "UTF-8");
		List<WebElement> lstValidateElements = printheadLS.findElements(By.xpath(".//h3"));
		//List<WebElement> lstValidateElements = printheadLS.findElements(By.xpath(".//h3 style='color: Red;'"));
		for (WebElement webElement : lstValidateElements) {
		   String innerValue = webElement.getText();
		   if(innerValue.contains(checkValue))
		   {
			   response.setStatus("Approved");			   
			   logger.info("validatePreapprovalMofaLetters: Approved");
			   break;
		   }else{
			   response.setStatus("Disapproved");
			   logger.info("validatePreapprovalMofaLetters: Disapproved");
		   }
		}
		}catch(Exception e){
			logger.error("validatePreapprovalMofaLetters >>",e);
		}*/
		
		ResponseVO response = new ResponseVO();
		try{
			
			String visaNumber =	driver.findElement(By.xpath("//*[@id='content']/div/div[2]/div/div/div[2]/div[1]/div[1]/div[2]/div/span[1]")).getText();
			
			String checkValue="Visa Number";
			
			if(visaNumber.contains(checkValue)){
			   response.setStatus("Approved");			   
			   logger.info("validatePreapprovalMofaLetters: Approved");
			}else{
			   response.setStatus("Disapproved");
			   logger.info("validatePreapprovalMofaLetters: Disapproved");
			}
		}catch(Exception e){
			logger.error("validatePreapprovalMofaLetters >>",e);
		}
		return new Gson().toJson(response);
	}
	
	public WebDriver ifOldWebsite(WebDriver driver, SettingsVO settingVO) throws InterruptedException {
		try {
			SettingsVO settingConfig = settingsDetails.readConfig();
			String searchValue = "2";
			driver.findElement(By.xpath("//*[@id=\"dlgMessageContent\"]/div/div/button")).click();
			pageLangSwitch(driver);
			if (settingConfig.getMofaLang().equals("English")) {
				driver.findElement(By.xpath("//*[@id=\"dlgMessageContent\"]/div/div/button")).click();
			}
			driver.findElement(By.xpath("//*[@id=\"dlgMessageContent\"]/div/div/button")).click();
			new Select(driver.findElement(By.id("SearchingType"))).selectByValue(searchValue);
			By letter = By.id("ApplicationNumber");
			By sponser = By.id("SponserID");
			driver.findElement(letter).sendKeys(settingVO.getLetterNo());
			driver.findElement(sponser).sendKeys(settingVO.getSponser_id());
			final String previousURL = driver.getCurrentUrl();
			WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofMinutes(2));
			ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					if (!d.getCurrentUrl().equalsIgnoreCase(previousURL)) {
						return true;
					} else {
						return false;
					}
				}
			};
			driverWait.until(e);
			logger.info("Submit Validate page");
		} catch (NoSuchElementException ne) {
			ne.printStackTrace();
			if (validattempt <= 2) {
				ifOldWebsite(driver, settingVO);
			}
			validattempt++;
			logger.error("validateInvitation >>", ne);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("validateInvitation Failed", e);
			throw new InterruptedException(e.getMessage());
		}
		validattempt = 0;
		return driver;
	}
	
}
