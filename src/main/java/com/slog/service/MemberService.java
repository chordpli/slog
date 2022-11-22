package com.slog.service;

import com.slog.domain.dto.MemberDto;
import com.slog.domain.entity.Member;
import com.slog.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> login(MemberDto memberDto) {
        return memberRepository.findByMemberEmailAndMemberPassword(memberDto.getMemberEmail(), memberDto.getMemberPassword());
    }
}
