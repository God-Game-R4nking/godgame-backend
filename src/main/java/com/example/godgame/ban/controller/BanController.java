package com.example.godgame.ban.controller;

import com.example.godgame.ban.dto.BanDto;
import com.example.godgame.ban.entity.Ban;
import com.example.godgame.ban.mapper.BanMapper;
import com.example.godgame.ban.service.BanService;
import com.example.godgame.dto.SingleResponseDto;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.service.MemberService;
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
@RequestMapping("/bans")
public class BanController {

    private final static String BAN_DEFAULT_URL = "/bans";
    private final BanService banService;
    private final BanMapper mapper;
    private final MemberService memberService;

    public BanController(BanService banService, BanMapper mapper, MemberService memberService) {
        this.banService = banService;
        this.mapper = mapper;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity postBan(@Valid @RequestBody BanDto.Post requestBody, Authentication authentication) {

        Ban ban = mapper.banPostDtoToBan(requestBody);
        Ban createdBan = banService.createBan(ban, authentication);
        URI location = UriCreator.createUri(BAN_DEFAULT_URL, createdBan.getBanId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getBans(
            @PathVariable("member-id") @Positive long memberId,
            @Positive @RequestParam int page,
            @Positive @RequestParam int size,
            Authentication authentication) {

        Page<Ban> pageBans = banService.findBans(memberId,  page - 1, size, authentication);

        List<Ban> findBans = pageBans.getContent();

        return new ResponseEntity(new SingleResponseDto<>(mapper.bansToResponseDtos(findBans)), HttpStatus.OK);
    }

    @DeleteMapping("/{ban-member-id}")
    public ResponseEntity deleteBan(@PathVariable("ban-member-id") long banMemberId, Authentication authentication) {

        banService.deleteBan(banMemberId, authentication);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
