package com.slog.repository;

import com.slog.domain.entity.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

	Optional<Member> findByMemberEmail(String email);

	boolean existsByMemberEmail(String memberEmail);

	boolean existsByMemberNickname(String memberNickname);
}
