package com.enjaz.webautofill.util;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class OpsysApplicantBean
  implements Serializable
{
  private static final long serialVersionUID = -4474010617480186109L;
  private String opsysURL;
  private String applicantId;
  // private Map<String, String> elementMap;
  private LinkedHashMap<String, String> elementMap;
  private Integer statusCode;
  private String statusMessage;
  
  public String getOpsysURL()
  {
    return this.opsysURL;
  }
  
  public void setOpsysURL(String opsysURL)
  {
    this.opsysURL = opsysURL;
  }
  
  public String getApplicantId()
  {
    return this.applicantId;
  }
  
  public void setApplicantId(String applicantId)
  {
    this.applicantId = applicantId;
  }
  
  public Integer getStatusCode()
  {
    return this.statusCode;
  }
  
  public void setStatusCode(Integer statusCode)
  {
    this.statusCode = statusCode;
  }
  
  public String getStatusMessage()
  {
    return this.statusMessage;
  }
  
  public void setStatusMessage(String statusMessage)
  {
    this.statusMessage = statusMessage;
  }

public LinkedHashMap<String, String> getElementMap() {
	return elementMap;
}

public void setElementMap(LinkedHashMap<String, String> elementMap) {
	this.elementMap = elementMap;
}
}
