package com.enjaz.webautofill.valueobject;

import java.io.Serializable;

public class SettingsVO implements Serializable {
	private static final long serialVersionUID = -4839686265691362595L;
	private Integer settings_id;
	private String opsys_url;
	private String enj_user_name;
	private String enj_password;
	private String invitation_number;
	private String sponser_id;
	private String car_number;
	private String occupation;
	private String purpose;
	private String sponser_name;
	private String sponser_address;
	private String sponser_phone;
	private String applicant_id;
	private String siteType;
	private String letterNo;
	private String letterType;
	private String ezjazAck;
	private String aFirstName;
	private String aFather;
	private String aGrand;
	private String aFamily;
	/*private String mahram_name;
	private String mahram_relation;*/
	private String noOfEntry;
	private String enzPayPassword;
	private String printLang;
	private String isFetchArabicNameRequired;
	private String mofaURL;
	private String enjazURL;
	private String visa_url;
	private String mainPage;
	private String login_url;
	private String visitName;
	private String enjazUserName;
	private String enjazPassword;
	private String enjazPaymentPassword;
	private String serviceEnjazPaymentPassword;
	private String e_number;
	private String passport_no;
	// added for eliminating language selection url redirect
	private String redirectUrl;
	private String mofaLang;
	private String launchOpsys;
	
	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	public String getVisa_url() {
		return visa_url;
	}

