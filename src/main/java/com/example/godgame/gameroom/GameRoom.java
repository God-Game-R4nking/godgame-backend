package com.example.godgame.gameroom;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// 레디스에서 사용할 게임룸 클래스 정의
@Getter
@Setter
public class GameRoom {
    private long gameRoomId;
    private String gameRoomName;
    private String gameRoomPassword; // 비공개 방의 경우 입력해야 함
    private int maxPopulation = 8;
    private int currentPopulation = 1; // 생성자 포함
    private String gameRoomStatus = "대기중";
    private String publicStatus = "공개"; // 기본은 공개
    private List<Long> memberIds;
    private long gameId; // 게임 ID 추가


    public GameRoom(String gameRoomName, String gameRoomPassword, String publicStatus, long memberId, long gameId) {
        this.gameRoomName = gameRoomName;
        this.gameRoomPassword = gameRoomPassword;
        this.publicStatus = publicStatus;
        this.memberIds = new ArrayList<>();
        this.memberIds.add(memberId); // 생성자 ID 추가
        this.gameId = gameId; // 게임 ID 설정
    }

    public GameRoom(Long gameRoomId, String gameRoomName, String gameRoomPassword, String publicStatus, long memberId, long gameId) {
        this.gameRoomId = gameRoomId;
        this.gameRoomName = gameRoomName;
        this.gameRoomPassword = gameRoomPassword;
        this.publicStatus = publicStatus;
        this.memberIds = new ArrayList<>();
        this.memberIds.add(memberId);
        this.gameId = gameId;
    }



    // 생성자 (비밀번호가 필요 없는 경우)
    public GameRoom(String gameRoomName, String publicStatus, long memberId, long gameId) {
        this(gameRoomName, null, publicStatus, memberId, gameId); // 비밀번호는 null로 설정
    }

    public GameRoom() {
        this.memberIds = new ArrayList<>(); // 기본 초기화
    }

    // 멤버 추가 메서드
    public boolean addMember(long memberId) {
        if (currentPopulation < maxPopulation) {
            memberIds.add(memberId);
            currentPopulation++;
            return true; // 추가 성공
        }
        return false; // 최대 인원 초과
    }

    // 멤버 제거 메서드
    public boolean removeMember(long memberId) {
        if (memberIds.remove(memberId)) {
            currentPopulation--;
            return true; // 제거 성공
        }
        return false; // 해당 멤버가 존재하지 않음
    }
}



