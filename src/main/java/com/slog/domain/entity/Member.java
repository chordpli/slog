package com.slog.domain.entity;

import com.slog.domain.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    private Long memberNo;

    @Column(unique = true, name = "member_email")
    private String memberEmail;

    @Column(name = "member_password")
    private String memberPassword;
    @Column(name = "member_nickname")
    private String memberNickname;
    @Column(name = "member_sex")
    private Integer memberSex;
    @Column(name = "member_phone_number")
    private String memberPhoneNumber;

    @Builder
    public Member(Long memberNo, String memberEmail, String memberPassword, String memberNickname, Integer memberSex, String memberPhoneNumber) {
        this.memberNo = memberNo;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberNickname = memberNickname;
        this.memberSex = memberSex;
        this.memberPhoneNumber = memberPhoneNumber;
    }

    public static Member createUser(MemberDto userDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .memberNickname(userDto.getMemberNickname())
                .memberEmail(userDto.getMemberEmail())
                .memberPassword(passwordEncoder.encode(userDto.getMemberPassword()))
                .memberSex(userDto.getMemberSex())
                .memberPhoneNumber(userDto.getMemberPhoneNumber())
                .build();
    }
}
