package com.meli.exam.mutant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MutantApplication {

	public static void main(String[] args) {
		var app = new SpringApplication(MutantApplication.class);
		Map<String, Object> defProperties = new HashMap<>();
		defProperties.put("spring.profiles.default", "dev");
		app.setDefaultProperties(defProperties);
		app.run(args).getEnvironment();
	}

}
