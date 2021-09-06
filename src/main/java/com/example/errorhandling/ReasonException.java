package com.example.errorhandling;

public class ReasonException extends RuntimeException {

	public ReasonException() {
		super("reason");
	}
}
