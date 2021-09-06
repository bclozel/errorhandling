package com.example.errorhandling;

public class DatabaseErrorException extends RuntimeException {

	public DatabaseErrorException() {
		super("database error");
	}
}
