package com.slog.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slog.config.JwtUtils;
import com.slog.domain.dto.member.AuthenticationRequest;
import com.slog.domain.dto.member.JoinRequest;
import com.slog.domain.dto.member.JoinResponse;
import com.slog.exception.ErrorCode;
import com.slog.exception.SlogAppException;
import com.slog.repository.MemberRepository;
import com.slog.service.MemberService;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@WithMockUser
class AuthenticationControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	MemberService memberService;

	@MockBean
	MemberRepository memberRepository;

	@MockBean
	JwtUtils jwtUtils;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
			.apply(documentationConfiguration(restDocumentation))
			.build();
	}

	@Test
	void join_Success() throws Exception {
		JoinRequest request = JoinRequest.builder()
			.memberEmail("test@email.com")
			.memberNickname("test")
			.memberPassword("Password123$")
			.build();

		JoinResponse response = JoinResponse.builder()
			.memberNo(1L)
			.memberEmail(request.getMemberEmail())
			.memberNickname(request.getMemberNickname())
			.build();

		given(memberService.join(any())).willReturn(response);

		mockMvc.perform(post("/api/v1/auth/join")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.resultCode").value("SUCCESS"))
			.andExpect(jsonPath("$.data.memberNo").value(1L))
			.andExpect(jsonPath("$.data.memberEmail").value("test@email.com"))
			.andExpect(jsonPath("$.data.memberNickname").value("test"))
			.andDo(print())
			.andDo(document("auth/join-success",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("memberEmail").description("회원 이메일"),
					fieldWithPath("memberNickname").description("회원 닉네임"),
					fieldWithPath("memberPassword").description("회원 비밀번호")
				),
				responseFields(
					fieldWithPath("resultCode").description("결과 코드"),
					fieldWithPath("data.memberNo").description("회원 번호"),
					fieldWithPath("data.memberEmail").description("회원 이메일"),
					fieldWithPath("data.memberNickname").description("회원 닉네임")
				)));
		;
	}

	@Test
	void join_Fail_Duplicated_Member() throws Exception {
		JoinRequest request = JoinRequest.builder()
			.memberEmail("test@email.com")
			.memberNickname("test")
			.memberPassword("Password123$")
			.build();

		given(memberService.join(any())).willThrow(
			new SlogAppException(ErrorCode.DUPLICATED_MEMBER_INFO, ErrorCode.DUPLICATED_MEMBER_INFO.getMessage()));

		mockMvc.perform(post("/api/v1/auth/join")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(request)))
			.andExpect(status().isConflict())
			.andDo(print())
			.andDo(document("auth/join-fail-duplicated-member",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("memberEmail").description("회원 이메일"),
					fieldWithPath("memberNickname").description("회원 닉네임"),
					fieldWithPath("memberPassword").description("회원 비밀번호")
				),
				responseFields(
					fieldWithPath("resultCode").description("결과 코드"),
					fieldWithPath("data.errorCode").description("에러 코드"),
					fieldWithPath("data.message").description("에러 메시지")
				)));
	}

	@Test
	void authenticate_Success() throws Exception {
		AuthenticationRequest request = AuthenticationRequest.builder()
			.email("test@email.com")
			.password("Password123$")
			.build();

		String token = "some_valid_token";

		given(memberService.authentication(any())).willReturn(token);

		mockMvc.perform(post("/api/v1/auth/authenticate")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.resultCode").value("SUCCESS"))
			.andExpect(jsonPath("$.data").value(token))
			.andDo(print())
			.andDo(document("auth/authenticate-success",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("email").description("이메일"),
					fieldWithPath("password").description("비밀번호")
				),
				responseFields(
					fieldWithPath("resultCode").description("결과 코드"),
					fieldWithPath("data").description("JWT 토큰")
				)));
	}

	@Test
	void authenticate_Fail_Inconsistent_Information() throws Exception {
		AuthenticationRequest request = AuthenticationRequest.builder()
			.email("test@email.com")
			.password("Password123$")
			.build();

		given(memberService.authentication(any())).willThrow(
			new SlogAppException(ErrorCode.INCONSISTENT_INFORMATION, ErrorCode.INCONSISTENT_INFORMATION.getMessage()));

		mockMvc.perform(post("/api/v1/auth/authenticate")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(request)))
			.andExpect(status().isConflict())
			.andDo(print())
			.andDo(document("auth/authenticate-fail-inconsistent-information",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("email").description("이메일"),
					fieldWithPath("password").description("비밀번호")
				),
				responseFields(
					fieldWithPath("resultCode").description("결과 코드"),
					fieldWithPath("data.errorCode").description("에러 코드"),
					fieldWithPath("data.message").description("에러 메시지")
				)));
	}
}