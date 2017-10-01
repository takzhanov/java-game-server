package com.github.takzhanov.game.service;

import com.github.takzhanov.game.domain.UserProfile;

import java.util.Objects;

public interface AccountService {
    boolean addUser(UserProfile userProfile);

    UserProfile getUser(String userName);

    UserProfile getSession(String sessionId);

    int getUsersLimit();

    void setUsersLimit(int usersLimit);

    int getUsersCount();

    default boolean tryLogin(String login, String password) {
        return Objects.equals(login, password);
    }

    default boolean tryRegister(String login, String password) {
        return true;
    }
}
