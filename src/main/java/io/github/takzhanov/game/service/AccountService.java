package io.github.takzhanov.game.service;

import io.github.takzhanov.game.domain.UserProfile;

public interface AccountService {
    boolean addUser(UserProfile userProfile);

    UserProfile getUser(String login);

    UserProfile getSession(String sessionId);

    int getUsersLimit();

    void setUsersLimit(int usersLimit);

    int getUsersCount();

    boolean tryLogin(String login, String password);

    boolean tryRegister(String login, String password);
}
