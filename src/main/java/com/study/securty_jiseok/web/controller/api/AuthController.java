package com.study.securty_jiseok.web.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.securty_jiseok.service.auth.PrincpalDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final PrincpalDetailsService princpalDetailsService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup() {
		
		return ResponseEntity.ok(princpalDetailsService.addUser());
	}
}
