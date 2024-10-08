package com.example.godgame.ban.mapper;

import com.example.godgame.ban.dto.BanDto;
import com.example.godgame.ban.entity.Ban;
import com.example.godgame.board.dto.BoardDto;
import com.example.godgame.comment.dto.CommentDto;
import com.example.godgame.comment.entity.Comment;
import com.example.godgame.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BanMapper {
//    @Mapping(source = "memberId",target = "member.memberId")
//    @Mapping(source = "banMemberId", target = "banMember.memberId")
    default Ban banPostDtoToBan(BanDto.Post postDto){
        Ban ban = new Ban();
        Member banMember = new Member();
        banMember.setMemberId(postDto.getBanMemberId());
        ban.setBanMember(banMember);
        return ban;
    }
    @Mapping(source = "member.memberId", target = "memberId")
    @Mapping(source = "banMember.memberId", target = "banMemberId")
    BanDto.Response banToResponseDto(Ban ban);
    @Mapping(source = "member.memberId", target = "memberId")
    List<BanDto.Response> bansToResponseDtos(List<Ban> bans);
}
