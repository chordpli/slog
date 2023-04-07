package com.slog.service;

import org.springframework.stereotype.Service;

import com.slog.domain.entity.Blog;
import com.slog.domain.entity.Member;
import com.slog.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {

	private final BlogRepository blogRepository;

	public Blog createBlog(String memberNickname) {
		return blogRepository.save(Blog.of(memberNickname));
	}
}
