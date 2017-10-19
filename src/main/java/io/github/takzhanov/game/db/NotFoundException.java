package io.github.takzhanov.game.db;

public class NotFoundException extends DbException {
    public NotFoundException(Throwable t) {
        super(t);
    }
}
