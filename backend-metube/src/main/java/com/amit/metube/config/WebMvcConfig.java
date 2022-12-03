package com.amit.metube.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry.addMapping("/**")
			.allowedOriginPatterns("*")
			.allowedMethods("GET","POST","PUT","DELETE","PATCH","OPTIONS")
			.allowedHeaders("*")
			.maxAge(3600);
	}

}
