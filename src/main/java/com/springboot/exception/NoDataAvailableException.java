package com.springboot.exception;

public class NoDataAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoDataAvailableException(String message) {
		super(message);

	}

}
