package com.enjaz.webautofill.controller;

import java.net.URI;
import java.net.URISyntaxException;

public class Test {
	
	
	
	private static String URLNormalize(String url) throws URISyntaxException{
		
		URI uri = new URI(url.replaceAll("Enjaz", ""));
		return uri.normalize().toString();
	}
	
	
	public static void main(String args[]){
		
		String msg = " Payment Successfull ";
		System.out.println(msg);
		System.out.println(msg.trim());
		
		URI uri;
		try {
			uri = new URI("http://10.96.28.20:8080/VFSEtimad/opsys/main/setPaymentDoneByAutoFill");
			String paymentDoneURL = uri.normalize().toString();
			System.out.println(paymentDoneURL);
			
			System.out.println("Done");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}

}
