package com.thethelafaltein.chatroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ChatroomApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatroomApplication.class, args);
	}

}
