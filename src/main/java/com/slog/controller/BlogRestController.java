package com.slog.controller;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slog.domain.Response;
import com.slog.domain.dto.blog.RenameBlogRequest;
import com.slog.domain.dto.blog.RenameBlogResponse;
import com.slog.service.BlogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
public class BlogRestController {

	private final BlogService blogService;

	@PostMapping("/rename/{blogId}")
	public Response<RenameBlogResponse> renameBlog(
		@RequestBody @Valid final RenameBlogRequest request,
		@PathVariable final Long blogId,
		final Authentication authentication) {

		String memberEmail = authentication.getName();

		return Response.success(blogService.renameBlog(memberEmail, request, blogId));
	}
}