	public void setVisa_url(String visa_url) {
		this.visa_url = visa_url;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getLogin_url() {
		return login_url;
	}

	public void setLogin_url(String login_url) {
		this.login_url = login_url;
	}

	public String getMofaURL() {
		return mofaURL;
	}

	public void setMofaURL(String mofaURL) {
		this.mofaURL = mofaURL;
	}

	public String getEnjazURL() {
		return enjazURL;
	}

	public void setEnjazURL(String enjazURL) {
		this.enjazURL = enjazURL;
	}

	public String getEnzPayPassword() {
		return enzPayPassword;
	}

	public void setEnzPayPassword(String enzPayPassword) {
		this.enzPayPassword = enzPayPassword;
	}

	public String getPrintLang() {
		return printLang;
	}

	public void setPrintLang(String printLang) {
		this.printLang = printLang;
	}

	public Integer getSettings_id() {
		return this.settings_id;
	}

	public void setSettings_id(Integer settings_id) {
		this.settings_id = settings_id;
	}

	public String getOpsys_url() {
		return this.opsys_url;
	}

	public void setOpsys_url(String opsys_url) {
		this.opsys_url = opsys_url;
	}

	public String getEnj_user_name() {
		return this.enj_user_name;
	}

	public void setEnj_user_name(String enj_user_name) {
		this.enj_user_name = enj_user_name;
	}

	public String getEnj_password() {
		return this.enj_password;
	}

	public void setEnj_password(String enj_password) {
		this.enj_password = enj_password;
	}

	public String getInvitation_number() {
		return this.invitation_number;
	}

	public void setInvitation_number(String invitation_number) {
		this.invitation_number = invitation_number;
	}

	public String getSponser_id() {
		return this.sponser_id;
	}

	public void setSponser_id(String sponser_id) {
		this.sponser_id = sponser_id;
	}

	public String getApplicant_id() {
		return this.applicant_id;
	}

	public void setApplicant_id(String applicant_id) {
		this.applicant_id = applicant_id;
	}

	/**
	 * @return the siteType
	 */
	public String getSiteType() {
		return siteType;
	}

	/**
	 * @param siteType
	 *            the siteType to set
	 */
	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	/**
	 * @return the sponser_name
	 */
	public String getSponser_name() {
		return sponser_name;
	}

	/**
	 * @param sponser_name
	 *            the sponser_name to set
	 */
	public void setSponser_name(String sponser_name) {
		this.sponser_name = sponser_name;
	}

	/**
	 * @return the sponser_address
	 */
	public String getSponser_address() {
		return sponser_address;
	}

	/**
	 * @param sponser_address
	 *            the sponser_address to set
	 */
	public void setSponser_address(String sponser_address) {
		this.sponser_address = sponser_address;
	}

	/**
	 * @return the sponser_phone
	 */
	public String getSponser_phone() {
		return sponser_phone;
	}

	/**
	 * @param sponser_phone
	 *            the sponser_phone to set
	 */
	public void setSponser_phone(String sponser_phone) {
		this.sponser_phone = sponser_phone;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getLetterNo() {
		return letterNo;
	}

	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}

	public String getEzjazAck() {
		return ezjazAck;
	}

	public void setEzjazAck(String ezjazAck) {
		this.ezjazAck = ezjazAck;
	}

	public String getLetterType() {
		return letterType;
	}

	public void setLetterType(String letterType) {
		this.letterType = letterType;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getaFirstName() {
		return aFirstName;
	}

	public void setaFirstName(String aFirstName) {
		this.aFirstName = aFirstName;
	}

	public String getaFather() {
		return aFather;
	}

	public void setaFather(String aFather) {
		this.aFather = aFather;
	}

	public String getaGrand() {
		return aGrand;
	}

	public void setaGrand(String aGrand) {
		this.aGrand = aGrand;
	}

	public String getaFamily() {
		return aFamily;
	}

	public void setaFamily(String aFamily) {
		this.aFamily = aFamily;
	}

	public String getCar_number() {
		return car_number;
	}

	public void setCar_number(String car_number) {
		this.car_number = car_number;
	}

	/*public String getMahram_name() {
		return mahram_name;
	}

	public void setMahram_name(String mahram_name) {
		this.mahram_name = mahram_name;
	}

	public String getMahram_relation() {
		return mahram_relation;
	}

	public void setMahram_relation(String mahram_relation) {
		this.mahram_relation = mahram_relation;
	}*/

	public String getNoOfEntry() {
		return noOfEntry;
	}

	public void setNoOfEntry(String noOfEntry) {
		this.noOfEntry = noOfEntry;
	}
	
	public String getIsFetchArabicNameRequired() {
		return isFetchArabicNameRequired;
	}

	public void setIsFetchArabicNameRequired(String isFetchArabicNameRequired) {
		this.isFetchArabicNameRequired = isFetchArabicNameRequired;
	}

	public String getEnjazUserName() {
		return enjazUserName;
	}

	public void setEnjazUserName(String enjazUserName) {
		this.enjazUserName = enjazUserName;
	}

	public String getEnjazPassword() {
		return enjazPassword;
	}

	public void setEnjazPassword(String enjazPassword) {
		this.enjazPassword = enjazPassword;
	}

	public String getEnjazPaymentPassword() {
		return enjazPaymentPassword;
	}

	public void setEnjazPaymentPassword(String enjazPaymentPassword) {
		this.enjazPaymentPassword = enjazPaymentPassword;
	}

	public String getServiceEnjazPaymentPassword() {
		return serviceEnjazPaymentPassword;
	}

	public void setServiceEnjazPaymentPassword(String serviceEnjazPaymentPassword) {
		this.serviceEnjazPaymentPassword = serviceEnjazPaymentPassword;
	}

	public String getE_number() {
		return e_number;
	}

	public void setE_number(String e_number) {
		this.e_number = e_number;
	}

	public String getPassport_no() {
		return passport_no;
	}

	public void setPassport_no(String passport_no) {
		this.passport_no = passport_no;
	}

	public String getMofaLang() {
		return mofaLang;
	}

	public void setMofaLang(String mofaLang) {
		this.mofaLang = mofaLang;
	}
	public String getLaunchOpsys() {
		return launchOpsys;
		
	}
	public void setLaunchOpsys(String launchOpsys) {
		this.launchOpsys = launchOpsys;
		
	}
}
