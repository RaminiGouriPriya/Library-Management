package com.epam.library.exception;

import org.springframework.stereotype.Component;

@Component
public class UserNameDoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UserNameDoesNotExistException() {
//		throws exception
	}

}
