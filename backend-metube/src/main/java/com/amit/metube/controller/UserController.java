package com.amit.metube.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amit.metube.service.UserRegistrationService;
import com.amit.metube.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserRegistrationService userRegistrationService;
	
	private final UserService userService;
	
	@GetMapping("/register")
	@ResponseStatus(HttpStatus.OK)
	public String register(Authentication authentication) {
		Jwt jwt = (Jwt) authentication.getPrincipal();
		return userRegistrationService.registerUser(jwt.getTokenValue());
	}
	
	@PostMapping("subscribe/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public boolean subscribeUser(@PathVariable String userId) {
		return userService.subscribeUser(userId);
	}
	
	@PostMapping("unsubscribe/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public boolean unSubscribeUser(@PathVariable String userId) {
		return userService.unSubscribeUser(userId);
	}
	
	@GetMapping("/{userId}/history")
	@ResponseStatus(HttpStatus.OK)
	public Set<String> userHistory(@PathVariable String userId){
		return userService.getUserHistory(userId);
	}
}
