package com.github.takzhanov.game.servlets;

import com.github.takzhanov.game.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourceServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String PAGE_URL = "/resources";
    private ResourceService resourceService;

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
