package com.slog.domain.entity;

import com.slog.exception.ErrorCode;
import com.slog.exception.SlogAppException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long blogId;

	private String blogTitle;

	private String blogLogoUrl;

	public static Blog of(String memberNickname) {
		return Blog.builder()
			.blogLogoUrl("basic img url")
			.blogTitle(memberNickname + "님의 블로그")
			.build();
	}

	public void rename(String name) {
		if (name.length() > 25 || name.isEmpty()) {
			throw new SlogAppException(ErrorCode.INPUTS_THAT_DO_NOT_MEET_REQUIREMENTS, ErrorCode.INPUTS_THAT_DO_NOT_MEET_REQUIREMENTS.getMessage());
		}
		this.blogTitle = name;
	}
}
