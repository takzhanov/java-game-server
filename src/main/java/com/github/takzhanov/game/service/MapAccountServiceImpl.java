package com.github.takzhanov.game.service;

import com.github.takzhanov.game.domain.UserProfile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MapAccountServiceImpl extends AbstractAccountService {
    //имитация пользовательской базы
    private final Map<String, UserProfile> users = new ConcurrentHashMap<>();
    private final AtomicLong nextUserId = new AtomicLong(0);

    @Override
    public boolean addUser(UserProfile userProfile) {
        return null == users.putIfAbsent(userProfile.getLogin(), userProfile);
    }

    @Override
    public UserProfile getUser(String login) {
        return users.get(login);
    }

    @Override
    public boolean tryRegister(String login, String password) {
        UserProfile newUser = new UserProfile(nextUserId.getAndIncrement(), login, password);
        return addUser(newUser);
    }
}
