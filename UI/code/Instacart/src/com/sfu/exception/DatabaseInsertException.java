package com.sfu.exception;

public class DatabaseInsertException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	* Constructs an instance of InvalidDatabaseInsertException using an error message.
	*/
	public DatabaseInsertException(String errorMessage) {
        super(errorMessage);
    }

}
