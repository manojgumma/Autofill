package com.enjaz.webautofill.valueobject;

import java.io.Serializable;

public class InvitationVO
  implements Serializable
{
  private static final long serialVersionUID = -307483479718128893L;
  private String employerID;
  private String sponsorName;
  private String sponsorID;
  private String invitationNumber;
  private String searchType;
  private String sponsorPhNumber;
  private String sponsorAddress;
  private String applicantID;
  private String letterNo;
  
  public String getEmployerID()
  {
    return this.employerID;
  }
  
  public void setEmployerID(String employerID)
  {
    this.employerID = employerID;
  }
  
  public String getSponsorName()
  {
    return this.sponsorName;
  }
  
  public void setSponsorName(String sponsorName)
  {
    this.sponsorName = sponsorName;
  }
  
  public String getSearchType()
  {
    return this.searchType;
  }
  
  public void setSearchType(String searchType)
  {
    this.searchType = searchType;
  }
  
  public String getInvitationNumber()
  {
    return this.invitationNumber;
  }
  
  public void setInvitationNumber(String invitationNumber)
  {
    this.invitationNumber = invitationNumber;
  }
  
  public String getSponsorPhNumber()
  {
    return this.sponsorPhNumber;
  }
  
  public void setSponsorPhNumber(String sponsorPhNumber)
  {
    this.sponsorPhNumber = sponsorPhNumber;
  }
  
  public String getSponsorAddress()
  {
    return this.sponsorAddress;
  }
  
  public void setSponsorAddress(String sponsorAddress)
  {
    this.sponsorAddress = sponsorAddress;
  }
  
  public String getApplicantID()
  {
    return this.applicantID;
  }
  
  public void setApplicantID(String applicantID)
  {
    this.applicantID = applicantID;
  }

public String getSponsorID() {
	return sponsorID;
}

public void setSponsorID(String sponsorID) {
	this.sponsorID = sponsorID;
}

public String getLetterNo() {
	return letterNo;
}

public void setLetterNo(String letterNo) {
	this.letterNo = letterNo;
}
}
