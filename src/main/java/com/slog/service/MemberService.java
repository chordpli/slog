package com.slog.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.slog.config.JwtUtils;
import com.slog.domain.dto.AuthenticationRequest;
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

	public String authentication(AuthenticationRequest request) {
		final UserDetails user = memberRepository.findByMemberEmail(request.getEmail())
			.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));

		if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
			);
			log.info("인증 성공: {}", request.getEmail());
			return jwtUtils.generateToken(user);
		}
		throw new IllegalStateException("인증에 실패하였습니다.");
	}
}