package com.example.godgame.ban.repository;

import com.example.godgame.ban.entity.Ban;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BanRepository extends JpaRepository<Ban, Long> {

    Optional<Ban> findByMember_MemberIdAndBanMember_MemberId(long memberId, long banMemberId);


}
