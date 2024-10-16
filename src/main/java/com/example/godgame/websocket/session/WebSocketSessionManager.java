package com.example.godgame.websocket.session;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    private ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addSession(String sessionId, WebSocketSession session) {
        sessions.put(sessionId, session);
    }

    public WebSocketSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public Collection<WebSocketSession> getAllSessions() {
        return sessions.values();
    }
}