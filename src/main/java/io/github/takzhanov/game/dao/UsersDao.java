package io.github.takzhanov.game.dao;

import io.github.takzhanov.game.domain.UserProfile;
import io.github.takzhanov.game.executor.QueryExecutor;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDao {
    private final QueryExecutor executor;

    public UsersDao(Connection connection) {
        this.executor = new QueryExecutor(connection);
    }

    public UserProfile getById(long id) throws SQLException {
        return executor.execQuery("select * from users where id=" + id, result -> {
            result.next();
            return new UserProfile(result.getLong(1), result.getString(2), result.getString(3));
        });
    }

    public Long findIdByLogin(String login) throws SQLException {
        return executor.execQuery("select * from users where user_name='" + login + "'", result -> {
            if (result.next()) {
                return result.getLong(1);
            } else {
                return null;
            }
        });
    }

    public void insertUser(UserProfile userProfile) throws SQLException {
        executor.execUpdate("insert into users (user_name, password) values ('" + userProfile.getLogin() + "', '" + userProfile.getPassword() + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, user_name varchar(256), password varchar(256), primary key (id))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}
