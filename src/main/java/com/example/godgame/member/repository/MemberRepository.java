package com.example.godgame.member.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.godgame.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(String id);
    Optional<Member> findByPhone(String phone);
    Page<Member> findAll(Pageable pageable);
    boolean existsByNickName(String nickName);

    Optional<Member> findByNickName(String nickName);
    Optional<Member> findByMemberNameAndPhoneAndIdentificationNumber(String memberName, String phone, String identificationNumber);
}
