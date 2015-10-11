package com.github.ytakzhanov.game.service;

import com.github.ytakzhanov.game.main.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private Map<String, UserProfile> users = new HashMap<>();
    private Map<String, UserProfile> sessions = new HashMap<>();

    public boolean addUser(String userName, UserProfile userProfile) {
        if (users.containsKey(userName)) {
            return false;
        } else {
            users.put(userName, userProfile);
            return true;
        }
    }

    public UserProfile getUser(String userName) {
        return users.get(userName);
    }

    public UserProfile getSession(String sessionId) {
        return sessions.get(sessionId);
    }
}
