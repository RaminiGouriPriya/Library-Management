package com.epam.book.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest req) {
		List<String> inputErrors = new ArrayList<>();
		ex.getAllErrors().forEach(err->
			inputErrors.add(err.getDefaultMessage())
		); 
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.name(),inputErrors.toString(),req.getDescription(false));
	}
	
	@ExceptionHandler(IdNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ExceptionResponse handleIdNotFoundException(IdNotFoundException ex, WebRequest req) {
		return new ExceptionResponse(new Date().toString(), HttpStatus.NOT_FOUND.name(),ex.getMessage(),req.getDescription(false));
	}
	

	
}
