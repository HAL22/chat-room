package com.thethelafaltein.chatroom.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thethelafaltein.chatroom.db.ChatRepository;
import com.thethelafaltein.chatroom.db.UserRepository;
import com.thethelafaltein.chatroom.model.Chat;
import com.thethelafaltein.chatroom.model.ChatMessage;
import com.thethelafaltein.chatroom.model.ChatMessageType;
import com.thethelafaltein.chatroom.model.ChatStatus;
import com.thethelafaltein.chatroom.model.User;

import jakarta.transaction.Transactional;

@Service
public class ChatroomService {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;

   

    @Transactional
    public void insertChat(ChatMessage chatMessage,Long userId){
        Chat chat = new Chat(userId,chatMessage.getContent(),LocalDateTime.now(),LocalDateTime.now(),ChatStatus.ACTIVE);
        chatRepository.save(chat);
    }

    public Optional<User> getUserByUsernameAndPassowrd(String username,String password){
       List<User> users = userRepository.findAll();
       for(User user:users){
        if(user.getUsername().equals(username) && user.getPassword().equals(password)){
            return Optional.of(user);
        }
       }

       return Optional.empty();
    }

    @Transactional
    public List<ChatMessage> getChatHistory(){
        System.out.println("here");
        Optional<List<Chat>> history =  chatRepository.findAllByStatus(ChatStatus.ACTIVE);
        List<ChatMessage>messages = new ArrayList<>();

        for(Chat chat: history.get()){
            Optional<User>usr = userRepository.findById(chat.getCustomerId());
            if(usr.isPresent()){
                String username = usr.get().getUsername();
                messages.add(new ChatMessage(chat.getMessage(), username, chat.getUpdatedAt(), chat.getId(), ChatMessageType.CHAT));
            }    
        }

        return messages;
    }

    @Transactional
    public void deleteChatFromHistory(Long userId,Long chatId){
        chatRepository.updateChatStatus(chatId, ChatStatus.INACTIVE, userId);
    }
}
