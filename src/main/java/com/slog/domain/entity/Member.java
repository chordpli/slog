package com.slog.domain.entity;

import com.slog.domain.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    @Column(unique = true, nullable = true, name = "member_email")
    private String memberEmail;

    @Column(nullable = true, name = "member_password")
    private String memberPassword;

    @Column(unique = true, nullable = true, name = "member_nickname")
    private String memberNickname;

    @Column(name = "member_auth_key")
    private String memberAuthKey;

    @Column(name = "member_status")
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @Column(name = "member_sns_provider")
    private String memberSnsProvider;

}
