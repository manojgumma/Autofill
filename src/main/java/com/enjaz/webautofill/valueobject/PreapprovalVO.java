package com.enjaz.webautofill.valueobject;

import java.io.Serializable;

/**
 * 25-05-2015
 * @author Prabakaran
 *
 */
public class PreapprovalVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7585002405990622019L;
	private String occupation;
	private String purpose;
	private String sponser_name;
	private String enjazNoOfEntries;
	private String firstName;
	private String secondName;
	private String otherName;
	private String lastName;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	public String getEnjazNoOfEntries() {
		return enjazNoOfEntries;
	}
	
	public void setEnjazNoOfEntries(String enjazNoOfEntries) {
		this.enjazNoOfEntries = enjazNoOfEntries;
	}
	
	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}
	/**
	 * @param occupation the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}
	/**
	 * @param purpose the purpose to set
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	/**
	 * @return the sponser_name
	 */
	public String getSponser_name() {
		return sponser_name;
	}
	/**
	 * @param sponser_name the sponser_name to set
	 */
	public void setSponser_name(String sponser_name) {
		this.sponser_name = sponser_name;
	}
}
