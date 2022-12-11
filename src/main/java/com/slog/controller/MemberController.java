package com.slog.controller;

import com.slog.domain.Response;
import com.slog.domain.dto.MemberDto;
import com.slog.domain.dto.MemberJoinRequest;
import com.slog.domain.dto.MemberLoginRequest;
import com.slog.domain.dto.MemberLoginResponse;
import com.slog.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/members")
@Slf4j
public class MemberController {
    private final MemberService memberService;


    @PostMapping("/join")
    public Response<MemberLoginResponse> join(@RequestBody MemberJoinRequest dto) {
        MemberDto member = memberService.join(dto);
        return Response.success(
                MemberLoginResponse.builder()
                        .memberEmail(member.getMemberEmail())
                        .memberNickname(member.getMemberNickname())
                        .memberStatus(member.getMemberStatus())
                        .build()
        );
    }

    @PostMapping("/login")
    public Response<String> login(@RequestBody  MemberLoginRequest dto) {
        log.info("memberEmail = {}", dto.getMemberEmail());
        String token = memberService.login(dto);
        return Response.success(token);
    }

    @PostMapping("/test")
    public Response<String> test(Authentication authentication) {
        String name = authentication.getName();
        log.info("userName = {}", name);
        return Response.success(name);
    }
}
