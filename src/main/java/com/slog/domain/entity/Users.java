package com.slog.domain.entity;

import com.slog.domain.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Users {

    @Id
    private Long no;

    @Column(unique = true)
    private String email;
    private String password;
    private String nickName;
    private String sex;
    private Integer phoneNumber;

    @Builder
    public Users(Long no, String email, String password, String nickName, String sex, Integer phoneNumber) {
        this.no = no;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
    }

    public static Users createUser(UserDto userDto, PasswordEncoder passwordEncoder) {
        return Users.builder()
                .nickName(userDto.getNickName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .sex(userDto.getSex())
                .phoneNumber(userDto.getPhoneNumber())
                .build();
    }
}
