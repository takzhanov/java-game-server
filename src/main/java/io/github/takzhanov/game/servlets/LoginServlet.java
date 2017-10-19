package io.github.takzhanov.game.servlets;

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
 * Обработчик формы входа
 */
public class LoginServlet extends HttpServlet {
    public static final String PAGE_URL = "/login";

    private AccountService accountService;

    public LoginServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("message", req.getAttribute("message"));
        pageVariables.put("login", req.getParameter("login"));
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(PageGenerator.getPage("login.tml", pageVariables));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (accountService.tryLogin(login, password)) {
            req.getSession().setAttribute("login", login);
            resp.sendRedirect(WelcomeServlet.PAGE_URL);
        } else {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("message", "Логин или пароль не верный");
            pageVariables.put("login", login);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(PageGenerator.getPage("login.tml", pageVariables));
        }
    }
}
