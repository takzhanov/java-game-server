package com.github.takzhanov.game.service;

import com.github.takzhanov.game.domain.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class MapAccountServiceImpl implements AccountService {
    //имитация пользовательской базы
    private Map<String, UserProfile> users = new HashMap<>();
    //текущие сессии пользователей
    private Map<String, UserProfile> sessions = new HashMap<>();

    @Override
    public boolean addUser(UserProfile userProfile) {
        if (users.containsKey(userProfile.getLogin())) {
            return false;
        } else {
            users.put(userProfile.getLogin(), userProfile);
            return true;
        }
    }

    @Override
    public UserProfile getUser(String userName) {
        return users.get(userName);
    }

    @Override
    public UserProfile getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public int getUsersLimit() {
        return 0;
    }

    @Override
    public void setUsersLimit(int usersLimit) {

    }

    @Override
    public int getUsersCount() {
        return users.size();
    }
}
