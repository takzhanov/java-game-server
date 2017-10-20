package io.github.takzhanov.game.servlets;

import io.github.takzhanov.game.context.ApplicationContext;
import io.github.takzhanov.game.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = ResourceServlet.PAGE_URL)
public class ResourceServlet extends HttpServlet {
    public static final String PAGE_URL = "/io/github/takzhanov/game/resources";
    private final ResourceService resourceService;
    private Logger logger = LoggerFactory.getLogger(ResourceServlet.class);

    public ResourceServlet() {
        this(ApplicationContext.get(ResourceService.class));
    }

    public ResourceServlet(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getParameter("path");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        if (path == null || path.isEmpty()) {
            response.getWriter().println("isEmpty");
        } else {
            response.getWriter().println(path);
        }
        logger.info(path);
        resourceService.processResource(path);
    }
}
