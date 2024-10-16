package com.example.godgame.websocket.config;

import com.example.godgame.catchmind.service.CatchmindService;
import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.gameroom.service.GameRoomService;
import com.example.godgame.member.service.MemberService;
import com.example.godgame.websocket.webchat.ChattingMessage;
import com.example.godgame.websocket.webchat.MyHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final RedisConnectionFactory redisConnectionFactory;
    private final RedisTemplate<String, ChattingMessage> redisTemplate;
    private final RedisTemplate<String, GameRoom> redisStringGameRoomTemplate;
    private final RedisTemplate<String, Object> redisHashGameRoomTemplate;
    private final MemberService memberService;
    private final GameRoomService gameRoomService;
    private final CatchmindService catchmindService;
    private final ObjectMapper objectMapper;

    public WebSocketConfig(RedisConnectionFactory redisConnectionFactory, RedisTemplate<String, ChattingMessage> redisTemplate, RedisTemplate<String, GameRoom> redisStringGameRoomTemplate, RedisTemplate<String, Object> redisHashGameRoomTemplate, MemberService memberService, GameRoomService gameRoomService, CatchmindService catchmindService, ObjectMapper objectMapper) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.redisTemplate = redisTemplate;
        this.redisStringGameRoomTemplate = redisStringGameRoomTemplate;
        this.redisHashGameRoomTemplate = redisHashGameRoomTemplate;
        this.memberService = memberService;
        this.gameRoomService = gameRoomService;
        this.catchmindService = catchmindService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/myHandler")
                .addInterceptors(new JwtHandshakeInterceptor())
                .setAllowedOriginPatterns("*");
    }

    @Bean
    public MyHandler myHandler() {
        return new MyHandler(objectMapper, redisTemplate, redisStringGameRoomTemplate, redisHashGameRoomTemplate, redisMessageListener(), memberService, gameRoomService, catchmindService);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListener() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

}
