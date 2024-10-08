package com.example.godgame.websocket.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(JwtHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 쿼리 파라미터에서 userId를 가져옵니다.
        String userId = request.getURI().getQuery().split("&")[0].split("=")[1]; // userId 쿼리 파라미터 추출

        if (userId != null) {
            attributes.put("userId", userId);
            return true; // 핸드쉐이크 진행
        }

        return false; // userId가 없으면 핸드쉐이크 차단
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
