package com.example.godgame.member.mapper;

import com.example.godgame.member.dto.MemberDto;
import com.example.godgame.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    default Member memberPostToMember(MemberDto.Post requestBody){
        Member member = new Member();
        member.setId(requestBody.getId());
        member.setMemberName(requestBody.getMemberName());
        member.setNickName(requestBody.getNickName());
        member.setPassword(requestBody.getPassword());
        member.setPhone(requestBody.getPhone());
        member.setIdentificationNumber(requestBody.getIdentificationNumber());

        return member;
    }

    Member memberPatchToMember(MemberDto.Patch requestBody);

    default Member memberToMemberPasswordGet(MemberDto.PasswordGet requestBody){
        Member member = new Member();
        member.setMemberName(requestBody.getMemberName());
        member.setIdentificationNumber(requestBody.getIdentificationNumber());
        member.setPhone(requestBody.getPhone());

        return member;
    }

    default MemberDto.Response memberToMemberResponse(Member member){
        MemberDto.Response response = new MemberDto.Response();
        response.setMemberId(member.getMemberId());
        response.setMemberStatus(member.getMemberStatus());
        response.setMemberName(member.getMemberName());
        response.setNickName(member.getNickName());
        response.setCreatedAt(member.getCreatedAt());
        response.setModifiedAt(member.getModifiedAt());
        response.setTotalPoint(member.getTotalPoint());

        return response;
    }

    List<MemberDto.Response> memberToMemberResponseDtos(List<Member> members);
}
