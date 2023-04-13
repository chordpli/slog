package com.slog.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.slog.config.JwtUtils;
import com.slog.domain.dto.member.AuthenticationRequest;
import com.slog.domain.dto.member.JoinRequest;
import com.slog.domain.dto.member.JoinResponse;
import com.slog.domain.entity.Member;
import com.slog.exception.SlogAppException;
import com.slog.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtUtils jwtUtils;

	private AuthenticationRequest authenticationRequest;
	private JoinRequest joinRequest;
	private UserDetails userDetails;
	private Member member;

	@BeforeEach
	void setUp() {
		authenticationRequest = new AuthenticationRequest("test@example.com", "Password123$");
		joinRequest = new JoinRequest("test@example.com", "Password123$", "nickname");
		userDetails = mock(UserDetails.class);
		member = mock(Member.class);
	}

	@Test
	void authentication_Success() {
		doReturn(Optional.of(userDetails)).when(memberRepository).findByMemberEmail(authenticationRequest.getEmail());
		given(passwordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword()))
			.willReturn(true);
		given(jwtUtils.generateToken(userDetails)).willReturn("sample_token");

		String result = memberService.authentication(authenticationRequest);

		verify(authenticationManager).authenticate(
			new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
				authenticationRequest.getPassword())
		);
		assertEquals("sample_token", result);
	}

	@Test
	void authentication_Fail() {
		given(memberRepository.findByMemberEmail(authenticationRequest.getEmail()))
			.willReturn(Optional.ofNullable(member));
		given(passwordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword()))
			.willReturn(false);

		assertThrows(SlogAppException.class, () -> memberService.authentication(authenticationRequest));
	}

	@Test
	void authentication_Fail_Member_Not_Found() {
		doReturn(Optional.empty()).when(memberRepository).findByMemberEmail(authenticationRequest.getEmail());
		assertThrows(SlogAppException.class, () -> memberService.authentication(authenticationRequest));
	}

	@Test
	void join_Fail_DuplicatedEmail() {
		given(memberRepository.existsByMemberEmail(joinRequest.getMemberEmail())).willReturn(true);

		assertThrows(SlogAppException.class, () -> memberService.join(joinRequest));
	}

	@Test
	void join_Fail_DuplicatedNickname() {
		given(memberRepository.existsByMemberEmail(joinRequest.getMemberEmail())).willReturn(false);
		given(memberRepository.existsByMemberNickname(joinRequest.getMemberNickname())).willReturn(true);

		assertThrows(SlogAppException.class, () -> memberService.join(joinRequest));
	}
}