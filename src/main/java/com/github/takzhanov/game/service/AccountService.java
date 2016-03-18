package com.github.takzhanov.game.service;

import com.github.takzhanov.game.domain.UserProfile;

public interface AccountService {
    boolean addUser(String userName, UserProfile userProfile);

    UserProfile getUser(String userName);

    UserProfile getSession(String sessionId);

}
