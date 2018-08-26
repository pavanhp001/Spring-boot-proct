package com.A.ui.vo;

public class CartError {

	/**
	 * Error Code From Service
	 */
	private String code;
	/**
	 * Error Message from Service
	 */
	private String message;
	/**
	 * Error description from Service
	 */
	private String description;
	/**
	 * 
	 */
	private int errorType;

	/**
	 * @param code
	 */
	public CartError(final String code) {
		this.code = code;

	}

	/**
	 * @param code
	 * @param message
	 */
	public CartError(final String code, final String message) {
		this.code = code;
		this.message = message;

	}

	/**
	 * @param code
	 * @param message
	 * @param description
	 */
	public CartError(final String code, final String message,
			final String description) {
		this.code = code;
		this.message = message;
		this.description = description;
	}

	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return
	 */
	public int getErrorType() {
		return errorType;
	}

	/**
	 * @param errorType
	 */
	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}
	
	
	

}
