package com.study.securty_jiseok.web.dto.auth;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.study.securty_jiseok.domain.user.User;

import lombok.Data;

@Data
public class SignupReqDto {
	
	@NotBlank
	@Pattern(regexp = "^[가-힇]*$", message = "한글만 입력 가능합니다.")
	private String name;
	
	@NotBlank
	@Email
	private String eamil;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4, 12}$")
	private String username;
	
	@NotBlank
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[-~!@#$%^&*_+=])[a-zA-Z\\d-~!@#$%^&*_+=]{8, 16}$")
	private String password;
	
	@AssertTrue(message = "아이디 중복확인이 되지 않았습니다.")
	private boolean checkUsernameFlag;
	
	public User toEntity() {
		return User.builder()
				.user_name(name)
				.user_email(eamil)
				.user_id(username)
				.user_password(password)
				.user_roles("ROLE_USER")
				.build();
	}
}
