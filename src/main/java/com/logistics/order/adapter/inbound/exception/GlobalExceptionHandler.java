package com.logistics.order.adapter.inbound.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.logistics.order.adapter.inbound.exception.dto.ApiError;
import com.logistics.order.adapter.inbound.exception.dto.ApiResponseWrapper;
import com.logistics.order.application.exception.ApplicationException;
import com.logistics.order.application.exception.UnexpectedOrderServiceException;
import com.logistics.order.domain.exception.DomainException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DomainException.class)
	public ResponseEntity<ApiResponseWrapper<Void>> handleDomainException(DomainException ex) {
		List<ApiError> errors = new ArrayList<>();

		errors.add(new ApiError(null, ex.getMessage()));
		return ResponseEntity.badRequest().body(ApiResponseWrapper.failure(errors));
	}

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ApiResponseWrapper<Void>> handleApplicationException(ApplicationException ex) {

		List<ApiError> errors = List.of(new ApiError(null, ex.getMessage()));
		return ResponseEntity.badRequest().body(ApiResponseWrapper.failure(errors));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseWrapper<Void>> handleGeneralException(Exception ex) {
		List<ApiError> errors = new ArrayList<>();

		errors.add(new ApiError(null, ex.getMessage()));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseWrapper.failure(errors));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponseWrapper<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<ApiError> errors = new ArrayList<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.add(new ApiError(error.getField(), error.getDefaultMessage())));
		return ResponseEntity.badRequest().body(ApiResponseWrapper.failure(errors));
	}
	
	@ExceptionHandler(UnexpectedOrderServiceException.class)
	public ResponseEntity<ApiResponseWrapper<Void>> handleValidationExceptions(UnexpectedOrderServiceException ex) {
		List<ApiError> errors = List.of(new ApiError(null, ex.getMessage()));
		return ResponseEntity.badRequest().body(ApiResponseWrapper.failure(errors));
	}
	
	

}
