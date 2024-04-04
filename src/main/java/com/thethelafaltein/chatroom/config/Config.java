package com.thethelafaltein.chatroom.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thethelafaltein.chatroom.db.ChatRepository;
import com.thethelafaltein.chatroom.db.UserRepository;
import com.thethelafaltein.chatroom.model.Chat;
import com.thethelafaltein.chatroom.model.ChatStatus;
import com.thethelafaltein.chatroom.model.User;

@Configuration
public class Config {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, ChatRepository chatRepository){
        return args -> {
            User user1 = new User("tom","1234",LocalDateTime.now(),LocalDateTime.now());
            User user2 = new User("john","5341",LocalDateTime.now(),LocalDateTime.now());
            User user3 = new User("xan","qwerty",LocalDateTime.now(),LocalDateTime.now());

            List<User>list = new ArrayList<>();
            list.add(user1);
            list.add(user2);
            list.add(user3);

            List<User>users = userRepository.saveAllAndFlush(list);

            List<Chat>list1 = new ArrayList<>();

            for(User user:users){
                list1.add(new Chat(user.getId(),"Hey guys it's "+user.getUsername(),LocalDateTime.now(),LocalDateTime.now(),ChatStatus.ACTIVE));
            }
       

            chatRepository.saveAllAndFlush(list1);
        };
    }
    
}
