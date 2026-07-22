package com.AI_Powered_Resume_JobMatcher.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, "Invalid Input", ex.getMessage());
	}


	// File too large (based on spring.servlet.multipart.max-file-size)
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<Map<String, Object>> handleFileTooLarge(MaxUploadSizeExceededException ex) {
		return buildResponse(HttpStatus.PAYLOAD_TOO_LARGE, "File Too Large",
				"Resume file exceeds the 5MB size limit.");
	}


	// Validation errors on request DTOs (e.g. missing jobDescription field)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> err.getField() + ": " + err.getDefaultMessage())
				.reduce((a, b) -> a + "; " + b)
				.orElse("Validation failed");
		return buildResponse(HttpStatus.BAD_REQUEST, "Validation Error", message);
	}


	// Errors from the Gemini API call itself (e.g. rate limit, invalid key, model errors)
	@ExceptionHandler(WebClientResponseException.class)
	public ResponseEntity<Map<String, Object>> handleGeminiApiError(WebClientResponseException ex) {
		return buildResponse(HttpStatus.BAD_GATEWAY, "AI Service Error",
				"Failed to get a response from Gemini: " + ex.getStatusCode());
	}

	// Fallback for anything unhandled (e.g. JSON parsing failure from Gemini's response)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
				"Something went wrong. Please try again.");
	}


	private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String error, String message) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());
		body.put("error", error);
		body.put("message", message);
		return ResponseEntity.status(status).body(body);
	}



}
