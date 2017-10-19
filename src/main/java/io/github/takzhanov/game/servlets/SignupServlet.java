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
 * Обработчик регистрации нового пользователя
 */
public class SignupServlet extends HttpServlet {
    public static final String PAGE_URL = "/signup";

    private final AccountService accountService;

    public SignupServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("message", req.getAttribute("message"));
        pageVariables.put("login", req.getParameter("login"));
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(PageGenerator.getPage("signup.html", pageVariables));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        resp.setContentType("text/html;charset=utf-8");

        if (accountService.tryRegister(login, password)) {
            resp.sendRedirect(LoginServlet.PAGE_URL + "?login=" + login);
        } else {
            req.setAttribute("message", "Регистрация не прошла");
            req.getRequestDispatcher(PAGE_URL).forward(req, resp);
        }
    }
}
