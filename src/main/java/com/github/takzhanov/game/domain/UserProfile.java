package com.github.takzhanov.game.domain;

public class UserProfile {
    private long id;
    private String login;
    private String password;
    private String email;

    public UserProfile(long id, String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public UserProfile(long id, String login, String password) {
        this(id, login, password, "");
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
