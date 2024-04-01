package com.thethelafaltein.chatroom.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;;

@Entity
@Table(name="chat")
public class Chat {
    @Id
    @SequenceGenerator(
            name = "player_sequence",
            sequenceName = "player_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "player_sequence"
    )
    private Long id;
    private Long customerId;
    private String message;
    @Convert(converter = ChatStatusConverter.class)
    @Column(name="status")
    private ChatStatus chatStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Chat(){

    }

    public Chat(long id, long customerId, String message, LocalDateTime createdAt, LocalDateTime updatedAt, ChatStatus chatStatus){
        this.id = id;
        this.customerId = customerId;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.chatStatus = chatStatus;
    }

    public Chat(long customerId, String message, LocalDateTime createdAt, LocalDateTime updatedAt, ChatStatus chatStatus){
        this.customerId = customerId;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.chatStatus = chatStatus;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public ChatStatus getChatStatus(){
        return chatStatus;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


}
