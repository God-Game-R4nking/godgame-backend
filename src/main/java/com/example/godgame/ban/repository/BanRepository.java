package com.example.godgame.ban.repository;

import com.example.godgame.ban.entity.Ban;
import com.example.godgame.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BanRepository extends JpaRepository<Ban, Long> {

    Page<Ban> findByMember(Member member, Pageable pageable);

    Optional<Ban> findByBanMember(Member banMember);

    Optional<Ban> findByMemberAndBanMember(Member member, Member banMember);
}