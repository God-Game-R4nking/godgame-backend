package com.example.godgame.gameroom;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// 레디스에서 사용할 게임룸 클래스 정의
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameRoom {
    private long gameRoomId;
    private String gameRoomName;
    private int maxPopulation = 8;
    private int currentPopulation = 1; // 생성자 포함
    private String gameRoomStatus = "wait";
    private String publicStatus = "public";
    private List<Long> memberIds;
    private long gameId; // 게임 ID 추가

    // 멤버 추가 메서드
    public boolean addMember(Long memberId) {
        if (currentPopulation < maxPopulation) {
            memberIds.add(memberId);
            currentPopulation++;
            return true; // 추가 성공
        }
        return false; // 최대 인원 초과
    }

    // 멤버 제거 메서드
    public boolean removeMember(Long memberId) {
        return memberIds.remove(memberId);
    }
}



