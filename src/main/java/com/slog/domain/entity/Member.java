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
    private Long no;

    @Column(unique = true)
    private String email;
    private String password;
    private String nickName;
    private Integer sex;
    private String phoneNumber;

    @Builder
    public Member(Long no, String email, String password, String nickName, Integer sex, String phoneNumber) {
        this.no = no;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
    }

    public static Member createUser(MemberDto userDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .nickName(userDto.getNickName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .sex(userDto.getSex())
                .phoneNumber(userDto.getPhoneNumber())
                .build();
    }
}
