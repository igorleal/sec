package com.igor.sec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class MySecApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySecApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
			    // This is not safe at all. Valid only for development purpose
				registry.addMapping("/*").allowedOrigins("*").allowedHeaders("authorization", "content-type").allowedMethods("GET", "POST", "DELETE");
			}
		};
	}
}
