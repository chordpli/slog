package com.slog.service;

import org.springframework.stereotype.Service;

import com.slog.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {

	private final BlogRepository blogRepository;

}
