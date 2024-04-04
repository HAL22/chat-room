package com.thethelafaltein.chatroom.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ChatMessage {
    private String content;
    private String sender;
    private Long contentId;
    private List<ChatMessage> history;
    private LocalDateTime timestamp;
    private ChatMessageType type;

    public ChatMessage(String content,String sender,LocalDateTime timestamp, Long contentId, ChatMessageType type){
        this.content = content;
        this.sender = sender;
        this.timestamp = timestamp;
        this.contentId = contentId;
        this.history = new ArrayList<>();
        this.type = type;
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

    public Long getContentId(){
        return contentId;
    }

    public List<ChatMessage> getHistory(){
        return history;
    }

    public ChatMessageType getType(){
        return type;
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

    public void setContentId(Long contentId){
        this.contentId = contentId;
    }

    public void setHistory(List<ChatMessage> history){
        this.history = history;
    }

    public void setType(ChatMessageType type){
        this.type = type;
    }
}
