package com.github.takzhanov.game.main;

import com.github.takzhanov.game.db.DbService;
import com.github.takzhanov.game.db.DbServiceImpl;
import com.github.takzhanov.game.service.*;
import com.github.takzhanov.game.servlets.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
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

        DbService dbService = new DbServiceImpl();
        dbService.printConnectionInfo();
        AccountService accountService = new DbAccountServiceImpl(dbService);
//        AccountService accountService = new MapAccountServiceImpl();

        AccountUserControllerMBean statServer = new AccountUserController(accountService);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=AccountServerController.usersLimit");
        mbs.registerMBean(statServer, name);

        ResourceService resourceService = new ResourceService();
        mbs.registerMBean(resourceService, new ObjectName("Admin:type=ResourceServerController"));

        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setResourceBase("static");
//        context.setConfigurations(new Configuration[]{new AnnotationConfiguration()});
        context.addServlet(new ServletHolder(new MirrorServlet()), "/mirror");
        context.addServlet(new ServletHolder(new WebSocketEchoChatServlet()), "/chat");
        context.addServlet(new ServletHolder(new AdminPageServlet(accountService)), AdminPageServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new ResourceServlet(resourceService)), ResourceServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new WelcomeServlet()), WelcomeServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new LoginServlet(accountService)), LoginServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new SignupServlet(accountService)), SignupServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new HomeServlet(accountService)), HomeServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new StatusServlet()), StatusServlet.PAGE_URL);

        Server server = new Server(port);
        server.setHandler(context);
        server.start();
        logger.debug(server.dump());
        logger.info("Server started");
        server.join();
    }
}
