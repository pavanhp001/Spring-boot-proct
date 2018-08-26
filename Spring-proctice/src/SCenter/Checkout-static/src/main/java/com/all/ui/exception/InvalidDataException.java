package com.AL.ui.exception;

public class InvalidDataException extends Exception  {
	private static final long serialVersionUID = -4798498446556221410L;

    public InvalidDataException() {
        super();
    }

    /**
     * @param message
     */
    public InvalidDataException(final String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public InvalidDataException(final Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public InvalidDataException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
