package com.epam.library.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	@Autowired
	ObjectMapper objectMapper;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest req) {
		List<String> inputErrors = new ArrayList<>();
		ex.getAllErrors().forEach(err->
			inputErrors.add(err.getDefaultMessage())
		);
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.name(),inputErrors.toString(),req.getDescription(false));
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ExceptionResponse> handleException(FeignException ex, WebRequest req)
			throws JsonProcessingException {
		String errorMessage = objectMapper.readValue(ex.contentUTF8(), ExceptionResponse.class).getError();
		ExceptionResponse error = new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.name(),
				errorMessage, req.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidParameterException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleIdNotFoundException(InvalidParameterException ex, WebRequest req) {

		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.name(),
				ex.getMessage(), req.getDescription(false));
	}
	
	@ExceptionHandler(LimitReachedException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleLimitReachedException(LimitReachedException ex, WebRequest req) {

		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.name(),
				ex.getMessage(), req.getDescription(false));
	}

	@ExceptionHandler(BookCanNotBeDeleted.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleBookCanNotBeDeleted(BookCanNotBeDeleted ex, WebRequest req) {
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.name(),
				ex.getMessage(), req.getDescription(false));
	}

	@ExceptionHandler(BookAlreadyIssuedException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleBookAlreadyIssuedException(BookAlreadyIssuedException ex, WebRequest req) {
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.name(),
				ex.getMessage(), req.getDescription(false));
	}

}
