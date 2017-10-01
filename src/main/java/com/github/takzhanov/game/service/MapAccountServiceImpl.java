package com.github.takzhanov.game.service;

import com.github.takzhanov.game.domain.UserProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapAccountServiceImpl implements AccountService {
    private static long userId = 0;
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

    @Override
    public boolean tryLogin(String login, String password) {
        if (sessions.containsKey(login)) {
//            уже авторизован
            return false;
        }
        UserProfile userProfile = users.get(login);
        if (null != userProfile) {
            if (Objects.equals(userProfile.getPassword(), password)) {
                sessions.put(login, userProfile);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean tryRegister(String login, String password) {
        users.put(login, new UserProfile(userId++, login, password));
        return true;
    }
}
