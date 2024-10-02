package com.example.godgame.ban.service;


import com.example.godgame.ban.entity.Ban;
import com.example.godgame.ban.repository.BanRepository;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import com.example.godgame.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BanService {

    private final BanRepository banRepository;

    public BanService(BanRepository banRepository) {
        this.banRepository = banRepository;
    }

    public Ban createBan(Ban ban){
        
        return banRepository.save(ban);
    }

    public Ban findVerifiedBan(long memberId, long banMemberId){
        Optional<Ban> optionalBan = banRepository.findByMember_MemberIdAndBanMember_MemberId(memberId, banMemberId);
        Ban findBan =  optionalBan.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.BAN_NOT_FOUND));
        return findBan;
    }

    public void verifyExistsBan(long memberId, long banMemberId) {
        Optional<Ban> ban = banRepository.findByMember_MemberIdAndBanMember_MemberId(memberId, banMemberId);
        if (ban.isPresent()) throw new BusinessLogicException(ExceptionCode.BAN_EXISTS);
    }


}
