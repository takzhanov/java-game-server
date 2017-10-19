package io.github.takzhanov.game.service;

public class AccountUserController implements AccountUserControllerMBean {
    private AccountService accountService;

    public AccountUserController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public int getUsersLimit() {
        return accountService.getUsersLimit();
    }

    @Override
    public void setUsersLimit(int usersLimit) {
        accountService.setUsersLimit(usersLimit);
    }

    @Override
    public int getUsersCount() {
        return accountService.getUsersCount();
    }
}
