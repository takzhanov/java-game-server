package io.github.takzhanov.game.servlets;

import io.github.takzhanov.game.service.ChatService;
import io.github.takzhanov.game.service.ChatWebSocket;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "WebSocketEchoChatServlet", urlPatterns = {"/chat"})
public class WebSocketEchoChatServlet extends WebSocketServlet {
    public static final String PAGE_URL = "/chat";
    private static final int LOGOUT_TIME = 10 * 60 * 1000;
    private final ChatService chatService;

    public WebSocketEchoChatServlet() {
        this.chatService = new ChatService();
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator((req, resp) -> new ChatWebSocket(chatService));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("chat.html").forward(req, resp);
    }
}
