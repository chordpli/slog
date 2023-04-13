package com.slog.domain.dto.blog;

import com.slog.domain.entity.Blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RenameBlogResponse {

	private Long blogId;
	private String blogName;

	public static RenameBlogResponse fromEntity(Blog blog) {
		return RenameBlogResponse.builder()
			.blogId(blog.getBlogId())
			.blogName(blog.getBlogTitle())
			.build();
	}
}
