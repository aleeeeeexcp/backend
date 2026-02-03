package com.app.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ManagementApplication {

	public static void main(String[] args) {
		System.setProperty("spring.data.mongodb.uri", "mongodb+srv://acampar11_db_user:Password123456@cluster0.n7l4tfk.mongodb.net/management?retryWrites=true&w=majority");
		SpringApplication.run(ManagementApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
