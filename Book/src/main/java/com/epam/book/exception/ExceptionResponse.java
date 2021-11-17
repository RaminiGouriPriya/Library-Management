package com.epam.book.exception;

import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ExceptionResponse {
	String timestamp;
	String status;
	String error;
	String path;
	
	public ExceptionResponse() {}
	public ExceptionResponse(String timestamp, String status, String error, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.path = path;
	}

}
