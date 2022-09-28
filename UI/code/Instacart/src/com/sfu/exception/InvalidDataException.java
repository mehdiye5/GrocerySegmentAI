package com.sfu.exception;

/**
* InvalidDataException is a custom exception that indicates that the
* required data is invalid.
*/
public class InvalidDataException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	* Constructs an instance of InvalidDataException using an error message.
	*/
	public InvalidDataException(String errorMessage) {
        super(errorMessage);
    }
}
