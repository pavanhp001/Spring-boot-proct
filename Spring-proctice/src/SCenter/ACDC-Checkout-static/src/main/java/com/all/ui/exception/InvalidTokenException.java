package com.AL.ui.exception;

import com.AL.V.exception.BaseException;

/**
 * Handle the Invalid CSRF token
 * 
 * @author skandimalla
 *
 */
public class InvalidTokenException extends BaseException {

	private static final long serialVersionUID = -1522948158158590694L;

	/**
	 * Throwing the custom invalid csrf token exception
	 * @param message
	 */
	public InvalidTokenException(String message) {
		super(message);
	}

}
