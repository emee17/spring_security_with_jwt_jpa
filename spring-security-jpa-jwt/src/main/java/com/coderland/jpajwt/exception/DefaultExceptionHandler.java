package com.coderland.jpajwt.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.coderland.jpajwt.model.ApiErrorMessage;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { CoderNotFoundException.class })
	public ResponseEntity<ApiErrorMessage> coderNotFoundException(CoderNotFoundException e) {
		ApiErrorMessage response = new ApiErrorMessage(e.getMessage(), "CL_NOT_FOUND",
				ZonedDateTime.now(ZoneId.of("Z")), null);
		log.info("Error Response " + response);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { BadCredentialsException.class })
	public ResponseEntity<ApiErrorMessage> badCredentialsException(BadCredentialsException e) {
		ApiErrorMessage response = new ApiErrorMessage(
				e.getMessage(), 
				"CL_UNAUTHORIZE_ACCESS",
				ZonedDateTime.now(ZoneId.of("Z")), null);
		log.info("Error Response " + response);
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ValidatedModel> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(new ValidatedModel(((FieldError) error).getField(), error.getDefaultMessage()));
		}
		ApiErrorMessage response = new ApiErrorMessage(
				"Validation Failed", 
				"CL_VALIDATION_FAILED", 
				ZonedDateTime.now(ZoneId.of("Z")), 
				details);
		log.info("Error Response " + response);
		return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
