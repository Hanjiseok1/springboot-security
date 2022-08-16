package com.study.securty_jiseok.service.auth;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.study.securty_jiseok.domain.user.User;
import com.study.securty_jiseok.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	
	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		String provider = null;
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		Map<String, Object> attributes = oAuth2User.getAttributes();
		ClientRegistration clientRegistration = userRequest.getClientRegistration();
		log.error(">>>>> userRequest: {}", userRequest.getClientRegistration());
		log.error(">>>>> attributes: {}", oAuth2User.getAttributes());
		
		provider = clientRegistration.getClientName();
		provider = clientRegistration.getClientName();	
		
		return oAuth2User;
	}
	
	private User getOAuth2User(String provider, Map<String, Object> attributes) throws OAuth2AuthenticationException {
		
		User user = null;
		
		String oauth2_id = null;
		
		String id = null;
		
		Map<String, Object> response = null;
		
			if(provider.equalsIgnoreCase("google")) {
				response = attributes;
				id = response.get("sub");
				
				
			}else if(provider.equalsIgnoreCase("naver")) {
				response = (Map<String, Object>) attributes.get("response");
				id = (String) response.get("id");
				
			}else {
				throw new OAuth2AuthenticationException("provider Error!");
			}
			
			oauth2_id = provider + "_" + id;
			
			try {
				user = userRepository.findOAuth2UserByUsername(oauth2_id);
			} catch (Exception e) {
				e.printStackTrace();
				throw new OAuth2AuthenticationException("DATABASE Error!");
			}
			
			if(user == null) {
				user = User.builder()
						.user_name((String)response.get("name"))
						.user_email((String) response.get("email"))
						.user_id(oauth2_id)
						.oauth2_id(oauth2_id)
						.user_password(new BCryptPasswordEncoder().encode(id))
						.user_roles("ROLE_USER")
						.user_provider(provider)
						.build();
				
					userRepository.save(user);
			}
			
	}
}








