package io.github.takzhanov.game.service;

import io.github.takzhanov.game.domain.UserProfile;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractAccountService implements AccountService {
    //текущие сессии пользователей
    private final Map<String, UserProfile> sessions = new ConcurrentHashMap<>();
    private final AtomicInteger usersLimit = new AtomicInteger(0);

    @Override
    public UserProfile getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public int getUsersLimit() {
        return usersLimit.intValue();
    }

    @Override
    public void setUsersLimit(int usersLimit) {
        if (usersLimit > 0) {
            this.usersLimit.getAndSet(usersLimit);
        }
    }

    @Override
    public int getUsersCount() {
        return sessions.size();
    }

    @Override
    public boolean tryLogin(String login, String password) {
        if (sessions.containsKey(login)) {
//            уже авторизован
            return false;
        }
        UserProfile userProfile = getUser(login);
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

}
