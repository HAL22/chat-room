package com.thethelafaltein.chatroom.model;

import java.util.HashMap;
import java.util.Map;

public enum ChatStatus {
    ACTIVE(1),
    INACTIVE(2);

    private int value;
    private static Map<Integer, ChatStatus> map = new HashMap<>();

    private ChatStatus(int value) {
        this.value = value;
    }

    static {
        for (ChatStatus chatType : ChatStatus.values()) {
            map.put(chatType.value, chatType);
        }
    }

    public static ChatStatus valueOf(int chatType) {
        return (ChatStatus) map.get(chatType);
    }

    public int getValue() {
        return value;
    }

}
