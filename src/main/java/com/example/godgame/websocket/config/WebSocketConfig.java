package com.example.godgame.websocket.config;

import com.example.godgame.gameroom.service.GameRoomService;
import com.example.godgame.member.service.MemberService;
import com.example.godgame.websocket.webchat.ChattingMessage;
import com.example.godgame.websocket.webchat.MyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

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

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/myHandler").setAllowedOriginPatterns("*");
    }

    @Bean
    public MyHandler myHandler() {
        return new MyHandler(redisTemplate,memberService, gameRoomService);
    }
}
