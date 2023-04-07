package com.slog.fixture;

import com.slog.domain.entity.Member;
import com.slog.domain.enums.MemberStatus;

public class Fixture {
	public static Member getMemberFixture(){
		return Member.builder()
			.memberNo(1L)
			.memberEmail("member@fixture.com")
			.memberNickname("fixture_member")
			.memberPassword("fixturePassword123$")
			.memberStatus(MemberStatus.USER)
			.build();
	}
}
