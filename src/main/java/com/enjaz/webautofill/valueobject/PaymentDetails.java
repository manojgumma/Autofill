package com.enjaz.webautofill.valueobject;

public class PaymentDetails {
	private String transactionNumber;
	public String getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public String getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getReceiptNumber() {
		return receiptNumber;
	}
	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}
	public String getMedicalTransNumber() {
		return medicalTransNumber;
	}
	public void setMedicalTransNumber(String medicalTransNumber) {
		this.medicalTransNumber = medicalTransNumber;
	}
	private String applicationNumber;
	private String transactionType;
	private String totalAmount;
	private String receiptNumber;
	private String medicalTransNumber;
	

}
