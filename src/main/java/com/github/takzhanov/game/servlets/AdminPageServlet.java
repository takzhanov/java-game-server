package com.github.takzhanov.game.servlets;

import com.github.takzhanov.game.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminPageServlet extends HttpServlet {
    final static Logger logger = LoggerFactory.getLogger(AdminPageServlet.class);
    public static final String PAGE_URL = "/admin";

    private AccountService accountService;

    public AdminPageServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
//        Map<String, Object> pageVariables = new HashMap<>();
//        String timeString = request.getParameter("shutdown");
//        if (timeString != null) {
//            int timeMS = Integer.valueOf(timeString);
//            logger.info("Server will be down after: " + timeMS + " ms");
//            TimeHelper.sleep(timeMS);
//            logger.info("\nShutdown");
//            System.exit(0);
//        }
//        pageVariables.put("status", "run");
//        response.getWriter().println(PageGenerator.getPage("admin.tml", pageVariables));
        response.getWriter().println(accountService.getUsersLimit());
    }

}
