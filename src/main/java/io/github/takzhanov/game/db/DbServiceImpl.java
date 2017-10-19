package io.github.takzhanov.game.db;

import io.github.takzhanov.game.dao.UsersDao;
import io.github.takzhanov.game.domain.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbServiceImpl implements DbService {
    private final static Logger logger = LoggerFactory.getLogger(DbServiceImpl.class);

    static {
        try {
            DriverManager.registerDriver((Driver) Class.forName("org.h2.Driver").newInstance());
        } catch (SQLException | InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private final Connection connection;

    public DbServiceImpl() {
        this.connection = DbServiceImpl.getH2Connection();
    }

    static Connection getH2Connection() {
        try {
            String url = "jdbc:h2:./h2db";
            String name = "jorj";
            String pass = "jorj";

            return DriverManager.getConnection(url, name, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void printConnectionInfo() {
        try {
            logger.info("DB name: " + connection.getMetaData().getDatabaseProductName());
            logger.info("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            logger.info("Driver: " + connection.getMetaData().getDriverName());
            logger.info("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserProfile getUser(long id) throws DbException {
        try {
            return (new UsersDao(connection).getById(id));
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public UserProfile findUserByLogin(String login) throws DbException {
        try {
            UsersDao dao = new UsersDao(connection);
            Long id = dao.findIdByLogin(login);
            return id != null ? dao.getById(id) : null;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public long addUser(UserProfile userProfile) throws DbException {
        try {
            connection.setAutoCommit(false);
            UsersDao dao = new UsersDao(connection);
            dao.createTable();
            dao.insertUser(userProfile);
            connection.commit();
            return dao.findIdByLogin(userProfile.getLogin());
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DbException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    @Override
    public void cleanUp() throws DbException {
        UsersDao dao = new UsersDao(connection);
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}
