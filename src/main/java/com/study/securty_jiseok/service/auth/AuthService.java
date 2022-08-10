package com.study.securty_jiseok.service.auth;

import com.study.securty_jiseok.web.dto.auth.UsernameCheckReqDto;

public interface AuthService {

	public boolean checkUsername(UsernameCheckReqDto usernameCheckReqDto) throws Exception;
	public boolean signup() throws Exception;
}
