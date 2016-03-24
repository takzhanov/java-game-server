package com.github.takzhanov.game.service;

import com.github.takzhanov.game.domain.UserProfile;

public interface AccountService {
    boolean addUser(UserProfile userProfile);

    UserProfile getUser(String userName);

    UserProfile getSession(String sessionId);

    int getUsersLimit();

    void setUsersLimit(int usersLimit);

    int getUsersCount();
}
