package com.example.godgame.ban.service;


import com.example.godgame.ban.entity.Ban;
import com.example.godgame.ban.repository.BanRepository;
import com.example.godgame.board.entity.Board;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.repository.MemberRepository;
import com.example.godgame.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class BanService {

    private final BanRepository banRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public BanService(BanRepository banRepository, MemberService memberService, MemberRepository memberRepository) {
        this.banRepository = banRepository;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Ban createBan(Ban ban, Authentication authentication){

        Member member = memberService.findVerifiedMember(authentication.getName());
        ban.setMember(member);
        long memberId = member.getMemberId();
        long banMemberId = ban.getBanMember().getMemberId();

        verifyExistsBan(memberId, banMemberId);

        return banRepository.save(ban);
    }

    @Transactional(readOnly = true)
    public Page<Ban> findBans(long memberId, int page, int size, Authentication authentication) {
        authenticateBanByMemberId(memberId, authentication);
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member member = optionalMember.orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return banRepository.findByMember(member, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public void deleteBan(long banMemberId, Authentication authentication) {

        String memberName = authentication.getName();
        Optional<Member> optionalMember = memberRepository.findById(memberName);
        Member member = optionalMember.orElseThrow(()-> new BusinessLogicException(ExceptionCode.BAN_DELETE_NOT_ALLOWED));
        Optional<Member> optionalBanMember = memberRepository.findById(banMemberId);
        Member banMember = optionalBanMember.orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Ban ban = new Ban();
        ban.setMember(member);
        ban.setBanMember(banMember);

        Optional<Ban> optionalBan = banRepository.findByMemberAndBanMember(member, banMember);
        Ban deleteBan = optionalBan.orElseThrow(()-> new BusinessLogicException(ExceptionCode.BAN_NOT_FOUND));

        banRepository.delete(deleteBan);
    }

    @Transactional(readOnly = true)
    public void verifyExistsBan(long memberId, long banMemberId){

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member member = optionalMember.orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Optional<Member> optionalBanMember = memberRepository.findById(banMemberId);
        Member banMember = optionalBanMember.orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Optional<Ban> ban = banRepository.findByMemberAndBanMember(member, banMember);

        if (ban.isPresent()) throw new BusinessLogicException(ExceptionCode.BAN_EXISTS);
    }

    @Transactional(readOnly = true)
    public void authenticateBanByMemberId(long memberId, Authentication authentication) {

        String memberName = authentication.getName();
        Member findMember = memberService.findVerifiedMemberId(memberId);

        if(!findMember.getId().equals(memberName)) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOWED);
        }
    }

}
