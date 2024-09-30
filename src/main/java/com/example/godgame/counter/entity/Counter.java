package com.example.godgame.counter.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


// 수동으로 Id를 관리하기 위해서 몽고 DB에서 사용할 카운터 클래스를 설정
@Getter
@Setter
@Document(collection = "counters")
public class Counter {
    @Id
    private String id; // 카운터 ID
    private long seq;  // 현재 시퀀스 번호

    public Counter() {}

    public Counter(String id, long seq) {
        this.id = id;
        this.seq = seq;
    }

}
