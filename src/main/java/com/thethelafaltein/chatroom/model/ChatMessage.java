package com.thethelafaltein.chatroom.model;

import java.time.LocalDateTime;

public class ChatMessage {
    private String content;
    private String sender;
    private LocalDateTime timestamp;

    public ChatMessage(String content,String sender,LocalDateTime timestamp){
        this.content = content;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    // Getters
    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
