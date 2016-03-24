package com.github.takzhanov.game.service;

import com.github.takzhanov.game.db.DbServiceImpl;
import com.github.takzhanov.game.domain.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class DbAccountServiceImpl implements AccountService {
    private int usersLimit = 10;
    private int usersCount;

    //пользовательской базы
    private DbServiceImpl dbService;
    //текущие сессии пользователей
    private Map<String, UserProfile> sessions = new HashMap<>();

    public DbAccountServiceImpl(DbServiceImpl dbService) {
        this.dbService = dbService;
    }

    @Override
    public boolean addUser(UserProfile userProfile) {
        return (dbService.addUser(userProfile) != -1);
    }

    @Override
    public UserProfile getUser(String login) {
        return dbService.findUserByLogin(login);
    }

    @Override
    public UserProfile getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public int getUsersLimit() {
        return this.usersLimit;
    }

    @Override
    public void setUsersLimit(int usersLimit) {
        this.usersLimit = usersLimit >= 0 ? usersLimit : 0;
    }

    @Override
    public int getUsersCount() {
        return this.usersCount;
    }
}
