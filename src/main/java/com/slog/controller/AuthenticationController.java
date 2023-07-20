package com.slog.controller;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slog.domain.Response;
import com.slog.domain.dto.member.AuthenticationRequest;
import com.slog.domain.dto.member.JoinRequest;
import com.slog.domain.dto.member.JoinResponse;
import com.slog.exception.ErrorCode;
import com.slog.exception.SlogAppException;
import com.slog.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
	private final MemberService memberService;

	@PostMapping("/authenticate")
	public Response<String> authenticate(@RequestBody final @Valid AuthenticationRequest request) {
		try {
			return Response.success(memberService.authentication(request));
		} catch (SlogAppException e) {
			throw new SlogAppException(ErrorCode.INCONSISTENT_INFORMATION, ErrorCode.INCONSISTENT_INFORMATION.getMessage());
		}
	}

	@PostMapping("/join")
	public Response<JoinResponse> join(@RequestBody final @Valid JoinRequest request){
		try {
			return Response.success(memberService.join(request));
		} catch (DataIntegrityViolationException | SlogAppException e) {
			throw new SlogAppException(ErrorCode.DUPLICATED_MEMBER_INFO, ErrorCode.DUPLICATED_MEMBER_INFO.getMessage());
		}
	}
}
