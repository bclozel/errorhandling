package com.example.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnknownUserException extends RuntimeException {

	public UnknownUserException(String name) {
		super("Unknown user [" + name + "]");
	}
	
}
