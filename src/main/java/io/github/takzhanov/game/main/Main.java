package io.github.takzhanov.game.main;

import io.github.takzhanov.game.context.ApplicationContext;
import io.github.takzhanov.game.db.DbService;
import io.github.takzhanov.game.db.DbServiceImpl;
import io.github.takzhanov.game.service.*;
import io.github.takzhanov.game.servlets.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void initAppContext() throws JMException {
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

        ApplicationContext.register(DbService.class, dbService);
        ApplicationContext.register(AccountService.class, accountService);
        ApplicationContext.register(ResourceService.class, resourceService);
    }

    private static WebAppContext initWebContext() {
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setResourceBase("static");
//        context.setConfigurations(new Configuration[]{new AnnotationConfiguration()});
        context.addServlet(new ServletHolder(new AdminPageServlet()), AdminPageServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new MirrorServlet()), MirrorServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new WebSocketEchoChatServlet()), WebSocketEchoChatServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new ResourceServlet()), ResourceServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new WelcomeServlet()), WelcomeServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new LoginServlet()), LoginServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new SignupServlet()), SignupServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new HomeServlet()), HomeServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new StatusServlet()), StatusServlet.PAGE_URL);
        return context;
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length == 1) {
            String portString = args[0];
            port = Integer.valueOf(portString);
        }
        LOGGER.info("Starting at port: {}", String.valueOf(port));

        initAppContext();
        WebAppContext context = initWebContext();

        Server server = new Server(port);
        server.setHandler(context);
        server.start();
        LOGGER.debug(server.dump());
        LOGGER.info("Server started");
        server.join();
    }
}
