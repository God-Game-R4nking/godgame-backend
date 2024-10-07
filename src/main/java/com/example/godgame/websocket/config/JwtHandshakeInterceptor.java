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
import java.util.Map;

@Slf4j
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(JwtHandshakeInterceptor.class);
    private final SecretKey secretKey;

    public JwtHandshakeInterceptor(SecretKey secretKey) {
        this.secretKey = secretKey;
    }


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.info("WebSocket Handshake Request: {}", request.getURI());

        String authHeader = request.getHeaders().getFirst("Authorization");
        logger.info("Authorization Header: {}", authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(token)
                        .getBody();

                String userId = claims.getSubject();
                attributes.put("userId", userId);
                logger.info("JWT validated. User ID: {}", userId);

                return true; // 핸드셰이크 진행
            } catch (JwtException e) {
                logger.error("JWT validation failed: {}", e.getMessage());
                return false; // JWT 검증 실패 시 핸드셰이크 차단
            }
        }

        logger.warn("Authorization header is missing or invalid.");
        return false; // Authorization 헤더가 없는 경우 핸드셰이크 차단
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
