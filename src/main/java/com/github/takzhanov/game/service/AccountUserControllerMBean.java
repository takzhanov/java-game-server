package com.github.takzhanov.game.service;

public interface AccountUserControllerMBean {
    int getUsersLimit();

    void setUsersLimit(int usersLimit);

    int getUsersCount();
}
