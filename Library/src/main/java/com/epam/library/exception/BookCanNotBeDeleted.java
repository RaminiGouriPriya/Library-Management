package com.epam.library.exception;

import org.springframework.stereotype.Component;

@Component
public class BookCanNotBeDeleted extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BookCanNotBeDeleted() {}
	public BookCanNotBeDeleted(String message) {
		super(message);
	}

}
