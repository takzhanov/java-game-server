package com.github.takzhanov.game.service;

import com.github.takzhanov.game.db.DbException;
import com.github.takzhanov.game.db.DbService;
import com.github.takzhanov.game.domain.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbAccountServiceImpl extends AbstractAccountService {
    private final Logger logger = LoggerFactory.getLogger(DbAccountServiceImpl.class);
    //пользовательской базы
    private final DbService dbService;

    public DbAccountServiceImpl(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public boolean addUser(UserProfile userProfile) {
        return (dbService.addUser(userProfile) != -1);
    }

    @Override
    public UserProfile getUser(String login) {
        try {
            return dbService.findUserByLogin(login);
        } catch (DbException e) {
            logger.info("User not found"); //TODO не замалчивать
            return null;
        }
    }

    @Override
    public boolean tryRegister(String login, String password) {
        UserProfile newUser = new UserProfile(-1, login, password);
        return addUser(newUser);
    }
}
