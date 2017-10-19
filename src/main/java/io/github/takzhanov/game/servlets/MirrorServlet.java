package io.github.takzhanov.game.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MirrorServlet extends HttpServlet {
    public static final String PAGE_URL = "/mirror";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(request.getParameter("key") == null ? "" : request.getParameter("key"));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
