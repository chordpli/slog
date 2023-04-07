package com.slog.service;

import org.springframework.stereotype.Service;

import com.slog.domain.dto.blog.RenameBlogRequest;
import com.slog.domain.dto.blog.RenameBlogResponse;
import com.slog.domain.entity.Blog;
import com.slog.domain.entity.Member;
import com.slog.exception.ErrorCode;
import com.slog.exception.SlogAppException;
import com.slog.repository.BlogRepository;
import com.slog.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {

	private final BlogRepository blogRepository;
	private final MemberRepository memberRepository;

	public Blog createBlog(String memberNickname) {
		return blogRepository.save(Blog.of(memberNickname));
	}

	public RenameBlogResponse renameBlog(String memberEmail, RenameBlogRequest request, Long blogId) {
		Member member = memberRepository.findByMemberEmail(memberEmail)
			.orElseThrow(() -> {
			throw new SlogAppException(ErrorCode.NOT_FOUND, ErrorCode.NOT_FOUND.getMessage());
		});
		;
		Blog blog = blogRepository.findById(blogId)
			.orElseThrow(() -> {
				throw new SlogAppException(ErrorCode.NOT_FOUND, ErrorCode.NOT_FOUND.getMessage());
			});

		blog.rename(request.getName());
		blogRepository.save(blog);

		return RenameBlogResponse.builder().blogId(blog.getBlogId()).blogName(blog.getBlogTitle()).build();
	}
}
