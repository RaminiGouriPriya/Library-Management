package com.epam.library.exception;

public class LimitReachedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LimitReachedException(String message) {
		super(message);
	}

}
