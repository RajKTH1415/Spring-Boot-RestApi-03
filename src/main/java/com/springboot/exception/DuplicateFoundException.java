package com.springboot.exception;

import java.io.Serial;

public class DuplicateFoundException extends RuntimeException {
	
	
	@Serial
	private static final long serialVersionUID = 1L;

	public DuplicateFoundException(String message) {
		super(message);
	}

}
