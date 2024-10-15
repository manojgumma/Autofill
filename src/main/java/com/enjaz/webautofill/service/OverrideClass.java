package com.enjaz.webautofill.service;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
 
public class OverrideClass implements WebDriverListener{
	public void afterChangeValueOf(WebElement arg0, WebDriver driver) {
		// TODO Auto-generated method stub		
		System.out.println("After ChangeValueOf  "+ driver.getCurrentUrl());
	}
	public void afterClickOn(WebElement arg0, WebDriver driver) {
		// TODO Auto-generated method stub	
		System.out.println("After ClickOn "+ driver.getCurrentUrl());
	}
	public void afterFindBy(By arg0, WebElement arg1, WebDriver driver) {
		// TODO Auto-generated method stub		
		System.out.println("After FindBy"+ driver.getCurrentUrl());
	}
	public void afterNavigateBack(WebDriver driver) {
		System.out.println("After clicking back  "+ driver.getCurrentUrl());		
	}
	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub		
		System.out.println("afterNavigateForward "+ driver.getCurrentUrl());		
	}
	public void afterNavigateTo(String arg0, WebDriver driver) {
		// TODO Auto-generated method stub		
		System.out.println("afterNavigateTo"+ driver.getCurrentUrl());		
	}
	public void afterScript(String arg0, WebDriver driver) {
		// TODO Auto-generated method stub	
		System.out.println("afterScript  "+ driver.getCurrentUrl());		
	}
	public void beforeChangeValueOf(WebElement arg0, WebDriver driver) {
		// TODO Auto-generated method stub
		System.out.println("beforeChangeValueOf "+ driver.getCurrentUrl());		
	}
	public void beforeClickOn(WebElement arg0, WebDriver driver) {
		// TODO Auto-generated method stub	
		System.out.println("BeforeClickOn  "+ driver.getCurrentUrl());		
	}
	public void beforeFindBy(By arg0, WebElement arg1, WebDriver driver) {
		// TODO Auto-generated method stub		
		System.out.println("beforeFindBy  "+ driver.getCurrentUrl());		
	}
	public void beforeNavigateBack(WebDriver driver) {
		System.out.println("Before clicking Back"+driver.getCurrentUrl());		
	}
	public void beforeNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub		
		System.out.println("beforeNavigateForward "+ driver.getCurrentUrl());		
	}
	public void beforeNavigateTo(String arg0, WebDriver driver) {
		// TODO Auto-generated method stub	
		System.out.println("beforeNavigateTo  "+ driver.getCurrentUrl());		
	}
	public void beforeScript(String arg0, WebDriver driver) {
		// TODO Auto-generated method stub		
		System.out.println("beforeScript  "+ driver.getCurrentUrl());		
	}
	public void onException(Throwable arg0, WebDriver driver) {
		// TODO Auto-generated method stub	
		System.out.println("onException  "+ driver.getCurrentUrl());		
	}
	public void beforeAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}
	public void afterAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}
	public void afterAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}
	public void beforeAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}
	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}
	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}
	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}
	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}
	public void beforeSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}
	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}
	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
		// TODO Auto-generated method stub
		
	}
	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
		// TODO Auto-generated method stub
		
	}
	public void beforeGetText(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}
	public void afterGetText(WebElement element, WebDriver driver, String text) {
		// TODO Auto-generated method stub
		
	}
}