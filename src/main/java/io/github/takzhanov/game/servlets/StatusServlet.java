package io.github.takzhanov.game.servlets;

import io.github.takzhanov.game.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = StatusServlet.PAGE_URL)
public class StatusServlet extends HttpServlet {
    public static final String PAGE_URL = "/status";

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("pathInfo", request.getPathInfo());
        pageVariables.put("authType", request.getAuthType());
        pageVariables.put("cookies", request.getCookies().length);
        for (Cookie cookie : request.getCookies()) {
            pageVariables.put(cookie.getName(), cookie.getValue());
        }
        pageVariables.put("userPrincipal", request.getUserPrincipal());
        pageVariables.put("parameters", request.getParameterMap().toString());
        return pageVariables;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(request);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        StringBuilder table = new StringBuilder();
        table.append("<tr>");
        table.append("<td>").append("session").append("</td>");
        table.append("<td>").append(request.getSession(false)).append("</td>");
        table.append("</tr>");
        for (Map.Entry entry : pageVariables.entrySet()) {
            table.append("<tr>");
            table.append("<td>").append(entry.getKey()).append("</td>");
            table.append("<td>").append(entry.getValue()).append("</td>");
            table.append("</tr>");
        }
        pageVariables.put("table", table);
        response.getWriter().println(PageGenerator.getPage("status.html", pageVariables));
    }
}
