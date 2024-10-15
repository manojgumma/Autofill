package com.enjaz.webautofill.util;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.security.cert.X509Certificate;
import com.google.gson.Gson;

@Service
public class OpsysApplicantParser {
	private static final Logger logger = LoggerFactory
			.getLogger(OpsysApplicantParser.class);
  @SuppressWarnings("unchecked")
public static void main(String[] args)
    throws IOException
  {
    OpsysApplicantParser applicantParser = new OpsysApplicantParser();

    
    OpsysApplicantBean test = applicantParser.getOpsysApplicantDetails("http://localhost:8080/OpSys/opsys/main/loadEnjazNew", "3125088");

    System.out.println("OpsysApplicantBean " + new Gson().toJson(test));
    
    LinkedHashMap<String, String> propMap = test.getElementMap();
	@SuppressWarnings("rawtypes")
	Iterator properties = propMap.entrySet().iterator();
	
	String visaFee = "0";
	String enjazFee = "0";
	String medicalFee = "0";
	
	while (properties.hasNext()) {
		Map.Entry<String, String> mapEntry = (Map.Entry<String, String>) properties.next();
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
	
	System.out.println(visaFee+" : "+enjazFee+" : "+medicalFee);
  }
  
  public OpsysApplicantBean getOpsysApplicantDetails(String url, String applicantId)
  {
	  System.out.println(url +"=="+applicantId);
    OpsysApplicantBean applicantBean = new OpsysApplicantBean();
    applicantBean.setOpsysURL(url);
    applicantBean.setApplicantId(applicantId);
    try
    {
    	trustAllCertificates();
      Document opsysDocument = Jsoup.connect(url)
        .header("Content-Type", "application/x-www-form-urlencoded")
        .data("applicantId", applicantId)
        .timeout(60000)
        .post();
      
      String title = opsysDocument.title();
      System.out.println("title : " + title);

      Elements links = opsysDocument.select("h1");
      
//      logger.info("getOpsysApplicantDetails >> " + links.text());
  //    System.out.println(links.text());
      
    //  System.out.println(links.size());
      
      if (links.size() > 0)
      {
        //Map<String, String> elementMap = new HashMap();
    	  LinkedHashMap<String, String> elementMap = new LinkedHashMap<String, String>();
        for (Element link : links)
        {
          //System.out.println("text : " + link.text());
          String[] pageElements = stringParseByDelimiter(link.text(), "&&");
          for (String element : pageElements)
          {
            String[] elementKeyValue = stringParseByDelimiter(element, "=");
            String keyVal = "";
            if(elementKeyValue.length >= 2){
            	keyVal = elementKeyValue[1];
            }else{
            	keyVal = " ";
            }
            //elementMap.put(elementKeyValue[0], elementKeyValue[1]);
            if(!elementKeyValue[0].contains("MAHRAM")) {
            elementMap.put(elementKeyValue[0], keyVal);
            }
          }
          applicantBean.setElementMap(elementMap);
        }
      }
    }
    catch (HttpStatusException e)
    {
      System.out.println(e.getMessage());
      System.out.println(e.getStatusCode());
      if (e.getStatusCode() == 400)
      {
        applicantBean.setStatusCode(Integer.valueOf(e.getStatusCode()));
        applicantBean.setStatusMessage("Invalid Opsys applicant id");
      }
      else if (e.getStatusCode() == 404)
      {
        applicantBean.setStatusCode(Integer.valueOf(e.getStatusCode()));
        applicantBean.setStatusMessage("Something went wrong. Opsys for enjaz URL properly configured?");
        e.printStackTrace();
      }
      else if (e.getStatusCode() == 500)
      {
        applicantBean.setStatusCode(Integer.valueOf(e.getStatusCode()));
        applicantBean.setStatusMessage("Something went wrong with the given applicant id");
        e.printStackTrace();
      }
    }
    catch (IOException e)
    {
      applicantBean.setStatusCode(Integer.valueOf(500));
      applicantBean.setStatusMessage("Something went wrong!! ");
      e.printStackTrace();
    }
    catch (Exception e)
    {
      applicantBean.setStatusCode(Integer.valueOf(500));
      applicantBean.setStatusMessage("Something went wrong!! ");
      e.printStackTrace();
    }
    return applicantBean;
  }
  
  private String[] stringParseByDelimiter(String inputData, String regex)
  {
    return inputData.split(regex);
  }
  public static void trustAllCertificates() {
	    try {
	        TrustManager[] trustAllCerts = new TrustManager[]{
	            (TrustManager) new X509TrustManager() {
	                public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
	            }
	        };

	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        });
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
  public void setPaymentDoneByAutoFill (String url, String applicantId) throws Exception
  {
	  try
	    {
		  trustAllCertificates();
	      @SuppressWarnings("unused")
		Document opsysDocument = Jsoup.connect(url)
	        .header("Content-Type", "application/x-www-form-urlencoded")
	        .data("applicantId", applicantId)
	        .timeout(60000)
	        .post();	      
	    }catch(Exception e){
	    	throw new Exception(e.getMessage());
	    }
  }
}