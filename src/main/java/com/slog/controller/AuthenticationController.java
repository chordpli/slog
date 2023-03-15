package com.slog.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slog.domain.Response;
import com.slog.domain.dto.AuthenticationRequest;
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
		return Response.success(memberService.authentication(request));
	}
}
