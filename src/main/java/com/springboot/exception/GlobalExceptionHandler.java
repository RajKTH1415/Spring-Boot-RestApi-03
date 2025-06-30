package com.springboot.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.springboot.respone.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		ApiResponse response = new ApiResponse(false, "Validation error", errors);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNoDataFoundException(StudentNotFoundException x) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("status", false);
		errorResponse.put("message", x.getMessage());
		errorResponse.put("errorCode", HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(NoDataAvailableException.class)
	public ResponseEntity<Map<String, Object>> handleNoDataFoundException(NoDataAvailableException exx) {
		Map<String, Object> error = new HashMap<>();
		error.put("status", false);
		error.put("message", exx.getMessage());
		error.put("error", HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DuplicateFoundException.class)
	public ResponseEntity<Map<String, Object>> duplicateFoundException(DuplicateFoundException exxx) {
		Map<String, Object> error = new HashMap<>();
		error.put("status", false);
		error.put("message", exxx.getMessage());
		error.put("error", HttpStatus.CONFLICT);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);

	}

}
