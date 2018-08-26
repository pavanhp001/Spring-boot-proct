package com.AL.ui.constants;

public enum ErrorConstants {
	
	INVALID_ORDER("CKT001", "Invalid Order number"),
	INVALID_LINE_ITEM("CKE002", "Invalid Line Item number"),
	SUCCESS("CKT000","Success"),
	CREDIT_CHECK_FAIL("CKT003","Credit Check Failed"),
	REQUEST_TN_FAIL("CKT004","Requested Transaction Failed"),
	INVALID_SELECTED_FEATURES("CKE005", "Invalid Selected Features"), 
	VALIDATE_ORDER_FAIL("CKE006","Order Validation Failed"),
	ORDER_SUBMISSION_FAIL("CKE007","Order Submission Failed"),
	ORDER_UPDATE_FAIL("CKE007","Failed to UPDATE Order"),
	REQUEST_DIALOGUE_TN_FAIL("","No Dialogues Returned"),
	CKO_ERROR("","CKOError");
	
	private String errorMessage;
	private String errorCode;
	
	private ErrorConstants(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}
}
