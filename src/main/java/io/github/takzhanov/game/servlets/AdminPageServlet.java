package io.github.takzhanov.game.servlets;

import io.github.takzhanov.game.context.ApplicationContext;
import io.github.takzhanov.game.helper.TimeHelper;
import io.github.takzhanov.game.service.AccountService;
import io.github.takzhanov.game.templater.PageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = AdminPageServlet.PAGE_URL)
public class AdminPageServlet extends HttpServlet {
    public static final String PAGE_URL = "/admin";
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminPageServlet.class);
    private AccountService accountService;

    public AdminPageServlet() {
        this(ApplicationContext.get(AccountService.class));
    }

    public AdminPageServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String timeString = request.getParameter("shutdown");
        if (timeString != null) {
            LOGGER.info("Bye-bye!");
            request.getRequestDispatcher("bye.html").forward(request, response);
            int timeMS = Integer.valueOf(timeString);
            LOGGER.info("Server will be down after: " + timeMS + " ms");
            TimeHelper.sleep(timeMS);
            LOGGER.info("Shutdown");
            System.exit(0);
        }
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("status", "run");
        pageVariables.put("usersLimit", accountService.getUsersLimit());
        response.getWriter().println(PageGenerator.getPage("admin.html", pageVariables));
    }

}
