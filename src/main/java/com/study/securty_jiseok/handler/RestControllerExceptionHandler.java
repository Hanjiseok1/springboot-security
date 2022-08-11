package com.study.securty_jiseok.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.study.securty_jiseok.handler.Exception.CustomvalidationApiException;
import com.study.securty_jiseok.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class RestControllerExceptionHandler {

	@ExceptionHandler(CustomvalidationApiException.class)
	public ResponseEntity<?> validationApiException(CustomvalidationApiException e) {
		return ResponseEntity.badRequest().body(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()));
	}
	
}
