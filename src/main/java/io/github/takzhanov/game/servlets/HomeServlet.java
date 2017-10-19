package io.github.takzhanov.game.servlets;

import io.github.takzhanov.game.domain.UserProfile;
import io.github.takzhanov.game.service.AccountService;
import io.github.takzhanov.game.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Строит домашнюю страничку
 */
public class HomeServlet extends HttpServlet {
    public static final String PAGE_URL = "/home";

    private final AccountService accountService;

    public HomeServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String) req.getSession().getAttribute("login");
        UserProfile up = accountService.getUser(login);
        Map<String, Object> pageVariables = new HashMap<>();
        if (null != up) {
            pageVariables.put("id", up.getId());
            pageVariables.put("login", up.getLogin());
            pageVariables.put("email", up.getEmail());
            pageVariables.put("name", up.getName());
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(PageGenerator.getPage("home.html", pageVariables));
    }
}
