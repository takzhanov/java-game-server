package io.github.takzhanov.game.servlets;

import io.github.takzhanov.game.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Отвечает за отрисовку приветственной странички со статусами
 */
public class WelcomeServlet extends HttpServlet {
    public static final String PAGE_URL = "/welcome";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = (String) session.getAttribute("login"); //TODO из сессий
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("greeting", null == login ? "Authorize!" : String.format("Welcome, %s!", login));
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(PageGenerator.getPage("welcome.html", pageVariables));
    }
}
