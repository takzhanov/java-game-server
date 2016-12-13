package com.github.takzhanov.game.db;

import com.github.takzhanov.game.domain.UserProfile;

public interface DbService {
    void printConnectionInfo();

    UserProfile getUser(long id) throws DbException;

    UserProfile findUserByLogin(String login) throws DbException;

    long addUser(UserProfile userProfile) throws DbException;

    void cleanUp();

}
