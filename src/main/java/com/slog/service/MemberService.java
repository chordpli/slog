package com.slog.service;

import com.slog.domain.dto.MemberDto;
import com.slog.domain.dto.MemberJoinRequest;
import com.slog.domain.dto.MemberLoginRequest;
import com.slog.domain.entity.Member;
import com.slog.repository.MemberRepository;
import com.slog.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret}")
    private String secretKey;

    private long expireTimeMs = 1000 * 60 * 60;

    public String login(MemberLoginRequest dto) {
        Member member = memberRepository.findByMemberEmail(dto.getMemberEmail())
                .orElseThrow(() -> new RuntimeException());

        if (!encoder.matches(dto.getMemberPassword(), member.getMemberPassword())) {
            throw new RuntimeException();
        }
        return JwtUtil.createJwt(member.getMemberNickname(), member.getMemberEmail(), member.getMemberStatus(), secretKey, expireTimeMs);
    }

    public MemberDto join(MemberJoinRequest dto) {
        memberRepository.findByMemberEmail(dto.getMemberEmail())
                .ifPresent(member ->{
                    throw new RuntimeException();
                });
        Member savedMember = memberRepository.save(dto.toEntity(encoder.encode(dto.getMemberPassword()), "authKey"));

        return MemberDto.builder()
                .memberEmail(savedMember.getMemberEmail())
                .memberNickname(savedMember.getMemberNickname())
                .memberPassword(savedMember.getMemberPassword())
                .memberAuthKey(savedMember.getMemberAuthKey())
                .memberStatus(savedMember.getMemberStatus())
                .build();
    }
}
