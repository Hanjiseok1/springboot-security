package com.study.securty_jiseok.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.study.securty_jiseok.config.auth.AuthFaliureHandler;
import com.study.securty_jiseok.service.auth.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;

//토큰이란 인증서와 같은것

@EnableWebSecurity	//기존의WebSecurityConfigurerAdapter를 비활성 시키고 현재 시큐리티 설정을 따르겠다. 라는 의미
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PrincipalOauth2UserService principalOauth2UserService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();	//csrf()와 disable() 안쓰면 나중에 문제가 생김
		http.authorizeRequests()	//authorizeRequests() 요청이 들어왔을때 인증을 거쳐라는 용어 //authorizeRequests()anyRequest()permitAll() 3개가 셋트임.
			.antMatchers("/api/v1/grant/test/user/**")
			.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			
			.antMatchers("/api/v1/grant/test/manager/**")
			.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			
			.antMatchers("/api/v1/grant/test/admin/**")
			.access("hasRole('ROLE_ADMIN')")
			
			.antMatchers("/", "/index", "/mypage/**")				//우리가 지정한 요청
			.authenticated()										//인증을 거쳐라
			
			.anyRequest()											//다른 모든 요청은
			.permitAll()											//모든 접근 권한을 부여하겠다
			
			.and()
			
			.formLogin()											//로그인 방식은 formLogin() 방식을 사용하겟다.
			.loginPage("/auth/signin")								//로그인 페이지는 해당 get요청을 통해 접근한다.
			.loginProcessingUrl("/auth/signin")						//로그인 요청(post요청)
			.failureHandler(new AuthFaliureHandler())
			
			.and()
			
			.oauth2Login()
			.userInfoEndpoint()
			.userService(principalOauth2UserService)
			
			.and()
			
			.defaultSuccessUrl("/index");
	}
}











