package com.coderland.jpajwt.model;

import java.time.ZonedDateTime;
import java.util.List;

import com.coderland.jpajwt.exception.ValidatedModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorMessage {

	private final String message;
	private final String errorCode;
	private final ZonedDateTime timestamp;
	private List<ValidatedModel> details;
	
	
}
