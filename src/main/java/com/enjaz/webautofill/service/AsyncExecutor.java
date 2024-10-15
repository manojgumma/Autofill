package com.enjaz.webautofill.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import com.enjaz.webautofill.util.OpsysApplicantParser;

@EnableAsync
public class AsyncExecutor {
	private static final Logger logger = LoggerFactory
			.getLogger(AsyncExecutor.class);
	@Async
	public void paymentDone(String paymentDoneURL, String applicationID ) throws InterruptedException
	{
		try{
		// System.out.println("Execute method asynchronously. " + Thread.currentThread().getName());
		 OpsysApplicantParser serviceCall = new OpsysApplicantParser();
			logger.info("paymentDoneURL >> " + paymentDoneURL +"," + applicationID);
			serviceCall.setPaymentDoneByAutoFill(paymentDoneURL, applicationID);
		 //Thread.sleep(20);
		}catch(Exception e){
			logger.error("paymentDone AsyncExecutor: >>"+ e.getMessage());
		}
	}

}