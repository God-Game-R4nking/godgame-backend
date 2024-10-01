//package com.example.godgame.event;
//
//import com.example.godgame.gameroom.GameRoom;
//import com.example.godgame.history.entity.GameHistory;
//import com.example.godgame.history.repository.GameHistoryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Component
//public class GameEventListener {
//
//    @Autowired
//    private GameHistoryRepository historyRepository; // HistoryRepository 주입
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate; // RedisTemplate 주입
//
//    @EventListener
//    public void handleGameEvent(GameEvent event) {
//        if ("START".equals(event.getAction())) {
//            // 게임 시작 시 히스토리 기록 로직 추가 가능
//            // 예를 들어, 게임 시작 시 필요한 작업을 여기에 추가할 수 있습니다.
//        } else if ("END".equals(event.getAction())) {
//            // 게임 종료 시 처리 로직
//            GameHistory history = new GameHistory();
//            history.setGameId(event.getGameId()); // 게임 ID 설정
//            history.setMemberId(event.getMemberId()); // 멤버 ID 설정 (필요 시)
//            history.setScore(0); // 점수 처리 로직 추가 필요
//            history.setCreatedAt(LocalDateTime.now());
//            history.setModifiedAt(LocalDateTime.now());
//            historyRepository.save(history); // 점수 히스토리에 저장
//
//            GameRoom gameRoom = (GameRoom) redisTemplate.opsForValue().get(event.getRoomName());
//            if (gameRoom != null) {
//                gameRoom.setCurrentPopulation(0); // 모든 멤버가 나감
//                redisTemplate.opsForValue().set(event.getRoomName(), gameRoom); // 업데이트
//
//                // 방 삭제 체크
//                removeGameRoomIfEmpty(event.getRoomName());
//            }
//        }
//    }
//
//    private void removeGameRoomIfEmpty(String roomName) {
//        GameRoom gameRoom = (GameRoom) redisTemplate.opsForValue().get(roomName);
//        if (gameRoom != null && gameRoom.getCurrentPopulation() == 0) {
//            redisTemplate.delete(roomName); // 방 삭제
//        }
//    }
//}