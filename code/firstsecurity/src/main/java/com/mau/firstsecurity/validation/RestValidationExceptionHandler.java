package com.mau.firstsecurity.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestValidationExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(RepositoryConstraintViolationException.class)
	public ResponseEntity<Object> handleValidationErrorException(Exception ex, WebRequest request){
		RepositoryConstraintViolationException vex = (RepositoryConstraintViolationException)ex;

		List<Map<String,String>> errors =
			vex.getErrors().getFieldErrors().stream()
						   .map( e -> {
							   HashMap<String, String> message = new HashMap<>();
							   message.put("fieldName", e.getField());
							   message.put("error", e.getCode());
							   message.put("resource", e.getObjectName());
							   return message;
						   })
						   .collect(Collectors.toList());
		// reponse renvoyée avec les messages d'erreur validation
		return new ResponseEntity<Object>(errors, new HttpHeaders(), HttpStatus.PARTIAL_CONTENT);
	}

}
