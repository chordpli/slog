package com.slog.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slog.config.JwtUtils;
import com.slog.domain.dto.blog.RenameBlogRequest;
import com.slog.domain.dto.blog.RenameBlogResponse;
import com.slog.exception.ErrorCode;
import com.slog.exception.SlogAppException;
import com.slog.repository.MemberRepository;
import com.slog.service.BlogService;

@WebMvcTest(BlogRestController.class)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@WithMockUser
class BlogRestControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	BlogService blogService;

	@MockBean
	MemberRepository memberRepository;

	@MockBean
	JwtUtils jwtUtils;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
			.apply(documentationConfiguration(restDocumentation))
			.apply(springSecurity())
			.build();
	}

	@Test
	void rename_success() throws Exception {
		RenameBlogRequest request = RenameBlogRequest
			.builder()
			.name("수정된 블로그 이름입니다.")
			.build();

		RenameBlogResponse response = RenameBlogResponse
			.builder()
			.blogId(1L)
			.blogName("수정된 블로그 이름입니다.")
			.build();

		long blogId = 1L;

		given(blogService.renameBlog(any(), any(), any())).willReturn(response);

		mockMvc.perform(post("/api/v1/blog/rename/" + blogId)
				.with(csrf())
				.header(HttpHeaders.AUTHORIZATION, "TOKEN")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.resultCode").value("SUCCESS"))
			.andExpect(jsonPath("$.data.blogId").value(1L))
			.andExpect(jsonPath("$.data.blogName").value("수정된 블로그 이름입니다."))
			.andDo(print())
			.andDo(document("blog/rename/rename-success",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("name").description("수정된 블로그 이름입니다.")
				),
				responseFields(
					fieldWithPath("resultCode").description("결과 코드"),
					fieldWithPath("data.blogId").description("블로그 번호"),
					fieldWithPath("data.blogName").description("블로그 이름")
				)));
	}

	@Test
	void rename_fail_invalidRequest() throws Exception {
		RenameBlogRequest request = RenameBlogRequest
			.builder()
			.name("블로그 제목이 공백입니다.") // 빈 블로그 이름
			.build();

		RenameBlogRequest request2 = RenameBlogRequest
			.builder()
			.name("블로그 제목이 25자 초과입니다.") // 빈 블로그 이름
			.build();

		long blogId = 1L;

		given(blogService.renameBlog(any(), any(), any())).willThrow(
			new SlogAppException(ErrorCode.INPUTS_THAT_DO_NOT_MEET_REQUIREMENTS,
				ErrorCode.INPUTS_THAT_DO_NOT_MEET_REQUIREMENTS.getMessage()));

		mockMvc.perform(post("/api/v1/blog/rename/" + blogId)
				.with(csrf())
				.header(HttpHeaders.AUTHORIZATION, "TOKEN")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(request)))
			.andExpect(status().isBadRequest())
			.andDo(print())
			.andDo(document("blog/rename/rename-fail-invalid-request",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("name").description("수정된 블로그 이름입니다.")
				),
				responseFields(
					fieldWithPath("resultCode").description("결과 코드"),
					fieldWithPath("data.errorCode").description("에러 코드"),
					fieldWithPath("data.message").description("오류 메시지")
				)));
		;
	}

	@Test
	void rename_fail_unauthorized() throws Exception {
		RenameBlogRequest request = RenameBlogRequest
			.builder()
			.name("적합하지 않은 사용자입니다.")
			.build();

		long blogId = 1L;

		given(blogService.renameBlog(any(), any(), any())).willThrow(
			new SlogAppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

		mockMvc.perform(post("/api/v1/blog/rename/" + blogId)
				.with(csrf())
				.header(HttpHeaders.AUTHORIZATION, "TOKEN")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(request)))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.resultCode").value("UNAUTHORIZED"))
			.andExpect(jsonPath("$.data.message").value("사용자가 권한이 없습니다."))
			.andDo(print())
			.andDo(document("blog/rename/rename-fail-unauthorized",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("name").description("수정된 블로그 이름입니다.")
				),
				responseFields(
					fieldWithPath("resultCode").description("결과 코드"),
					fieldWithPath("data.errorCode").description("에러 코드"),
					fieldWithPath("data.message").description("오류 메시지")
				)));
	}

	@Test
	void rename_fail_notFound() throws Exception {
		RenameBlogRequest request = RenameBlogRequest
			.builder()
			.name("해당 블로그가 존재하지 않습니다.")
			.build();

		long blogId = 1L;

		given(blogService.renameBlog(any(), any(), any())).willThrow(new SlogAppException(ErrorCode.NOT_FOUND, ErrorCode.NOT_FOUND.getMessage()));

		mockMvc.perform(post("/api/v1/blog/rename/" + blogId)
				.with(csrf())
				.header(HttpHeaders.AUTHORIZATION, "TOKEN")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(request)))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.resultCode").value("NOT_FOUND"))
			.andExpect(jsonPath("$.data.message").value("해당 페이지를 찾을 수 없습니다."))
			.andDo(print())
			.andDo(document("blog/rename/rename-fail-not-found",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("name").description("수정된 블로그 이름입니다.")
				),
				responseFields(
					fieldWithPath("resultCode").description("결과 코드"),
					fieldWithPath("data.errorCode").description("에러 코드"),
					fieldWithPath("data.message").description("오류 메시지")
				)));
	}
}