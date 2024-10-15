package com.enjaz.webautofill.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Util {
	public static boolean pingUrl(final String address) {
		 try {
		  final URL url = new URL(address);
		  final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		  urlConn.setConnectTimeout(1000 * 10); // mTimeout is in seconds
		  final long startTime = System.currentTimeMillis();
		  urlConn.connect();
		  final long endTime = System.currentTimeMillis();
		  if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
		   System.out.println("Time (ms) : " + (endTime - startTime));
		   System.out.println("Ping to "+address +" was success");
		   return true;
		  }
		 } catch (final MalformedURLException e1) {
		  e1.printStackTrace();
		 } catch (final IOException e) {
		  e.printStackTrace();
		 }
		 return false;
		}
	
	 public static boolean isProbablyArabic(String s) {
		    for (int i = 0; i < s.length();) {
		        int c = s.codePointAt(i);
		        if (c >= 0x0600 && c <=0x06E0)
		            return true;
		        i += Character.charCount(c);            
		    }
		    return false;
		  }
	// convert from UTF-8 -> internal Java String format
		public static String convertFromUTF8(String s) {
			String out = null;
			try {
				out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
			} catch (java.io.UnsupportedEncodingException e) {
				return null;
			}
			return out;
		}
		
		// convert from internal Java String format -> UTF-8
		public static String convertToUTF8(String s) {
			String out = null;
			try {
				out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
			} catch (java.io.UnsupportedEncodingException e) {
				return null;
			}
			return out;
		}
	

}
