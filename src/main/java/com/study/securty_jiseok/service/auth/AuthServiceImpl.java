package com.study.securty_jiseok.service.auth;

import org.springframework.stereotype.Service;

import com.study.securty_jiseok.domain.user.UserRepository;
import com.study.securty_jiseok.web.dto.auth.UsernameCheckReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final UserRepository userRepository;

	@Override
	public boolean checkUsername(UsernameCheckReqDto usernameCheckReqDto) throws Exception {
		
		return userRepository.findUserByUsername(usernameCheckReqDto.getUsername()) == null;
	}

	@Override
	public boolean signup() {
		return false;
	}

}
