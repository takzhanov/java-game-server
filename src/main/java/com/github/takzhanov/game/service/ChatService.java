package com.github.takzhanov.game.service;

import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatService {
    private Set<ChatWebSocket> webSockets;

    public ChatService() {
        webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public void sendMessage(String data) {
        for (ChatWebSocket socket : webSockets) {
            try {
                socket.sendString(data);
            } catch (Exception ex) {
                LoggerFactory.getLogger(ChatService.class).error(ex.getMessage());
            }
        }
    }

    public boolean add(ChatWebSocket chatWebSocket) {
        return webSockets.add(chatWebSocket);
    }

    public boolean remove(Object o) {
        return webSockets.remove(o);
    }
}
