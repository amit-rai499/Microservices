package com.amit.cloud.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FallBackMethodController {
	
	@GetMapping("/userServiceFallBack")
	public String userServiceFallBackMethod() {
		log.info("Inside userServicefallback");
		return "User Service is taking longer than expected."+"Please try again later";
	}
	@GetMapping("/departmentServiceFallBack")
	public String departmentServiceFallBackMethod() {
		log.info("Inside departmentServicefallback");
		return "Department Service is taking longer than expected."+"Please try again later";
	}
}
