package io.github.takzhanov.game.db;

public class DbException extends RuntimeException {
    public DbException(Throwable t) {
        super(t);
    }
}
