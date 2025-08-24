package com.example.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(modifyOnCreate = false)
public class WebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

}
