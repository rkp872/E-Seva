package com.rohit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ESevaProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ESevaProjectApplication.class, args);
	}

}
