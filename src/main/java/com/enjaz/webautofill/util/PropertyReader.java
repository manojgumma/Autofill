package com.enjaz.webautofill.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * 17-04-2015
 * @author Prabakaran
 *
 */
@Component
public class PropertyReader {

	@Value("${enjazUName}")
	private String enj_user_name;
	@Value("${enjazPassword}")
	private String enj_password;
	@Value("${OpsysURL}")
	private String opsys_url;
	@Value("${validatePage}")
	private String validate_url;
	@Value("${loginPage}")
	private String login_url;
	@Value("${visaPage}")
	private String visa_url;
	@Value("${searchType}")
	private String searchType;
	@Value("${loadEnjazDetails}")
	private String loadEnjazDetails;
	@Value("${chromeDriver}")
	private String chromeDriver;
	@Value("${configFile}")
	private String configFile;
	@Value("${version}")
	private String version;
	@Value("${mainPage}")
	private String mainPage;
	@Value("${enjazfillingmsg}")
	private String enjazFillingMsg;
	@Value("${updatefileurl}")
	private String updateFileUrl;
	@Value("${paymentDoneURL}")
	private String paymentDoneURL;
	@Value("${readmsg}")
	private String readmsg;
	@Value("${writemsg}")
	private String writemsg;
	@Value("${completMsg}")
	private String completMsg;
	@Value("${payeeFeesURL}")
	private String payeeFeesURL;
	public String getPaymentDoneURL() {
		return paymentDoneURL;
	}

	public void setPaymentDoneURL(String paymentDoneURL) {
		this.paymentDoneURL = paymentDoneURL;
	}

	public String getUpdateFileUrl() {
		return updateFileUrl;
	}

	public void setUpdateFileUrl(String updateFileUrl) {
		this.updateFileUrl = updateFileUrl;
	}

	public String getEnjazFillingMsg() {
		return enjazFillingMsg;
	}

	public void setEnjazFillingMsg(String enjazFillingMsg) {
		this.enjazFillingMsg = enjazFillingMsg;
	}

	public String getEnjazSuccessfulMsg() {
		return enjazSuccessfulMsg;
	}

	public void setEnjazSuccessfulMsg(String enjazSuccessfulMsg) {
		this.enjazSuccessfulMsg = enjazSuccessfulMsg;
	}

	@Value("${enjazsuccessfulmsg}")
	private String enjazSuccessfulMsg;
	
	
	
//	@Value("${printLanguage}")
//	private String printLanguage;
//	
//	public String getPrintLanguage() {
//		return printLanguage;
//	}
//
//	public void setPrintLanguage(String printLanguage) {
//		this.printLanguage = printLanguage;
//	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getEnj_user_name() {
		return enj_user_name;
	}

	public void setEnj_user_name(String enj_user_name) {
		this.enj_user_name = enj_user_name;
	}

	public String getEnj_password() {
		return enj_password;
	}

	public void setEnj_password(String enj_password) {
		this.enj_password = enj_password;
	}

	public String getOpsys_url() {
		return opsys_url;
	}

	public void setOpsys_url(String opsys_url) {
		this.opsys_url = opsys_url;
	}

	public String getValidate_url() {
		return validate_url;
	}

	public void setValidate_url(String validate_url) {
		this.validate_url = validate_url;
	}

	public String getLogin_url() {
		return login_url;
	}

	public void setLogin_url(String login_url) {
		this.login_url = login_url;
	}

	public String getVisa_url() {
		return visa_url;
	}

	public void setVisa_url(String visa_url) {
		this.visa_url = visa_url;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getLoadEnjazDetails() {
		return loadEnjazDetails;
	}

	public void setLoadEnjazDetails(String loadEnjazDetails) {
		this.loadEnjazDetails = loadEnjazDetails;
	}

	/**
	 * @return the chromeDriver
	 */
	public String getChromeDriver() {
		return chromeDriver;
	}

	/**
	 * @param chromeDriver the chromeDriver to set
	 */
	public void setChromeDriver(String chromeDriver) {
		this.chromeDriver = chromeDriver;
	}

	/**
	 * @return the configFile
	 */
	public String getConfigFile() {
		return configFile;
	}

	/**
	 * @param configFile the configFile to set
	 */
	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	/**
	 * @return the readmsg
	 */
	public String getReadmsg() {
		return readmsg;
	}

	/**
	 * @param readmsg the readmsg to set
	 */
	public void setReadmsg(String readmsg) {
		this.readmsg = readmsg;
	}

	/**
	 * @return the writemsg
	 */
	public String getWritemsg() {
		return writemsg;
	}

	/**
	 * @param writemsg the writemsg to set
	 */
	public void setWritemsg(String writemsg) {
		this.writemsg = writemsg;
	}

	/**
	 * @return the completMsg
	 */
	public String getCompletMsg() {
		return completMsg;
	}

	/**
	 * @param completMsg the completMsg to set
	 */
	public void setCompletMsg(String completMsg) {
		this.completMsg = completMsg;
	}

	/**
	 * @return the payeeFeesURL
	 */
	public String getPayeeFeesURL() {
		return payeeFeesURL;
	}

	/**
	 * @param payeeFeesURL the payeeFeesURL to set
	 */
	public void setPayeeFeesURL(String payeeFeesURL) {
		this.payeeFeesURL = payeeFeesURL;
	}
	
	
}
