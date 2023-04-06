package com.slog.domain.dto.member;

import com.slog.domain.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class JoinResponse {

	private Long memberId;
	private String memberEmail;
	private String memberNickname;
	private Long blogId;

	public static JoinResponse of(Member member) {
		return JoinResponse.builder()
			.memberId(member.getMemberId())
			.memberEmail(member.getMemberEmail())
			.memberNickname(member.getMemberNickname())
			.blogId(member.getBlog().getBlogId())
			.build();
	}
}
