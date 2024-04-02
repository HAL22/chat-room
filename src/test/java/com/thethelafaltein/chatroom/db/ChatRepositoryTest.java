package com.thethelafaltein.chatroom.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.thethelafaltein.chatroom.model.Chat;
import com.thethelafaltein.chatroom.model.ChatStatus;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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

    @Test
    void getChatByCustomerId(){
        // Positive case
        Chat chat = new Chat(100L,"Hello guys",LocalDateTime.now(),LocalDateTime.now(),ChatStatus.ACTIVE);
        chatRepository.saveAndFlush(chat);
        Optional<List<Chat>> result = chatRepository.findByCustomerId(100L);
        assertThat(result.isPresent()).isTrue();
        assertEquals(result.get().get(0).getCustomerId(), 100L);

        // Negative case
        result = chatRepository.findByCustomerId(1L);
        assertThat(result.get().isEmpty()).isTrue();
    }

    @Test
    void updateByStatus(){
        // Update works
        Chat chat = new Chat(100L,"Hello guys",LocalDateTime.now(),LocalDateTime.now(),ChatStatus.ACTIVE);
        chatRepository.saveAndFlush(chat);
        Optional<List<Chat>> result = chatRepository.findByCustomerId(100L);
        chatRepository.updateChatStatus(result.get().get(0).getId(), ChatStatus.INACTIVE,100L);
        result = chatRepository.findByCustomerId(100L);
        assertEquals(result.get().get(0).getChatStatus(), ChatStatus.INACTIVE);

        // Update doesn't work because customerId is wrong
        chatRepository.updateChatStatus(result.get().get(0).getId(), ChatStatus.ACTIVE,101L);
        result = chatRepository.findByCustomerId(100L);
        assertEquals(result.get().get(0).getChatStatus(), ChatStatus.INACTIVE);
    }

    @Test
    void finaAllByStatus(){
        List<Chat> chats = new ArrayList<>();
        chats.add(new Chat(100L,"Hello guys",LocalDateTime.now(),LocalDateTime.now(),ChatStatus.ACTIVE));
        chats.add(new Chat(101L,"I'm bored",LocalDateTime.now(),LocalDateTime.now(),ChatStatus.INACTIVE));
        chats.add(new Chat(100L,"Ready for summer",LocalDateTime.now(),LocalDateTime.now(),ChatStatus.ACTIVE));
        chats.add(new Chat(101L,"Really bored",LocalDateTime.now(),LocalDateTime.now(),ChatStatus.ACTIVE));

        for(Chat ch:chats){
            chatRepository.saveAndFlush(ch);
        }

        Optional<List<Chat>>result1 = chatRepository.findAllByStatus(ChatStatus.ACTIVE);
        assertThat(result1.isPresent()).isTrue();
        assertEquals(result1.get().size(), 3);
        Optional<List<Chat>>result2 = chatRepository.findAllByStatus(ChatStatus.INACTIVE);
        assertThat(result2.isPresent()).isTrue();
        assertEquals(result2.get().size(), 1);
        
    }

}
