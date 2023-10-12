package com.devsuperior.dscrud.controllers.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscrud.dto.CustomError;
import com.devsuperior.dscrud.dto.ValidationError;
import com.devsuperior.dscrud.services.exeptions.DatabaseException;
import com.devsuperior.dscrud.services.exeptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND; // Erro 404
		CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	// Para caso houvesse relacionamento entre classes
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<CustomError> dataBase(DatabaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST; 
		CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomError> MethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY; 
		ValidationError err = new ValidationError(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI());
		// e.getBindingResult().getFieldErrors(); // Ele que faz a lista dos erros conforme as annotations que fiz com o Bean Annotation
		for(FieldError f : e.getBindingResult().getFieldErrors()) {
			err.addError(f.getField(), f.getDefaultMessage());
		}	
		return ResponseEntity.status(status).body(err);
	}
}

