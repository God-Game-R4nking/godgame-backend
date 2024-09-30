package com.example.godgame.member.service;

import com.example.godgame.auth.utils.JwtAuthorityUtils;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import com.example.godgame.helper.event.MemberRegistrationApplicationEvent;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.repository.MemberRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthorityUtils authorityUtils;

    public MemberService(MemberRepository memberRepository, ApplicationEventPublisher publisher, PasswordEncoder passwordEncoder, JwtAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.publisher = publisher;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    public Member createMember(Member member) {
        verifyExistsId(member.getId());
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getId());
        member.setRoles(roles);

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

    public Member findMember(long memberId, String email) {
        // TODO should business logic
        //throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
//        findVerifiedMember(memberId);
        return findVerifiedMember(email);
    }




    public void deleteMember(String id){
        Member findMember = findVerifiedMember(id);
        findMember.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);

        memberRepository.save(findMember);
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


}

