package com.example.errorhandling;

public class WrappingException extends RuntimeException {

	public WrappingException(Throwable cause) {
		super("wrapping exception", cause);
	}
}
