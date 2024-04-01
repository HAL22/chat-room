package com.thethelafaltein.chatroom.db;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.thethelafaltein.chatroom.model.Chat;
import com.thethelafaltein.chatroom.model.ChatStatus;

@DataJpaTest
public class ChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;

    @AfterEach
    void tearDown(){
        chatRepository.deleteAllInBatch();
    }

    @Test
    void insertChat(){
        Chat chat = new Chat(100L,"Hello guys",LocalDateTime.now(),LocalDateTime.now(),ChatStatus.ACTIVE);
        chatRepository.saveAndFlush(chat);
    }

}
