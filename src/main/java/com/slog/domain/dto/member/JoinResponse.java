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

	private Long memberNo;
	private String memberEmail;
	private String memberNickname;

	public static JoinResponse of(Member member) {
		return JoinResponse.builder()
			.memberNo(member.getMemberNo())
			.memberEmail(member.getMemberEmail())
			.memberNickname(member.getMemberNickname())
			.build();
	}
}
