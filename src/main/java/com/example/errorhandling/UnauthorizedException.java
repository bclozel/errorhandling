package com.example.errorhandling;

public class UnauthorizedException extends RuntimeException {

	public UnauthorizedException() {
		super("unauthorized");
	}
}
