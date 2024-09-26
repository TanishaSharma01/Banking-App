package com.nagarro.exceptions;

public class InvalidDataException extends RuntimeException{
	
	public InvalidDataException() {
		super("Data is invalid.");
	}
	
	public InvalidDataException(String message) {
		super(message);
	}

}
