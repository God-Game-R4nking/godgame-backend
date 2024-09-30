package com.example.godgame.gameroom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "gameRooms")
public class GameRoom {
    @Id
    private long gameRoomId;  // 카운터 서비스를 사용해서 String 이 아닌 long 타입으로 변환 해줌  JPA와 ID 타입을 맞춰주기 위해서
    private String gameRoomName;
    private long  gameId;
    private List<Long> members = new ArrayList<>();
    private int currentMembers = 0;

    public void incrementMembers() {
        this.currentMembers++;
    }

    public void decrementMembers() {
        this.currentMembers--;
    }

    public void addMember(Long memberId) {
        if (currentMembers < 8) { // 최대 인원 수 확인
            members.add(memberId); // 멤버 ID 추가
            incrementMembers(); // 인원 수 증가
        } else {
            throw new IllegalStateException("Maximum capacity reached."); // 최대 인원 초과 예외
        }
    }

    public void removeMember(Long memberId) {
        if (members.remove(memberId)) {
            decrementMembers(); // 인원 수 감소
        }
    }
}
