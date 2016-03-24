package com.github.takzhanov.game.main;

import com.github.takzhanov.game.db.DbServiceImpl;
import com.github.takzhanov.game.service.*;
import com.github.takzhanov.game.servlets.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {
    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length == 1) {
            String portString = args[0];
            port = Integer.valueOf(portString);
        }
        logger.info("Starting at port: {}", String.valueOf(port));

        DbServiceImpl dbService = new DbServiceImpl();
        dbService.printConnectionInfo();
        AccountService accountService = new DbAccountServiceImpl(dbService);

        AccountUserControllerMBean serverStatistics = new AccountUserController(accountService);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=AccountServerController.usersLimit");
        mbs.registerMBean(serverStatistics, name);

        ResourceService resourceService = new ResourceService();
        mbs.registerMBean(resourceService, new ObjectName("Admin:type=ResourceServerController"));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new MirrorServlet()), "/mirror");
        context.addServlet(new ServletHolder(new WebSocketEchoChatServlet()), "/chat");
//        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/api/v1/auth/signin");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
//        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/api/v1/auth/signup");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new AdminPageServlet(accountService)), AdminPageServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new ResourceServlet(resourceService)), ResourceServlet.PAGE_URL);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();
        logger.info("Server started");
        server.join();
    }
}
