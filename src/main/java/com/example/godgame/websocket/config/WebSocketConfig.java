package com.example.godgame.websocket.config;

import com.example.godgame.gameroom.service.GameRoomService;
import com.example.godgame.member.service.MemberService;
import com.example.godgame.websocket.webchat.ChattingMessage;
import com.example.godgame.websocket.webchat.MyHandler;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final RedisTemplate<String, ChattingMessage> redisTemplate;
    private final MemberService memberService;
    private final GameRoomService gameRoomService;

    public WebSocketConfig(RedisTemplate<String, ChattingMessage> redisTemplate, MemberService memberService, GameRoomService gameRoomService) {
        this.redisTemplate = redisTemplate;
        this.memberService = memberService;
        this.gameRoomService = gameRoomService;
    }

    @Getter
    @Value("${jwt.key}")
    private String secretKeyString;

    private SecretKey secretKey;


    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/myHandler")
                .addInterceptors(new JwtHandshakeInterceptor(secretKey)) // 인터셉터 등록
                .setAllowedOriginPatterns("*");
    }

    @Bean
    public MyHandler myHandler() {
        return new MyHandler(redisTemplate, memberService, gameRoomService);
    }
}
