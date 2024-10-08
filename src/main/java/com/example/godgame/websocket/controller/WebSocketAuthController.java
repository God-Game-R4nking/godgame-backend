package com.example.godgame.websocket.controller;


import com.example.godgame.dto.MultiResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WebSocketAuthController {

    @PostMapping("/api/authenticate")
    public ResponseEntity<String> authenticate(Authentication authentication) {
        String userId = authentication.getName();
        // WebSocket URL을 반환
        String websocketUrl = "ws://localhost:8080/myHandler";

        return new ResponseEntity(new ResponseWebUrl(websocketUrl, userId), HttpStatus.OK);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class ResponseWebUrl {
        private String websocketUrl;
        private String userId;
    }
}
