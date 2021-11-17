package com.epam.library.exception;

import org.springframework.stereotype.Component;

@Component
public class IdNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	public IdNotFoundException() {
//		throws exception
	}
	
}
