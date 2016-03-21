package com.github.takzhanov.game.main;

import com.github.takzhanov.game.service.AccountService;
import com.github.takzhanov.game.service.AccountServiceImpl;
import com.github.takzhanov.game.servlets.AdminPageServlet;
import com.github.takzhanov.game.servlets.MirrorServlet;
import com.github.takzhanov.game.servlets.SignInServlet;
import com.github.takzhanov.game.servlets.SignUpServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length == 1) {
            String portString = args[0];
            port = Integer.valueOf(portString);
        }
        logger.info("Starting at port: {}", String.valueOf(port));

        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } finally {
            c.close();
        }

        AccountService accountService = new AccountServiceImpl();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new MirrorServlet()), "/mirror");
//        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/api/v1/auth/signin");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
//        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/api/v1/auth/signup");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new AdminPageServlet()), AdminPageServlet.ADMIN_PAGE_URL);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        Server server = new Server(port);
        server.setHandler(context);

        server.start();
        logger.info("Server started");
        server.join();
    }
}
