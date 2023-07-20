package com.slog.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.slog.config.JwtUtils;
import com.slog.domain.dto.member.AuthenticationRequest;
import com.slog.domain.dto.member.JoinRequest;
import com.slog.domain.dto.member.JoinResponse;
import com.slog.domain.entity.Blog;
import com.slog.domain.entity.Member;
import com.slog.exception.ErrorCode;
import com.slog.exception.SlogAppException;
import com.slog.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
	private final AuthenticationManager authenticationManager;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final BlogService blogService;

	public String authentication(AuthenticationRequest request) {
		final UserDetails user = memberRepository.findByMemberEmail(request.getEmail())
			.orElseThrow(() -> new SlogAppException(ErrorCode.MEMBER_NOT_FOUND, "존재하지 않는 회원입니다."));

		if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
			);
			log.info("인증 성공: {}", request.getEmail());
			return jwtUtils.generateToken(user);
		}
		throw new SlogAppException(ErrorCode.INCONSISTENT_INFORMATION, ErrorCode.INCONSISTENT_INFORMATION.getMessage());
	}

	public JoinResponse join(JoinRequest request) {
		// 중복된 이메일이나 닉네임이 이미 존재하는 경우 예외를 발생시킴
		if (memberRepository.existsByMemberEmail(request.getMemberEmail())) {
			throw new SlogAppException(ErrorCode.DUPLICATED_MEMBER_EMAIL, ErrorCode.DUPLICATED_MEMBER_EMAIL.getMessage());
		}
		if (memberRepository.existsByMemberNickname(request.getMemberNickname())) {
			throw new SlogAppException(ErrorCode.DUPLICATED_MEMBER_NICKNAME, ErrorCode.DUPLICATED_MEMBER_NICKNAME.getMessage());
		}

		// todo: 블로그 생성 로직
		Blog blog = blogService.createBlog(request.getMemberNickname());
		Member member = memberRepository.save(JoinRequest.toEntity(request, passwordEncoder, blog));

		return JoinResponse.of(member);
	}

	public void testQueryDsl() {
		memberRepository.testQueryDsl();
	}
}