package com.example.godgame.member.controller;

import com.example.godgame.dto.MultiResponseDto;
import com.example.godgame.dto.SingleResponseDto;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import com.example.godgame.member.dto.MemberDto;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.mapper.MemberMapper;
import com.example.godgame.member.service.MemberService;
import com.example.godgame.ranking.entity.Ranking;
import com.example.godgame.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/members")
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/members";
    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {

        Member member = mapper.memberPostToMember(requestBody);
        Member createdMember = memberService.createMember(member);

        URI location = UriCreator.createUri("/members", createdMember.getMemberId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(
            @PathVariable("member-id") @Positive long memberId,
            @Valid @RequestBody MemberDto.Patch requestBody,
            Authentication authentication) {
        requestBody.setMemberId(memberId);
        String id = authentication.getName();

        Member member = memberService.updateMember(mapper.memberPatchToMember(requestBody), id);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponse(member)), HttpStatus.OK);
    }

    @PatchMapping("/{member-id}/password")
    public ResponseEntity patchMemberPassword(
            @Valid @RequestBody MemberDto.PasswordPatch requestBody,
            Authentication authentication) {
        String id = authentication.getName();
        memberService.verifyPassword(id, requestBody.getPassword(), requestBody.getNewPassword());
        Member member = memberService.updatePassword(requestBody.getNewPassword(), id);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponse(member)), HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(
            @PathVariable("member-id") @Positive long memberId, Authentication authentication) {

        String email = authentication.getName();
        Member member = memberService.findMember(memberId, email);


        return new ResponseEntity(new SingleResponseDto<>(mapper.memberToMemberResponse(member)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<Member> pageMembers = memberService.findALlMember(page - 1, size);
        List<Member> members = pageMembers.getContent();
        return new ResponseEntity(new MultiResponseDto<>(mapper.memberToMemberResponseDtos(members), pageMembers), HttpStatus.OK);
    }


    @GetMapping("/check-nickName")
    public ResponseEntity nickNameAvailability(@RequestBody MemberDto.NickName requestBody) {
        //매퍼로 매핑 requestBody -> member.nickName으로 바꿔줘야함
        //nickNameDtoToNickName
        boolean isAvailable = memberService.isNickNameAvailable(requestBody.getNickName());
        MemberDto.Check responseDto = new MemberDto.Check(isAvailable);

        return new ResponseEntity<>(
                new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Long> resetPassword(@RequestBody MemberDto.PasswordGet passwordGet) {
        Long memberId = memberService.validateMember(mapper.memberPasswordGetDtoToMember(passwordGet));
        return ResponseEntity.ok(memberId); // 멤버 ID 반환
    }

    @PatchMapping("/reset-password/{member-id}")
    public void patchResetPassword(
            @Valid @RequestBody MemberDto.PasswordReset requestBody,
            @PathVariable ("member-id") long memberId) {
        memberService.resetPassword(requestBody.getPassword(), memberId);
    }
}
