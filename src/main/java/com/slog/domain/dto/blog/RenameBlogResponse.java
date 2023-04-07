package com.slog.domain.dto.blog;

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
}
