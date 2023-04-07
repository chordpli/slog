package com.slog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slog.domain.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
