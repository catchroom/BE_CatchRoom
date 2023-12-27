package com.example.catchroom_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class CatchRoomBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatchRoomBeApplication.class, args);
	}

}
