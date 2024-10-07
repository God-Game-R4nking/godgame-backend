package com.example.godgame.chat.controller;

import com.example.godgame.chat.entity.Chat;
import com.example.godgame.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) {
        chat.setCreatedAt(LocalDateTime.now());
        Chat savedChat = chatService.saveChat(chat);
        return ResponseEntity.status(201).body(savedChat);
    }

    @GetMapping("/game-room/{game-room-id}")
    public ResponseEntity<List<Chat>> getChatsByGameRoomId(@PathVariable String gameRoomId) {
        List<Chat> chats = chatService.getChatsByGameRoomId(gameRoomId);
        return ResponseEntity.ok(chats);
    }
}