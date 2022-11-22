package com.slog.repository;

import com.slog.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);
}
