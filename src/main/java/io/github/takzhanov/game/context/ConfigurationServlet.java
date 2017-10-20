package io.github.takzhanov.game.context;

import io.github.takzhanov.game.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.JMException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class ConfigurationServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Main.initAppContext();
        } catch (JMException e) {
            LOGGER.error("App configuration failed!");
        }
    }
}
