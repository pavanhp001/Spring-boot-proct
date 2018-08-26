package com.AL.ui.exception;

/**
 * @author ptripuraneni
 *
 */
public class InvalidFormatException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidFormatException() {
        super();
    }

    /**
     * @param message
     */
    public InvalidFormatException(final String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public InvalidFormatException(final Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public InvalidFormatException(final String message, final Throwable cause) {
        super(message, cause);
    }
	
}
