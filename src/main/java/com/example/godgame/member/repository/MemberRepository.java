package com.example.godgame.member.repository;

import com.example.godgame.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(String id);
    boolean existsByNickName(String nickName);
}
