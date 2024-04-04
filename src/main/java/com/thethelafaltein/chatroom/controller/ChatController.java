package com.thethelafaltein.chatroom.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import com.thethelafaltein.chatroom.model.ChatMessage;
import com.thethelafaltein.chatroom.model.ChatMessageType;
import com.thethelafaltein.chatroom.model.LoginDetails;
import com.thethelafaltein.chatroom.model.User;
import com.thethelafaltein.chatroom.service.ChatroomService;

@Controller
public class ChatController {

    @Autowired
    private ChatroomService chatroomService;


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        try{

            Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        if(chatMessage.getContent().startsWith("/history")){
            chatMessage.setHistory(chatroomService.getChatHistory());
           

            chatMessage.setType(ChatMessageType.HISTORY);

            return chatMessage;
        }
        else if(chatMessage.getContent().startsWith("/delete")){
            String[] arr = chatMessage.getContent().split(" ");

            Long contentId = 0L;
            
            contentId = Long.parseLong(arr[1]);
             
            chatroomService.deleteChatFromHistory(userId, contentId);

            chatMessage.setContent(chatMessage.getSender()+" deleted message: "+contentId.toString());

            chatMessage.setType(ChatMessageType.CHAT);

            chatMessage.setTimestamp(LocalDateTime.now());

            return chatMessage;
            
        }


        chatMessage.setType(ChatMessageType.CHAT);
        chatMessage.setTimestamp(LocalDateTime.now());
        chatroomService.insertChat(chatMessage, userId);
        return chatMessage;

        } catch (Exception e){
            chatMessage.setType(ChatMessageType.ERROR);

            chatMessage.setContent("Error executing following command: "+ chatMessage.getContent());

            return chatMessage;
        }  
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
    public LoginDetails login(@Payload LoginDetails loginDetails, SimpMessageHeaderAccessor headerAccessor){
        loginDetails.setIsAUser(false);
    
        Optional<User>user = chatroomService.getUserByUsernameAndPassowrd(loginDetails.getUsername(),loginDetails.getPassword());
        if(user.isPresent()){
            loginDetails.setIsAUser(true);
            headerAccessor.getSessionAttributes().put("userId", user.get().getId());
        }
        
        return loginDetails; 
    }
}
