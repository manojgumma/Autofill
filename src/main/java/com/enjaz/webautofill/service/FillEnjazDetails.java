package com.enjaz.webautofill.service;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.http.HttpServletRequest;


import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
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

import com.enjaz.webautofill.util.OpsysApplicantBean;
import com.enjaz.webautofill.util.OpsysApplicantParser;
import com.enjaz.webautofill.util.PropertyReader;
import com.enjaz.webautofill.valueobject.PaymentDetails;
import com.enjaz.webautofill.valueobject.SettingsVO;
import com.google.gson.Gson;

/**
 * 17-04-2015
 * 
 * @author Ramesh
 * 
 */
@Service
public class FillEnjazDetails {
	private static final Logger logger = LoggerFactory
			.getLogger(FillEnjazDetails.class);
	@Autowired
	private PropertyReader propertyReader;
	@Autowired
	private OpsysApplicantParser applicantObj;
	@Autowired
	private ReadMofaDetails readMofaDetails;
	@Autowired
	private SettingsDetails settingsDetails;
	static WebDriver fireboxDriver;
	static String eNumber = "";
	HashMap<String, Applicantdetails> applicantDetails = new HashMap<String, Applicantdetails>();
	/**
	 * Fill into Visa
	 * 
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public WebDriver fillVisaDetails(WebDriver driver, SettingsVO settingVO)
			throws InterruptedException {
		
		logger.info("Initiate fill Visa details" + new Gson().toJson(settingVO));
		
	// WebDriverWait wait = new WebDriverWait(driver, 10);
		SettingsVO settingConfig = settingsDetails.readConfig();
		if(null != settingConfig.getEnjazURL() && !settingConfig.getEnjazURL().equals("")){
		  try{
			  //https://enjazit.com.sa/Account/Login/company
			//URI uri = new URI(settingConfig.getEnjazURL("https://enjazit.com.sa/Account/Login/company"));
			  URI uri = new URI(settingConfig.getEnjazURL()+"Account/Login/enjazcompany");
			settingConfig.setLogin_url(uri.normalize().toString());
		  }catch(Exception e){
			  logger.error("Uri Build >>",e);
		  }
		} else {
			settingConfig.setLogin_url(propertyReader.getLogin_url());
		}
		
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		try {
			driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
			driver.get(settingConfig.getLogin_url());
			String currentUrl = driver.getCurrentUrl();
			if (settingVO.getMofaURL().equals("https://visa.mofa.gov.sa/")) {
				try {
					readMofaDetails.pageLangSwitch(driver);
				}catch(NoSuchElementException e) {
					e.printStackTrace();
				}
			} else {
				readMofaDetails.pageLangSwitch(driver);
			}
			
			if (currentUrl.equalsIgnoreCase(settingConfig.getRedirectUrl()) && !currentUrl.equalsIgnoreCase(settingConfig.getLogin_url()) ){
				driver.get(settingConfig.getLogin_url());
				currentUrl = driver.getCurrentUrl();
				readMofaDetails.pageLangSwitch(driver);
			}
			
			
			if (currentUrl.equalsIgnoreCase(settingConfig.getLogin_url())) {
				driver.findElement(By.id("UserName")).sendKeys(
						settingVO.getEnj_user_name().trim());
				driver.findElement(By.id("Password")).sendKeys(
						settingVO.getEnj_password().trim());
				// Move curser to captcha text box 
				new Actions(driver).moveToElement(driver.findElement(By.id("Captcha"))).click().perform();
				final String previousURL = driver.getCurrentUrl();
				WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(120));
				  @SuppressWarnings("rawtypes")
				ExpectedCondition e = new ExpectedCondition<Boolean>() {
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

			        driverWait.until(e);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			WebElement warning = driver.findElement(By
					.xpath("//div[contains(@class,'error-msg')]"));
			if (warning != null) {
				javascript
						.executeScript("document.getElementsByClassName('error-msg')[0].style.display = 'block';");
				String warningMsg = "document.getElementsByClassName('error-msg')[0].innerHTML  = "
						+ "'<b class=\"WarningMsg\" style=\"color:yellow\">Unable to fill enjaz details</b>';";
				javascript.executeScript(warningMsg);
			}
			throw new InterruptedException(e.getMessage());
		}
		return driver;
	}
	/**
	 * 
	 * @param driver
	 * @param settingVO
	 * @return
	 * @throws InterruptedException
	 */
	public String populateDetails(WebDriver driver, SettingsVO settingVO, HttpServletRequest request)
			throws Exception {
		
		String visaFees, enjazFees, medicalFees;
		visaFees = enjazFees = medicalFees = "";
		String firstName = "";
		String passportNumber, emailId, contactNo ="";
		
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();

		
		readMofaDetails.pageLangSwitch(driver);
		
		String currentUrl = driver.getCurrentUrl();
		
		if (!currentUrl.equalsIgnoreCase(settingVO.getVisa_url())){
			driver.get(settingVO.getVisa_url());
			currentUrl = driver.getCurrentUrl();
		}
		
		
		OpsysApplicantParser applicantObj = new OpsysApplicantParser();
		String opsysEndPoint = settingsDetails.readConfig().getOpsys_url();
		if (opsysEndPoint.trim().endsWith("/")) {
			opsysEndPoint = opsysEndPoint
					+ propertyReader.getLoadEnjazDetails();
		} else {
			opsysEndPoint = opsysEndPoint + "/"
					+ propertyReader.getLoadEnjazDetails();
		}
		startTime = System.currentTimeMillis();
		OpsysApplicantBean opsysApplicantBean = applicantObj
				.getOpsysApplicantDetails(opsysEndPoint,
						settingVO.getApplicant_id());
		LinkedHashMap<String, String> propMap = opsysApplicantBean
				.getElementMap();
		endTime = System.currentTimeMillis();
		logger.info("Total taken for enjaz service is(in ms) : " + (endTime-startTime));
		if(propMap == null){
			logger.error("Empty data has been received from opsys service : " + settingVO.getApplicant_id());
			return "";
		}
		//Keydown disable while filling data
		String jscript ="document.onkeydown = function (e) { return false; }";
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		try{
			javascript.executeScript(jscript);
		
			//Mousedown disable while filling data
			jscript ="document.onmousedown = function (e) { return false; }";
			javascript.executeScript(jscript);	
		}catch(Exception e){
			
		}
		
		@SuppressWarnings("rawtypes")
		Iterator properties = propMap.entrySet().iterator();
		
		Applicantdetails appDetails = new Applicantdetails();
		appDetails.setApplicantId(settingVO.getApplicant_id());
		
		logger.info("Fill Details Started");
		String enjazFillingMsg = propertyReader.getEnjazFillingMsg();
		
		String jquery = "var obj = $('.error-msg:eq(1)');obj.html('"+enjazFillingMsg+"');obj.show()";
		JavascriptExecutor javascriptExe = (JavascriptExecutor) driver;
		javascriptExe.executeScript(jquery);
		jquery = "var obj = $('#divErrorMsg');obj.hide()";
		javascriptExe.executeScript(jquery);
		startTime = System.currentTimeMillis();
		 
		try {
			startTime = System.currentTimeMillis();
			while (properties.hasNext()) {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				Map.Entry<String, String> mapEntry = (Map.Entry) properties.next();
				String keyName =  mapEntry.getKey().trim();	
			
				String contentStr = mapEntry.getValue();
				if(keyName.equals("ENJAZ_FEES"))
				{
					enjazFees = contentStr;
					appDetails.setEnjazFee(enjazFees);
					continue;
					
				}if(keyName.equals("VISA_FEES"))
				{
					visaFees = contentStr;
					appDetails.setVisaFee(visaFees);
					continue;
				}
				if(keyName.equals("MEDICAL_FEES"))
				{
					medicalFees = contentStr;
					appDetails.setMedicalFee(medicalFees);
					continue;
				}
				if(keyName.equals("EFIRSTNAME")){
					firstName = contentStr;
				}
				if(keyName.equalsIgnoreCase("PASSPORTnumber")){
					passportNumber = contentStr;
					appDetails.setPassportNumber(passportNumber);
					if(passportNumber == null && passportNumber.equals("")){
						logger.info("Passport number is empty");
					}
				}
				
				if(keyName.equalsIgnoreCase("emailId")){
					emailId = contentStr;
					if(emailId !=null && !emailId.equalsIgnoreCase("")){
						appDetails.setEmail(emailId);
					}else{
						appDetails.setEmail("");
					}
					continue;
				}
				
				if(keyName.equalsIgnoreCase("primaryPhoneNumber")){
					contactNo = contentStr;
					if(contactNo !=null && !contactNo.equalsIgnoreCase("")){
						appDetails.setContactNo(contactNo);
					}else{
						appDetails.setContactNo("");
					}
					continue;
				}
		
		//WebElement mapElement = driver.findElement(By.id(keyName));
				if(keyName.equals("PersonalPhoto")){
					if(contentStr != null && !contentStr.isEmpty() && !contentStr.equals(" "))
					{
					
						try{
							String filePath = new File(".").getCanonicalPath()+"\\photo";
						if(!(filePath.trim().equals(""))) {
							
							File file = new File(filePath);
							if(!file.exists())
							{
								file.mkdir();
							}
								byte[] data = Base64.decodeBase64(contentStr);
								try {
									Random rand = new Random();
									int selected = rand.nextInt(1000);
									filePath += "\\" + selected + ".jpg";
									File of = new File(filePath);
									FileOutputStream osf = new FileOutputStream(of);
									osf.write(data);
									osf.flush();
									osf.close();
									//driver.findElement(By.id("PersonalPhoto")).sendKeys(filePath);
									//Enjaz UI change
									driver.findElement(By.id("PersonalImage")).sendKeys(filePath);
									of.deleteOnExit();
								}catch(Exception e){
									logger.error("Image create from base64", e);
								}
							
						}
						}catch(Exception e){
							logger.error("Image File Operation Exception>>", e);
						}
					
											
					}
					
				}else{
					//if ((mapElement != null) && (!contentStr.trim().equals(""))) {
					
					
					if (!contentStr.trim().equals("")) {
					//if (cssStr.equalsIgnoreCase("display: none;")) {
					if(keyName.equalsIgnoreCase("PASSPORType") || keyName.equalsIgnoreCase("NATIONALITY") || keyName.equalsIgnoreCase("RELIGION") 
							||keyName.equalsIgnoreCase("SOCIAL_STATUS" )||keyName.equalsIgnoreCase("Sex") ||keyName.equalsIgnoreCase("VisaKind") 
							||keyName.equalsIgnoreCase("EmbassyCode") || keyName.equalsIgnoreCase("ENTRY_POINT") || keyName.equalsIgnoreCase("NUMBER_OF_ENTRIES")
							||keyName.equalsIgnoreCase("COMING_THROUGH")||keyName.equalsIgnoreCase("NATIONALITY_FIRST") ||keyName.equalsIgnoreCase("Number_Entry_Day")  ||keyName.equalsIgnoreCase("PASSPORT_ISSUE_PLACE") )
					{
				driver.manage().timeouts()
							.implicitlyWait(2, TimeUnit.SECONDS);
						if (keyName.equalsIgnoreCase("ENTRY_POINT") || keyName.equalsIgnoreCase("NUMBER_OF_ENTRIES") || keyName.equalsIgnoreCase("EmbassyCode") || keyName.equalsIgnoreCase("Number_Entry_Day")) {
							WebElement dropdownElement = null;
							try {
								dropdownElement = driver.findElement(By.id(keyName));
								Select dropdown = new Select(dropdownElement);
								dropdown.selectByVisibleText(contentStr.trim());
							} catch (StaleElementReferenceException e) {
								System.out.println("Element is stale. Re-locating the element...");
								 dropdownElement = driver.findElement(By.id(keyName));
							} catch (NoSuchElementException e) {
								System.out.println("Element not found by ID: " + keyName);
							}
						}
						driver.manage().timeouts()
						.implicitlyWait(10L, TimeUnit.SECONDS);
						
						if (keyName.equalsIgnoreCase("NATIONALITY_FIRST") ) {
							
							WebElement element = driver.findElement(By.cssSelector("input[name='Has_NATIONALITY_FIRST'][value='0']"));
							((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
							
							if (!contentStr.equalsIgnoreCase("null")) {
								 element = driver.findElement(By.cssSelector("input[name='Has_NATIONALITY_FIRST'][value='1']"));
								((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
							}
					}
						
						
						String jScript = "";
						
						if( keyName.equalsIgnoreCase("COMING_THROUGH") || keyName.equalsIgnoreCase("Sex") ||  keyName.equalsIgnoreCase("VisaKind")||  
								keyName.equalsIgnoreCase("NATIONALITY")||  keyName.equalsIgnoreCase("PASSPORType") || 
								 keyName.equalsIgnoreCase("RELIGION") || keyName.equalsIgnoreCase("SOCIAL_STATUS") || 
								keyName.equalsIgnoreCase("PASSPORT_ISSUE_PLACE")){
						       jScript="$('#"+keyName+"').trigger( 'change' );";
					    }
						
						//Enjaz drop-down component change
						jScript = "var obj = jQuery('#n_field_n option');var n_name=''; " +
								"var n_value='';$.each(obj, function(){if('"+ contentStr.trim() +"' == $(this).text()){	n_name = $(this).text();	n_value = $(this).val(); }});" +
								"if(n_name != '') {$('#n_field_n').focus();$('#n_field_n').val(n_value);$('#select2-n_field_n-container').html('<span class=\"select2-selection__clear\">�</span>'+n_name);}"+jScript;
						
						 javascript = (JavascriptExecutor) driver;
						try{
							
						 javascript.executeScript(jScript.replace("n_field_n", keyName));
						
						}catch(Exception ex){
							ex.printStackTrace();
							logger.error("enjaz autofill  >>", ex);
						}
						
				
					} else {
						 jscript = "var obj = document.getElementById('"+ keyName +"'); obj.value ='"+ contentStr + "'";
						 javascript = (JavascriptExecutor) driver;
						javascript.executeScript(jscript);
						
					}
					}
				}
			}
			
			
			applicantDetails.put(driver.getWindowHandle(), appDetails);
			 
			
			//Missing filled mandatory field
			String enjazsuccessfulMsg = propertyReader.getEnjazSuccessfulMsg();
			 jquery = "var obj = $('.error-msg:eq(1)');obj.html('"+enjazsuccessfulMsg+"');obj.show();";
			 //javascriptExe.executeScript(jquery);
			 jquery = jquery+"var obj1 = $('#divErrorMsg');obj1.hide();";
			 //to focus the last element
			 jquery = jquery+"$('#porpose').focus()";
			 javascriptExe.executeScript(jquery);
			 
			 try{
			//Keydown enable
				jscript ="document.onkeydown = null";
				javascript.executeScript(jscript);	
				
				//mousedown enable
				jscript ="document.onmousedown = null";
				javascript.executeScript(jscript);
			 }catch(Exception e){}
		
			 endTime = System.currentTimeMillis();
			 logger.info("Total taken to fill the Enjaz form (in ms) : " + (endTime-startTime));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		endTime = System.currentTimeMillis();
		
		 System.out.println("Total elapsed time in enjaz info filled :" + (endTime-startTime));
		 logger.info("Total elapsed time in enjaz info filled :" + (endTime-startTime));
		 logger.info("Enjaz info populated successfully");
		return "";
	}

	public void AfterSaveProcess(WebDriver driver, String applicationNo, SettingsVO settingVO){
	
		Applicantdetails appDetails = applicantDetails.get(driver.getWindowHandle());
		System.out.println("Applicant Id: "+appDetails.getApplicantId());
		logger.error("AfterSaveProcess>> Applicant Id: "+appDetails.getApplicantId());
	      
		
		String printLanguage = settingVO.getPrintLang();
		String enjazPayPassword = settingVO.getEnzPayPassword();
		String serviceEnjazPayPass = settingVO.getServiceEnjazPaymentPassword();
		
		logger.error("AfterSaveProcess>> Applicant Id: "+appDetails.getApplicantId());
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		
		String return_value = (String) javascript.executeScript("return $('.jtable-data-row td').filter(function() { return $(this).text() == '"+appDetails.getPassportNumber()+"'; }).closest('tr').find('td:first-child').text()");
		System.out.println("return_value "+return_value);
		if("".equals(applicationNo) || applicationNo== null){
			applicationNo=return_value;
			}

		if(null!=serviceEnjazPayPass&&!serviceEnjazPayPass.isEmpty())
			enjazPayPassword=serviceEnjazPayPass;
		
		enjazInformationTechnology(driver,settingVO);
		payVisaFees(driver,applicationNo,appDetails.getPassportNumber());
		
		if(singlePayment(driver,appDetails.getVisaFee(),appDetails.getEnjazFee(),appDetails.getMedicalFee())){
			
			//password should be getting form settings
			if(singlePaymentTransactions(driver,enjazPayPassword,appDetails.getApplicantId())){ 
				SinglePaymentAfterPaymentPost(driver,appDetails.getApplicantId(),appDetails.getMedicalFee());
				printFunctionlaity(driver, printLanguage);
			}
		}
	}
	
	private void enjazInformationTechnology(WebDriver driver, SettingsVO settingVO){ // 1
		try{
			logger.info("Start enjazInformationTechnology");
			String payeeFeesURL="";
			   //payeeFeesURL = "https://enjazit.com.sa/Enjaz/PayFees";
			payeeFeesURL=settingVO.getEnjazURL()+propertyReader.getPayeeFeesURL();
			logger.info("PayeeFeesURL:"+ payeeFeesURL);
			
			driver.get(payeeFeesURL);
				
			
		}catch(Exception e){
			logger.error("enjazInformationTechnology >>", e);
			
		}
	}
	private void payVisaFees(WebDriver driver, String applicationNo, String passportNo){ //2
		try
		{
		
			JavascriptExecutor javascript = (JavascriptExecutor) driver;
			String jscript = "var obj = document.getElementById('appno'); obj.value='"+applicationNo+"';var objPassportNo = document.getElementById('PassportNumber');objPassportNo.value='"+passportNo+"'";
			//enajz has change the passport number id - PassportNumber to PassportNo
			javascript.executeScript(jscript);
			//Click the search button
			jscript = "$('.btn').click()";
			javascript.executeScript(jscript);
			
		}catch(Exception e){
			logger.error("payVisaFees >>", e);
		}
		
	}
	
	private boolean singlePayment(WebDriver driver, String visaFees, String enajzFees, String medicalFees)
	{   
		
			readMofaDetails.mofaPageLangSwitch(driver);
		
		 
		boolean result = false;
		try{
			String jscript = "";
			JavascriptExecutor javascript = (JavascriptExecutor) driver;
			if(visaFees != null && !visaFees.equals("")){
				
				Double fees =  Double.parseDouble(visaFees);
				if(fees > 0){
					try{
					//Visa Fees
					jscript = "document.getElementById('WillPayVisaFee').checked = true";
					javascript.executeScript(jscript);
					result = true;
					}catch(Exception e){
						logger.error("Visa Fees >> ", e);
						
					}
					
				}
			}
		//	if(enajzFees != null && !enajzFees.equals("")){
				
				//int fees = Integer.parseInt(enajzFees);
				//if(fees > 0){
					try{
					//Application Fees
					jscript = "document.getElementById('WillPayAppFee').checked = true";
					javascript.executeScript(jscript);
					result = true;
					}catch(Exception e){
						logger.error("enajzFees >> ", e);
					}
						
					
			//	}
			//}
			if(medicalFees != null && !medicalFees.equals("")){
							
				Double fees =  Double.parseDouble(medicalFees);
				if(fees > 0){
					try{
					jscript = "document.getElementById('WillPayMedicalCertificateFee').checked = true";
					javascript.executeScript(jscript);
					result = true;
					}catch(Exception e){
						logger.error("medicalFees >> ", e);
					}
				}
				
			}
			
			try{
				//Medical Insurance Fees
				jscript = "document.getElementById('WillPayMedicalInsurance').checked = true";
				javascript.executeScript(jscript);
				result = true;
				}catch(Exception e){
					logger.error("MedicalInsuranceFees >> ", e);
				}
			
			if(result)	{	
			//Click continue buttton
			jscript = "var obj = $('.formloginbtn.fleft'); obj.click()";
			javascript.executeScript(jscript);
			}else{
				logger.info("All the fees value is 0 so it couldn't pay");
			}
			
			
		}catch(Exception e){
			logger.error("singlePayment >>", e);
		}
		return result;
	}
	
	/*@SuppressWarnings("unchecked")
	private void singlePaymentTransactionsOld(final WebDriver driver, String password, String applicationID){
		try{
			
			JavascriptExecutor javascript = (JavascriptExecutor) driver;
			String jscript = "document.getElementById('PaymentPassword').value = '"+password+"'";
			
			javascript.executeScript(jscript);
			//submit the pay button			
			jscript = "var obj = $('.formloginbtn.fleft'); obj.click()";
			javascript.executeScript(jscript);
		
			//Call payment done service url
			String opsysEndPoint = settingsDetails.readConfig().getOpsys_url();
			String paymentDoneURL =  opsysEndPoint +"/"+propertyReader.getPaymentDoneURL();
			URI uri = new URI(paymentDoneURL);
			paymentDoneURL = uri.normalize().toString();
			AsyncExecutor asyncExecutor = new AsyncExecutor();
			asyncExecutor.paymentDone(paymentDoneURL, applicationID);
	
			WebDriverWait driverWait = new WebDriverWait(driver, (1000*60*3));
			  ExpectedCondition e = new ExpectedCondition<Boolean>() {
		          public Boolean apply(WebDriver d) {
		        	  String tdValue = "";
						System.out.println("inside driver wait");
						int cnt = 0;
						try{
							List<WebElement> tableElements = null;
							try{
						 tableElements = driver.findElements(By.tagName("table"));
							}catch(Exception e){
								System.err.println(e.getMessage());
							}
							//issue fix : Autofill doesn�t navigate automatically up to the print page (Start)
							if(tableElements.size()>2){
								WebElement elements = tableElements.get(2);								
							//WebElement elements = tableElements.get(3);//issue fix : Autofill doesn�t navigate automatically up to the print page (end)
								
						// Get the rows which change always as and when users are added
						List<WebElement> allUsers = elements.findElements(By.xpath(".//tbody/tr/td"));

						// Loop through each row of users table
						//Take the trans number to judge this page completed loaded
						for(WebElement user : allUsers) {

						  	cnt++;
								tdValue = user.getText();
								if(cnt == 2){
									int value = Integer.parseInt(tdValue);
									if(value > 0)
									{
										return true;
									
									}
								}
							}
					
							
							}
					}catch(Exception e){
						logger.error("singlePaymentTransactions >>", e);
					}
					return false;
		          }
		        };

		        driverWait.until(e);
			System.out.println("singlePaymentTransactions result==> : "+e);
		}catch(Exception e){
			logger.error("singlePaymentTransactions >>", e);
		}
		
	}*/
	
	
	private boolean singlePaymentTransactions(final WebDriver driver, String password, String applicationID){
		boolean result = false;
		
		try{
			
			JavascriptExecutor javascript = (JavascriptExecutor) driver;
			String jscript = "document.getElementById('PaymentPassword').value = '"+password+"'";
			
			javascript.executeScript(jscript);
			//submit the pay button			
			jscript = "var obj = $('.formloginbtn.fleft'); obj.click()";
			javascript.executeScript(jscript);
		
			//Call payment done service url
			String opsysEndPoint = settingsDetails.readConfig().getOpsys_url();
			String paymentDoneURL =  opsysEndPoint +"/"+propertyReader.getPaymentDoneURL();
			URI uri = new URI(paymentDoneURL);
			paymentDoneURL = uri.normalize().toString();
			AsyncExecutor asyncExecutor = new AsyncExecutor();
			asyncExecutor.paymentDone(paymentDoneURL, applicationID);
			
			List<WebElement> errorMsgList = null;
			errorMsgList = driver.findElements(By.className("error-msg"));
			System.out.println("error message size: "+errorMsgList.size());
			for(WebElement errorMsg : errorMsgList) {
				System.out.println("error message text: "+errorMsg.getText());
				if(errorMsg !=null && errorMsg.getText().trim().equalsIgnoreCase("Payment Failed")){
					return false;
				}
				
			}
			
			WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofMinutes(3));
			  ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
		          public Boolean apply(WebDriver d) {
		        	  //String tdValue = "";
						System.out.println("inside driver wait");
						//int cnt = 0;
						try{
							WebElement message = driver.findElement(By.cssSelector(".alert.alert-success"));
							
							if(message.getText().trim().equalsIgnoreCase("Payment successfull")){
								
								List<WebElement> elementsRoot = driver.findElements(By.xpath("//div[@class='form-group']"));
								
								if(elementsRoot !=null && elementsRoot.size() > 0){
									
									//Getting the first transaction number value and check greater than zero
									 String feeValue= elementsRoot.get(0).findElement(By.xpath("./div")).getText().trim();
									 
									 if(Integer.parseInt(feeValue) > 0){
										 return true;
									 }
									
									
								}
							
								/*List<WebElement> tableElements = null;
								try{
									tableElements = driver.findElements(By.tagName("table"));
								}catch(Exception e){
									System.err.println(e.getMessage());
								}
								//issue fix : Autofill doesn�t navigate automatically up to the print page (Start)
								if(tableElements.size()>2){
									WebElement elements = tableElements.get(2);								
								//WebElement elements = tableElements.get(3);//issue fix : Autofill doesn�t navigate automatically up to the print page (end)
									
								// Get the rows which change always as and when users are added
								List<WebElement> allUsers = elements.findElements(By.xpath(".//tbody/tr/td"));
		
								// Loop through each row of users table
								//Take the trans number to judge this page completed loaded
								for(WebElement user : allUsers) {
	
								  	cnt++;
										tdValue = user.getText();
										if(cnt == 2){
											int value = Integer.parseInt(tdValue);
											if(value > 0)
											{
												return true;
											
											}
										}
									}
								}*/
						}
							 return true;
							
					}catch(Exception e){
						logger.error("singlePaymentTransactions >>", e);
					}
					return false;
		          }
		        };

		       result = driverWait.until(e);
			
		}catch(Exception e){
			logger.error("singlePaymentTransactions >>", e);
		}
		return result;
	}
	
	public void SinglePaymentAfterPaymentPost(WebDriver driver, String applicantID, String medicalFees){
		
		try{
			String feesName = "";
			String feesValue = "";
			String keyValue = "";
			PaymentDetails paymentDetails = null;
			HashMap< String ,PaymentDetails> paymentelements = new HashMap< String,PaymentDetails> ();
			HashMap< String, String > duplicateElements = new HashMap< String, String> ();
			
			
			List<WebElement> elementsRoot = driver.findElements(By.xpath("//div[@class='form-group']"));
			System.out.println(elementsRoot.size());
			
			if(elementsRoot !=null && elementsRoot.size() > 0){
			
				for(int i = 0; i < elementsRoot.size(); i++) {
				    feesName= elementsRoot.get(i).findElement(By.xpath("./label")).getText().trim();
				    feesValue= elementsRoot.get(i).findElement(By.xpath("./div")).getText().trim();
				    System.out.println(feesName+"="+feesValue);
				    
				    if(feesName.equals("Transaction Number")){
						
						if(duplicateElements.containsValue(feesValue)){
							break;
						}else{
							paymentDetails = new PaymentDetails();
						}
					}
				    
				    if(feesName.equals("Transaction Number")){
						paymentDetails.setTransactionNumber(feesValue);
					}else if(feesName.equals("Application Number")){
						paymentDetails.setApplicationNumber(feesValue);
					}else if(feesName.equals("Transaction Type")){
						paymentDetails.setTransactionType(feesValue);
						keyValue = feesValue;
					}else if(feesName.equals("Total Amount")){
						paymentDetails.setTotalAmount(feesValue);
					}else if(feesName.equals("Receipt Number")){
						paymentDetails.setReceiptNumber(feesValue);
						paymentelements.put(keyValue,paymentDetails);
					}
					
					duplicateElements.put(feesName, feesValue);
				}
			}
					
		 setTransEnajNo(applicantID, paymentelements);
			
		}catch (Exception e){
			
		}
	}
	
	public void setFireboxDriver(WebDriver driver)
	{
		fireboxDriver = driver;
	}
	private void setTransEnajNo(String applicationID, HashMap< String ,PaymentDetails> paymentDetails)
	{
		try{
			String jscript = "";
			if(fireboxDriver != null && paymentDetails != null){
				PaymentDetails applicationPayment = paymentDetails.get("Application Fee");
				PaymentDetails visaFees = paymentDetails.get("Visa Fee");
				PaymentDetails medicalFees = paymentDetails.get("Medical Certificates");
				PaymentDetails medicalInsuranceFee = paymentDetails.get("Medical Insurance Fee");
				PaymentDetails medicalInsuranceServiceFee = paymentDetails.get("Medical Insurance Service Fee");
				
				JavascriptExecutor javascript = (JavascriptExecutor) fireboxDriver;
				//For Application Fee
				if(applicationPayment != null){
					eNumber = "E"+applicationPayment.getApplicationNumber();
					jscript = "$('#data_enjazname_"+applicationID+"').parent().parent().find('td:nth-child(2) input').val('E"+applicationPayment.getApplicationNumber()+"')";
					javascript.executeScript(jscript);
					jscript = "$('#data_enjazname_"+applicationID+"').parent().parent().find('td:nth-child(4) input').val('"+applicationPayment.getTransactionNumber()+"')";
					javascript.executeScript(jscript);
				}
				
				//For visa Fee
				if(visaFees != null){
	
					jscript = "$('#data_enjazname_"+applicationID+"').parent().parent().find('td:nth-child(3) input').val('"+visaFees.getTransactionNumber()+"')";
					javascript.executeScript(jscript);
				}
				
				//For Medical Fee
				if(medicalFees != null){
					jscript = "$('#data_enjazname_"+applicationID+"').parent().parent().find('td:nth-child(5) input').val('"+medicalFees.getTransactionNumber()+"')";
					javascript.executeScript(jscript);
				}
				
				//For Medical Insurance Fee
				if(medicalInsuranceFee !=null){
					
					jscript = "$('#data_enjazname_"+applicationID+"').parent().parent().find('td:nth-child(7) input').val('"+medicalInsuranceFee.getTransactionNumber()+"')";
					javascript.executeScript(jscript);
				}
				
				//For Medical Insurance Service Fee
				if(medicalInsuranceServiceFee !=null){
					
					jscript = "$('#data_enjazname_"+applicationID+"').parent().parent().find('td:nth-child(9) input').val('"+medicalInsuranceServiceFee.getTransactionNumber()+"')";
					javascript.executeScript(jscript);
				}
			
			}
			
			
		}catch(Exception e){
			logger.error("setTransEnajNo >>", e);
		}
	}
	
	/////Print Functionality//////////////////////////////////////////////////////////
	public void printFunctionlaity(WebDriver driver, String printLang){
		try{
			logger.info("Start printFunctionlaity");
//			WebElement divElement = driver.findElement(By.cssSelector(".N-menu"));
//			WebElement loadHomeMainElement = divElement.findElement(By.cssSelector("li:nth-child(8) a"));
//			String loadHomeMainURL = loadHomeMainElement.getAttribute("href");
//			logger.info("printFunctionlaity:"+ loadHomeMainURL);
			SettingsVO settingConfig = settingsDetails.readConfig();
			if(null != settingConfig.getEnjazURL() && !settingConfig.getEnjazURL().equals("")){
				settingConfig.setMainPage(settingConfig.getEnjazURL()+"/Enjaz/Main");
			} else {
				settingConfig.setMainPage(propertyReader.getMainPage());
			}
			//Issue fix : Print button is not working. Normalizing the uri (Start)
			URI uri = new URI(settingConfig.getMainPage());
			settingConfig.setMainPage(uri.normalize().toString());
			//end
			
			String loadHomeMainURL = settingConfig.getMainPage();
			driver.get(loadHomeMainURL);
			
			pageLangSwitch(driver,printLang);
			
			String currentUrl = driver.getCurrentUrl();
			
			if (!currentUrl.equalsIgnoreCase(loadHomeMainURL)){
				driver.get(loadHomeMainURL);
				currentUrl = driver.getCurrentUrl();
			}
			
//			WebElement langElement = driver.findElement(By.csssSelector(".langswitch"));
//			String pangLangURL = langElement.getAttribute("href");
//			driver.get(pangLangURL);
			if(eNumber != null){ 
				String jscript = "var obj = document.getElementById('AppNo'); obj.value = '"+eNumber+"'";
				JavascriptExecutor javascript = (JavascriptExecutor) driver;
				javascript.executeScript(jscript);		
				jscript = "var obj = document.getElementById('PrintApplication'); obj.click()";
				javascript.executeScript(jscript);
			
					while(true){
						try{
						    Thread.sleep(10);
							jscript = "var obj = document.getElementById('printPage'); obj.click()";
							javascript.executeScript(jscript);
							break;
						}catch(Exception e){
							Thread.sleep(20);
							
						}
					}
				
						
			}
		
			
		//	String jscript = "$('.langswitch').click(function(){ window.location.href = $('.langswitch').attr('href')}).trigger('click')";
			
		}catch(Exception e){
			logger.error("printFunctionlaity >> ",e);
			
		}
	}
	
	public void pageLangSwitch(WebDriver driver, String printLang) {
			
		try{
			
			logger.info("Switch page language");
			
			/*String jscript = "var curLanguage = $('.language').html();if (curLanguage == 'English') {window.location = $('.language').attr('href')}";
			JavascriptExecutor javascript = (JavascriptExecutor) driver;
			javascript.executeScript(jscript);*/
			
			WebElement linkElement = driver.findElement(By.xpath("//a[contains(@class,'language')]"));
			if (linkElement != null) {
				String linkText = linkElement.getText();
				if (linkText.equalsIgnoreCase("English")) {  // current page is arabic
					if(printLang.equals("English")){
						JavascriptExecutor page_javascript = (JavascriptExecutor) driver;					
						 String pagescript = "var curLanguage = $('.language').html();if (curLanguage == 'English') {window.location = $('.language').attr('href')}";
						
						page_javascript.executeScript(pagescript);
					
						//driver.findElement(By.linkText("English")).click();
					}
					//driver.findElement(By.linkText("English")).sendKeys(Keys.ENTER);
				}else{ // current page is english
					if(printLang.equals("Arabic")){
						String jscript = "$('.language').click(function(){ window.location.href = $('.language').attr('href')}).trigger('click')";
						JavascriptExecutor javascript = (JavascriptExecutor) driver;
						javascript.executeScript(jscript);		
						
						}
				}
			}
		}
		catch(Exception langexception){
			
			logger.error("Page lang Switch failed",langexception);
			
		}
	}
	
	public void printFunctionalityEnjaz(WebDriver driver, String printLang, String appNumber, String passportNumber){
		try{
			logger.info("Start printFunctionalityEnjaz");
			
			//to click the Print Application link
			driver.findElement(By.linkText("Print Application")).click();
			String currentUrl = driver.getCurrentUrl();
			pageLangSwitch(driver,printLang);
			driver.get(currentUrl);
				
			if(appNumber != null && passportNumber !=null){ 
				//to fill the Application Number text box
				String jscript = "var obj = document.getElementById('AppNo'); obj.value = '"+appNumber+"'";
				JavascriptExecutor javascript = (JavascriptExecutor) driver;
				javascript.executeScript(jscript);
				
				//to fill the Passport Number text box
				jscript = "var obj = document.getElementById('PassportNo'); obj.value = '"+passportNumber+"'";
				javascript.executeScript(jscript);
				
				// Move curser to captcha text box 
				new Actions(driver).moveToElement(driver.findElement(By.id("Captcha"))).click().perform();
				
				
				//wait the driver until the Image code is entered
				WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofMinutes(2));

		        driverWait.until(ExpectedConditions.elementToBeClickable(By.id("printPage")));
		        
		        System.out.println("Print Page : "+driver.findElement(By.id("printPage")).getAttribute("value"));
		
				while(true){
					try{
					    Thread.sleep(10);
						jscript = "var obj = document.getElementById('printPage'); obj.click()";
						javascript.executeScript(jscript);
						break;
					}catch(Exception ex){
						Thread.sleep(20);
						
					}
				}
			}
		}catch(Exception e){
			logger.error("printFunctionalityEnjaz >> ",e);
		}
	}
		 
	/////////////////////////////////////////////////////////////////////////////////
 	
	/*private void fillTextFields(WebDriver driver, String keyName,
			SettingsVO settingVO, String contentStr, WebElement mapElement) {
		mapElement.sendKeys(Keys.chord(Keys.CONTROL, ""), contentStr);
		if ((keyName).equalsIgnoreCase("SPONSER_NAME")) {
			try {
				driver.findElement(By.id("SPONSER_NAME")).sendKeys(
						Keys.chord(Keys.CONTROL, ""),
						settingVO.getSponser_name());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ((keyName).equalsIgnoreCase("SPONSER_NUMBER")) {
			try {
				driver.findElement(By.id("SPONSER_NUMBER"))
						.sendKeys(Keys.chord(Keys.CONTROL, ""),
								settingVO.getSponser_id().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ((keyName).equalsIgnoreCase("SPONSER_ADDRESS")) {
			try {

				driver.findElement(By.id("SPONSER_ADDRESS")).sendKeys(
						Keys.chord(Keys.CONTROL, ""),
						settingVO.getSponser_address());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ((keyName).equalsIgnoreCase("SPONSER_PHONE")) {
			result = settingVO.getSponser_phone();
			try {
				driver.findElement(By.id("SPONSER_PHONE")).sendKeys(
						Keys.chord(Keys.CONTROL, ""),
						settingVO.getSponser_phone().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if ((keyName).equalsIgnoreCase("JOB_OR_RELATION")) {
			try {
				driver.findElement(By.id("JOB_OR_RELATION"))
						.sendKeys(Keys.chord(Keys.CONTROL, ""),
								settingVO.getOccupation());
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if ((keyName).equalsIgnoreCase("AFAMILY")) {
			try {
				driver.findElement(By.id("AFAMILY")).sendKeys(
						Keys.chord(Keys.CONTROL, ""), settingVO.getaFamily());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ((keyName).equalsIgnoreCase("AGRAND")) {
			try {
				driver.findElement(By.id("AGRAND")).sendKeys(
						Keys.chord(Keys.CONTROL, ""), settingVO.getaGrand());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ((keyName).equalsIgnoreCase("AFATHER")) {
			try {
				driver.findElement(By.id("AFATHER")).sendKeys(
						Keys.chord(Keys.CONTROL, ""), settingVO.getaFather());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ((keyName).equalsIgnoreCase("AFIRSTNAME")) {
			try {
				driver.findElement(By.id("AFIRSTNAME"))
						.sendKeys(Keys.chord(Keys.CONTROL, ""),
								settingVO.getaFirstName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ((keyName).equalsIgnoreCase("MAHRAM_NAME")) {
			try {
				driver.findElement(By.id("MAHRAM_NAME")).sendKeys(
						Keys.chord(Keys.CONTROL, ""),
						settingVO.getMahram_name());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ((keyName).equalsIgnoreCase("MAHRAM_RELATION")) {
			try {
				driver.findElement(By.id("MAHRAM_RELATION")).sendKeys(
						Keys.chord(Keys.CONTROL, ""),
						settingVO.getMahram_relation());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ((keyName).equalsIgnoreCase("porpose")) {
			try {
				driver.findElement(By.id("car_number"))
						.sendKeys(Keys.chord(Keys.CONTROL, ""),
								settingVO.getCar_number());
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				driver.findElement(By.id("porpose")).sendKeys(
						Keys.chord(Keys.CONTROL, ""), settingVO.getPurpose());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

			mapElement.sendKeys(Keys.chord(Keys.CONTROL, ""), contentStr);
		}
	}*/
	
	public void afterSaveProcessNewPayment(WebDriver driver, SettingsVO settingConfig, String applicationNumber, String passportNumber, HashMap<String, PaymentDetails> paymentElements) {
		
		try {
		Applicantdetails appDetails = applicantDetails.get(driver.getWindowHandle());
		System.out.println("Applicant Id: "+appDetails.getApplicantId());
		logger.error("AfterSaveProcessNewPayment >> Applicant Id: "+appDetails.getApplicantId());
		
		//Call payment done service url
		String opsysEndPoint = settingsDetails.readConfig().getOpsys_url();
		String paymentDoneURL =  opsysEndPoint +"/"+propertyReader.getPaymentDoneURL();
		
		URI	uri = new URI(paymentDoneURL);
		
		paymentDoneURL = uri.normalize().toString();
		AsyncExecutor asyncExecutor = new AsyncExecutor();
		asyncExecutor.paymentDone(paymentDoneURL, appDetails.getApplicantId());
		
		//to fill the enjaz transaction details to opsys page
		fillTransactionEnjazDetails(appDetails.getApplicantId(), paymentElements);
		
		//print functionality to print the application for an particular applicant
		printFunctionalityEnjaz(driver, settingConfig.getPrintLang(), applicationNumber, passportNumber);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	//method to submit applicant details in opsys page
	private void fillTransactionEnjazDetails(String applicationID, HashMap< String ,PaymentDetails> paymentDetails){
		try{
			String jscript = "";
			if(fireboxDriver != null && paymentDetails != null){
				PaymentDetails applicationPayment = paymentDetails.get("Applications");
				PaymentDetails medicalInsuranceServiceFee = paymentDetails.get("Medical Insurance Service Fee");
				PaymentDetails medicalInsuranceFee = paymentDetails.get("Medical Insurance Fee");
				PaymentDetails visaFee = paymentDetails.get("Visa Fee");
				
				JavascriptExecutor javascript = (JavascriptExecutor) fireboxDriver;
				if(applicationPayment != null){
					eNumber = "E"+applicationPayment.getApplicationNumber();
					jscript = "$('#data_enjazname_"+applicationID+"').parent().parent().find('td:nth-child(2) input').val('"+applicationPayment.getApplicationNumber()+"')";
					javascript.executeScript(jscript);
					jscript = "$('#data_enjazname_"+applicationID+"').parent().parent().find('td:nth-child(4) input').val('"+applicationPayment.getTransactionNumber()+"')";
					javascript.executeScript(jscript);
				}
				if(visaFee != null){
	
				jscript = "$('#data_enjazname_"+applicationID+"').parent().parent().find('td:nth-child(3) input').val('"+visaFee.getTransactionNumber()+"')";
				javascript.executeScript(jscript);
				}
				if(medicalInsuranceFee != null){
					jscript = "$('#data_enjazname_"+applicationID+"').parent().parent().find('td:nth-child(5) input').val('"+medicalInsuranceFee.getTransactionNumber()+"')";
					javascript.executeScript(jscript);
				}
			
			}
			
			//clearing the object after all the process
			paymentDetails.clear();
			
		}catch(Exception e){
			logger.error("fillTransactionEnjazDetails >>", e);
		}
	}
	public void populateInsuranceDetails(WebDriver driver, String insurance, String amount, String applicantId) {
		
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		String jscript = "";
		//for text box
		//jscript = "$('#data_enjazname_"+applicantId+"').parent().parent().find('td:nth-child(10) input').val('"+insurance.replace("'", " ")+"')";
		//javascript.executeScript(jscript);
		//for drop down
		jscript = "$('#data_enjazname_"+applicantId+"').parent().parent().find('td:nth-child(10) select').val('"+insurance.replace("'", " ").trim()+"')";
		javascript.executeScript(jscript);
		jscript = "$('#data_enjazname_"+applicantId+"').parent().parent().find('td:nth-child(6) input').val('"+amount+"')";
		javascript.executeScript(jscript);
		
	}
	
	
	public void fillENumber(WebDriver driver, String enumber) {
		System.out.println("enumber"+enumber);
		Applicantdetails appDetails = applicantDetails.get(driver.getWindowHandle());
		String applicantId = appDetails.getApplicantId();
		System.out.println("Applicant Id: "+applicantId);
		System.out.println("$('#data_enjazname_"+applicantId+"').parent().parent().find('td:nth-child(2) input').val('E"+enumber+"')");
		//to fill the enumber in opsys page
		if(fireboxDriver !=null){
			JavascriptExecutor javascript = (JavascriptExecutor) fireboxDriver;
			String jscript = "$('#data_enjazname_"+applicantId+"').parent().parent().find('td:nth-child(2) input').val('E"+enumber+"')";
			javascript.executeScript(jscript);
			 
		}
		
		//to fill email id and contact number in insurance page
		if(driver !=null){
			JavascriptExecutor javascript = (JavascriptExecutor) driver;
			
			//to fill email id in email text box
			String jscript = "var obj = document.getElementById('Email'); obj.value = '"+appDetails.getEmail()+"'";
			//String jscript = "var obj = document.getElementById('Email'); obj.value = 'test@gmail.com'";
			javascript.executeScript(jscript);
			
			//to fill Mobile number in Mobile Number text box
			jscript = "var obj = document.getElementById('Mobile'); obj.value = '"+appDetails.getContactNo()+"'";
			//jscript = "var obj = document.getElementById('Mobile'); obj.value = '1234567890'";
			javascript.executeScript(jscript);
			readMofaDetails.mofaPageLangSwitch(driver);
			
		}
		
	}
	
	private LinkedHashMap<String, String> getFeeDetails(String applicantId){
		
		OpsysApplicantParser applicantObj = new OpsysApplicantParser();
		String opsysEndPoint = settingsDetails.readConfig().getOpsys_url();
		if (opsysEndPoint.trim().endsWith("/")) {
			opsysEndPoint = opsysEndPoint
					+ propertyReader.getLoadEnjazDetails();
		} else {
			opsysEndPoint = opsysEndPoint + "/"
					+ propertyReader.getLoadEnjazDetails();
		}
		OpsysApplicantBean opsysApplicantBean = applicantObj.getOpsysApplicantDetails(opsysEndPoint,applicantId);
		LinkedHashMap<String, String> propMap = opsysApplicantBean.getElementMap();
		
		return propMap;
		
	}
	
	
	//After save process for visa types applicable for insurance
	public void AfterSaveProcessForInsurance(WebDriver driver, SettingsVO settingVO){
		
		String printLanguage = settingVO.getPrintLang();
		String enjazPayPassword = settingVO.getEnzPayPassword();
		String serviceEnjazPayPass = settingVO.getServiceEnjazPaymentPassword();
		if(null!=serviceEnjazPayPass&&!serviceEnjazPayPass.isEmpty())
			enjazPayPassword=serviceEnjazPayPass;
		
		String applicationNo = "";
		
		applicationNo = settingVO.getE_number();
		
		if(applicationNo.contains("E")){
			applicationNo = applicationNo.replaceAll("E", "");
		}
		
		//readMofaDetails.pageLangSwitch(driver);
		payVisaFees(driver,applicationNo,settingVO.getPassport_no());
		
		//call loadEnjazNew service to get visa fee starts here
		LinkedHashMap<String, String> propMap = getFeeDetails(settingVO.getApplicant_id());
		@SuppressWarnings("rawtypes")
		Iterator properties = propMap.entrySet().iterator();
		
		String visaFee = "0";
		String enjazFee = "0";
		String medicalFee = "0";
		
		while (properties.hasNext()) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			Map.Entry<String, String> mapEntry = (Map.Entry) properties.next();
			String keyName = mapEntry.getKey().trim();	
		
			String contentStr = mapEntry.getValue();
			if(keyName.equals("ENJAZ_FEES"))
			{
				enjazFee = contentStr;
				//appDetails.setEnjazFee(enjazFees);
				//continue;
				
			}if(keyName.equals("VISA_FEES"))
			{
				visaFee = contentStr;
				//appDetails.setVisaFee(visaFees);
				//continue;
			}
			if(keyName.equals("MEDICAL_FEES"))
			{
				medicalFee = contentStr;
				//appDetails.setMedicalFee(medicalFees);
				//continue;
			}
		}
		
		//get visa fee ends here
		if(singlePayment(driver, visaFee, enjazFee, medicalFee)){
			
			//password should be getting form settings
			if(singlePaymentTransactions(driver,enjazPayPassword,settingVO.getApplicant_id())){ 
				SinglePaymentAfterPaymentPost(driver,settingVO.getApplicant_id(),medicalFee);
				printFunctionlaity(driver, printLanguage);
			}
		}
	}
	
	
	
}
