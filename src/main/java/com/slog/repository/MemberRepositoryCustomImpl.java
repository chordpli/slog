package com.slog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.slog.domain.entity.Member;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.slog.domain.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public void testQueryDsl() {
        List<Member> memberList = queryFactory
                .selectFrom(member)
                .fetch();

        for (Member member : memberList) {
            System.out.println(member.getMemberNickname());
        }
    }
}
