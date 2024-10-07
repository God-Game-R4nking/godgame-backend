//package com.example.godgame.websocket.controller;
//
//
//import com.example.godgame.websocket.service.ChattingService;
//import com.example.godgame.websocket.webchat.ChattingMessage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class WebSocketController {
//
//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//
//    @Autowired
//    private ChattingService chatService;
//
//    private final Logger log = LoggerFactory.getLogger(getClass());
//
//    // 클라이언트에서 특정 채팅방의 메시지 조회
//    @GetMapping("/api/messages/{roomNo}")
//    public List<ChattingMessage> getMessagesByRoom(@PathVariable String roomNo) {
//        log.info("Received request to get messages for room: {}", roomNo);
//
//        return chatService.getMessagesByRoom(roomNo);
//    }
//
//
//    // WebSocket에서 사용하는 메시지 매핑 : 채팅방 입장 메시지 처리
//    @MessageMapping("/chat/join/{roomNo}")
//    public void join(@DestinationVariable String roomNo, @Payload ChattingMessage chatDto) {
//        log.info("User '{}' joined the room '{}'", chatDto.getMemberId(), roomNo);
//
//        // 채팅방 입장 메시지 저장
//        chatService.saveMessage(chatDto);
//
//        // 채팅방 입장 메시지를 해당 채팅방의 구독자에게 전송
//        chatDto.setContent(chatDto.getMemberId() + "님이 입장하셨습니다.");
//        simpMessagingTemplate.convertAndSend("/sub/chat/join/" + roomNo, chatDto);
//    }
//
//    // WebSocket에서 사용하는 메시지 매핑 : 채팅방 메시지 전송 처리
//    @MessageMapping("/chat/message/{roomNo}")
//    public void sendMessage(@DestinationVariable String roomNo, @Payload ChattingMessage chatDto) {
//        log.info("User '{}' sent a message in the room '{}': '{}'", chatDto.getMemberId(), roomNo, chatDto.getContent());
//
//        // 채팅방 메시지 저장
//        chatService.saveMessage(chatDto);
//
//        // 메시지가 ~로 시작한다면 해당 채팅방의 모든 구독자에게 전송
//        if (chatDto.getContent().startsWith("님이 입장하셨습니다.")) {
//            simpMessagingTemplate.convertAndSend("/sub/chat/room/" + roomNo, chatDto);
//        }
//    }
//}
