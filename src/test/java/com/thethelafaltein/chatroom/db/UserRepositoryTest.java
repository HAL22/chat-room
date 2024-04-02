package com.thethelafaltein.chatroom.db;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.thethelafaltein.chatroom.model.User;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;




@DataJpaTest
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void teardown(){
        userRepository.deleteAllInBatch();
    }

    @Test
    void insertUser(){
        User user = new User("Thethe","1000",LocalDateTime.now(),LocalDateTime.now());
        userRepository.saveAndFlush(user);     
    }


    @Test
    void getUserByUsernameAndPassowrd(){
        User user = new User("Thethe","1000",LocalDateTime.now(),LocalDateTime.now());
        userRepository.saveAndFlush(user);

        // Positive case
        Optional<User> u1 = userRepository.findByUsernameAndPassword("Thethe","1000");
        assertThat(u1.isPresent()).isTrue();

        // Negative cass
        Optional<User> u2 = userRepository.findByUsernameAndPassword("Thethe","10N0");
        assertThat(u2.isPresent()).isFalse();
    }
}
