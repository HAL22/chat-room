package com.thethelafaltein.chatroom.model;

import jakarta.persistence.*;;

public class ChatStatusConverter implements AttributeConverter<ChatStatus,Integer> {
    @Override
    public Integer convertToDatabaseColumn(ChatStatus status){
        return status.getValue();
    }

    @Override
    public ChatStatus convertToEntityAttribute(Integer statusId){
        return ChatStatus.valueOf(statusId);
    }
}
