package io.github.takzhanov.game.service;

import io.github.takzhanov.game.domain.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractAccountService implements AccountService {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
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
            LOGGER.debug("User {} authorized already", login);
            return false;
        }
        UserProfile userProfile = getUser(login);
        if (null != userProfile) {
            if (Objects.equals(userProfile.getPassword(), password)) {
                sessions.put(login, userProfile);
                LOGGER.info("User {} login", login);
                return true;
            } else {
                LOGGER.debug("Try authorize for user {}", login);
                return false;
            }
        } else {
            LOGGER.debug("Unknown user {}", login);
            return false;
        }
    }

}
