package com.example.godgame.member.repository;

import com.example.godgame.member.entity.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MemberRepository extends MongoRepository<Member, Long> {
}