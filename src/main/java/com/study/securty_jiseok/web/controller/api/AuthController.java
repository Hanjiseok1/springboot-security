package com.study.securty_jiseok.web.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.securty_jiseok.handler.Exception.CustomvalidationApiException;
import com.study.securty_jiseok.handler.aop.annotation.Log;
import com.study.securty_jiseok.handler.aop.annotation.Timer;
import com.study.securty_jiseok.handler.aop.annotation.ValidCheck2;
import com.study.securty_jiseok.service.auth.AuthService;
import com.study.securty_jiseok.service.auth.PrincpalDetailsService;
import com.study.securty_jiseok.web.dto.CMRespDto;
import com.study.securty_jiseok.web.dto.auth.SignupReqDto;
import com.study.securty_jiseok.web.dto.auth.UsernameCheckReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final PrincpalDetailsService princpalDetailsService;
	private final AuthService authService;
	
	@Log
	@Timer
	@ValidCheck2
	@GetMapping("/signup/validation/username")
	public ResponseEntity<?> checkUsername(@Valid UsernameCheckReqDto usernameCheckReqDto, BindingResult bindingResult) {
		
		
		
		boolean status = false;
		
		try {
			
			status = authService.checkUsername(usernameCheckReqDto);
		
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "서버오류", status));
		}
		
		return ResponseEntity.ok(new CMRespDto<>(1, "회원가입 가능여부", status));
	}
	
	@ValidCheck2
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody @Valid SignupReqDto signupReqDto, BindingResult bindingResult) {
		
		boolean status = false;
		
		
		
		try {
			status = princpalDetailsService.addUser(signupReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "회원가입 실패", status));
		}
		
		return ResponseEntity.ok(new CMRespDto<>(1, "회원가입 성공", status));
	}
}
