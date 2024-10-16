package com.example.godgame.member.service;

import com.example.godgame.auth.utils.JwtAuthorityUtils;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import com.example.godgame.helper.event.MemberRegistrationApplicationEvent;
import com.example.godgame.member.dto.MemberDto;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.repository.MemberRepository;
import com.example.godgame.ranking.entity.Ranking;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthorityUtils authorityUtils;
    private final RestTemplate restTemplate;


    public MemberService(MemberRepository memberRepository, ApplicationEventPublisher publisher, PasswordEncoder passwordEncoder, JwtAuthorityUtils authorityUtils, RestTemplate restTemplate) {
        this.memberRepository = memberRepository;
        this.publisher = publisher;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
        this.restTemplate = restTemplate;
    }

    public Member createMember(Member member) {
        verifyExistsId(member.getId());

        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getId());
        member.setRoles(roles);

        member.setRanking(new Ranking());

        if(!isNickNameAvailable(member.getNickName())){
            throw new BusinessLogicException(ExceptionCode.NICKNAME_EXISTS);
        }

        Member savedMember = memberRepository.save(member);

        publisher.publishEvent(new MemberRegistrationApplicationEvent(this, savedMember));
        return savedMember;
    }

    public Member updateMember(Member member, String id){
        Member findMember = findVerifiedMember(id);


        Optional.ofNullable(member.getNickName())
                .ifPresent(name -> findMember.setNickName(name));

        findMember.setModifiedAt(LocalDateTime.now());

        return memberRepository.save(findMember);
    }


    public void resetPassword(String password, long memberId){
        Member findMember = findVerifiedMemberId(memberId);
        findMember.setPassword(passwordEncoder.encode(password));
        findMember.setModifiedAt(LocalDateTime.now());
        memberRepository.save(findMember);
    }

    public Member updatePassword(String newPassword, String id){
        Member findMember = findVerifiedMember(id);

        if (findMember.getPassword() == null || findMember.getPassword().isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.PASSWORD_WRONG);
        }

        Optional.ofNullable(newPassword)
                .ifPresent(password -> findMember.setPassword(passwordEncoder.encode(newPassword)));

        findMember.setModifiedAt(LocalDateTime.now());
        return memberRepository.save(findMember);
    }

    public Member findMember(String id) {
        // TODO should business logic
        return findVerifiedMember(id);
    }

    public List<Member> findMembers(List<Long> memberIds){
        List<Member> findMembers = new ArrayList<>();

        for( Long memberId : memberIds){
            Member findMember = findVerifiedMemberId(memberId);
            findMembers.add(findMember);
        }

        return findMembers;
    }

    public Page<Member> findALlMember(int page, int size){
        return memberRepository.findAll(PageRequest.of(page,size, Sort.by("memberId").descending()));
    }


    public void deleteMember(String id){
        Member findMember = findVerifiedMember(id);
        findMember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);

        memberRepository.save(findMember);
    }

    public Member updateGameStatus(long memberId){
        Member findMember = findVerifiedMemberId(memberId);

        if(findMember.getMemberGameStatus().equals(Member.MemberGameStatus.MEMBER_WAIT)){
            findMember.setMemberGameStatus(Member.MemberGameStatus.MEMBER_PLAY);
        }else{
            findMember.setMemberGameStatus(Member.MemberGameStatus.MEMBER_WAIT);
        }
        return memberRepository.save(findMember);
    }

    public Long validateMember(Member fixMember) {
        // 사용자가 입력한 정보로 멤버 조회
        Optional<Member> optionalMember = memberRepository.findByMemberNameAndPhoneAndIdentificationNumber(
                fixMember.getMemberName(),
                fixMember.getPhone(),
                fixMember.getIdentificationNumber()
        );

        // 멤버가 존재하지 않을 경우 예외 발생
        Member member = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        // 멤버의 ID 반환
        return member.getMemberId();
    }



    public void verifyPassword(String id, String password, String newPassword){
        Member member = findVerifiedMember(id);

        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new BusinessLogicException(ExceptionCode.PASSWORD_WRONG);
        }
    }

    public void verifyExistsId(String id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }

    public void verifyExistsPhone(String phone) {
        Optional<Member> member = memberRepository.findByPhone(phone);
        if (member.isPresent()) throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }

    public boolean isNickNameAvailable(String nickName) {
        return !memberRepository.existsByNickName(nickName);
    }

    @Transactional(readOnly = true)
    public Member findVerifiedMember(String id){
        Optional<Member> optionalMember = memberRepository.findById(id);
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    @Transactional(readOnly = true)
    public Member findVerifiedMemberId(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    @Transactional(readOnly = true)
    public Member findVerifiedNickName(String nickName){
        Optional<Member> optionalMember = memberRepository.findByNickName(nickName);
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }


}

