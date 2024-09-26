package com.nagarro.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nagarro.payload.ApiResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerRescourceNotFoundException(ResourceNotFoundException ex){
		String message = ex.getMessage();
		ApiResponse response = ApiResponse.builder()
				.message(message)
				.success(false)
				.status(HttpStatus.NOT_FOUND)
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<ApiResponse> handlerInvalidDataException(InvalidDataException ex){
		String message = ex.getMessage();
		ApiResponse response = ApiResponse.builder()
				.message(message)
				.success(false)
				.status(HttpStatus.BAD_REQUEST)
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex) {
	    BindingResult bindingResult = ex.getBindingResult();

	    // You can iterate over field errors and create a custom error message
	    StringBuilder errorMessage = new StringBuilder("Validation errors: ");
	    for (FieldError fieldError : bindingResult.getFieldErrors()) {
	        errorMessage.append(fieldError.getDefaultMessage()).append("; ");
	    }

	    ApiResponse errorDto = ApiResponse.builder()
	            .message(errorMessage.toString())
	            .success(false)
	            .status(HttpStatus.BAD_REQUEST)
	            .timestamp(LocalDateTime.now()) // Add timestamp
	            .build();
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
	}
}

