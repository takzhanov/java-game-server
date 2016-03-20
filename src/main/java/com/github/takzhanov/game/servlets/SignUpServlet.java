package com.github.takzhanov.game.servlets;

import com.github.takzhanov.game.domain.UserProfile;
import com.github.takzhanov.game.service.AccountService;
import com.github.takzhanov.game.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet {
    private AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String name = request.getParameter("name");
//        String password = request.getParameter("password");
//
//        Map<String, Object> pageVariables = new HashMap<>();
//        if (accountService.addUser(name, new UserProfile(name, password, ""))) {
//            pageVariables.put("signUpStatus", "New user created");
//        } else {
//            pageVariables.put("signUpStatus", "User with name: " + name + " already exists");
//        }
//
//        response.getWriter().println(PageGenerator.getPage("signupstatus.html", pageVariables));
//        response.setStatus(HttpServletResponse.SC_OK);
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Map<String, Object> pageVariables = new HashMap<>();
        if (accountService.addUser(login, new UserProfile(login, password, ""))) {
            pageVariables.put("signUpStatus", "New user created");
        } else {
            pageVariables.put("signUpStatus", "User with name: " + name + " already exists");
        }

        response.getWriter().println(PageGenerator.getPage("signupstatus.html", pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
