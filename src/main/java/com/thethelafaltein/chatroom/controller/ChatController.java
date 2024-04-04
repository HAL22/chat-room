package com.thethelafaltein.chatroom.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.thethelafaltein.chatroom.model.ChatMessage;
import com.thethelafaltein.chatroom.model.ChatMessageType;
import com.thethelafaltein.chatroom.model.LoginDetails;

@Controller
public class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        if(chatMessage.getContent().startsWith("/history")){
            ChatMessage cm1 = new ChatMessage("I hate country music", "Dan", LocalDateTime.now(), 100L, null);
            ChatMessage cm2 = new ChatMessage("Dude what up", "Scott", LocalDateTime.now(), 101L, null);
            ChatMessage cm3 = new ChatMessage("Hello", "Peter", LocalDateTime.now(), 103L, null);

            List<ChatMessage>list = new ArrayList<>();
            list.add(cm1);
            list.add(cm2);
            list.add(cm3);

            chatMessage.setHistory(list);

            chatMessage.setType(ChatMessageType.HISTORY);

            return chatMessage;
        }

        
        chatMessage.setType(ChatMessageType.CHAT);
        chatMessage.setTimestamp(LocalDateTime.now());
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }


    @MessageMapping("/chat.loginUser")
    @SendTo("/topic/login")
    public LoginDetails login(@Payload LoginDetails loginDetails){
        loginDetails.setIsAUser(false);
        if(loginDetails.getUsername().equals("gg")){
            loginDetails.setIsAUser(true);
        }

        return loginDetails; 
    }
}
