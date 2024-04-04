package com.thethelafaltein.chatroom.db;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.thethelafaltein.chatroom.model.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;




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
        Optional<List<User>> u1 = userRepository.findAllByUserNameAndPassword("Thethe","1000");
        assertThat(u1.isPresent()).isTrue();
        assertEquals(u1.get().size(), 1);

        // Negative cass
        Optional<List<User>> u2 = userRepository.findAllByUserNameAndPassword("Thethe","10N0");
        assertThat(u2.isPresent()).isTrue();
        assertEquals(u2.get().size(), 0);
    }
}
