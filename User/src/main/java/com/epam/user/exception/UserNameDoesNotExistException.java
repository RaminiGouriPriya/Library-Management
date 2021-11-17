package com.epam.user.exception;

import org.springframework.stereotype.Component;

@Component
public class UserNameDoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserNameDoesNotExistException() {}
	public UserNameDoesNotExistException(String message) {
		super(message);
	}

}
