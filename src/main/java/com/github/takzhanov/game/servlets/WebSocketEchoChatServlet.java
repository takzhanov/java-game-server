package com.github.takzhanov.game.servlets;

import com.github.takzhanov.game.service.ChatService;
import com.github.takzhanov.game.service.ChatWebSocket;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.w3c.dom.html.HTMLAnchorElement;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "WebSocketEchoChatServlet", urlPatterns = {"/chat"})
public class WebSocketEchoChatServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 10 * 60 * 1000;
    private final ChatService chatService;

    public WebSocketEchoChatServlet() {
        this.chatService = new ChatService();
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator((req, resp) -> new ChatWebSocket(chatService));
    }
}
