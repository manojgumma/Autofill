package com.enjaz.webautofill.valueobject;

import java.io.Serializable;

public class ResponseVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7842633610357944918L;
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
