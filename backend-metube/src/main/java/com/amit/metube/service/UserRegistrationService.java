package com.amit.metube.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.util.Optional;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amit.metube.dto.UserInfoDTO;
import com.amit.metube.model.User;
import com.amit.metube.repo.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

	private final UserRepository userRepository;
	
	@Value("${auth0.userinfoEndpoint}")
	private String userInfoEndpoint;
	
	public String registerUser(String tokenValue) {
		// Make a call to user info endpoint
		HttpRequest httpRequest = HttpRequest.newBuilder()
			.GET()
			.uri(URI.create(userInfoEndpoint))
			.setHeader("Authorization", String.format("Bearer %s", tokenValue))
			.build();
		
		HttpClient httpClient =  HttpClient.newBuilder()
				.version(Version.HTTP_2)
				.build();
		
	
		try {
			HttpResponse<String> responseString =  httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
			String body =responseString.body();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			UserInfoDTO userInfoDTO = objectMapper.readValue(body, UserInfoDTO.class);
			
			Optional<User> userBySubject = userRepository.findBySub(userInfoDTO.getSub());
			if(userBySubject.isPresent()) {
				return userBySubject.get().getId();
			}else {
				User user = new User();
				user.setFirstName(userInfoDTO.getGivenName());
				user.setLastName(userInfoDTO.getFamilyName());
				user.setFullName(userInfoDTO.getName());
				user.setEmailAddress(userInfoDTO.getEmail());
				user.setSub(userInfoDTO.getSub());
				return userRepository.save(user).getId();
			}			
		} catch (Exception e) {
			throw new RuntimeException("Exception occurred while registering user",e);
		} 
		//Fetch User details and save them to the databse
	}
}
