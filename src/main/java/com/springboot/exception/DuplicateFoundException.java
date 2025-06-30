package com.springboot.exception;

public class DuplicateFoundException extends RuntimeException {
	
	
	private static final long serialVersionUID = 1L;

	public DuplicateFoundException(String message) {
		super(message);
	}

}
