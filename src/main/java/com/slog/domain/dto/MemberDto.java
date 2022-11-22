package com.slog.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @NoArgsConstructor
public class MemberDto {

    
    private Long no;
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String memberEmail;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 4, max = 16, message = "4자리 이상 16자리 이하의 비밀번호를 입력해주세요.")
    private String memberPassword;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String memberNickname;

    @NotBlank(message = "성별을 선택해주세요.")
    private Integer memberSex;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    private String memberPhoneNumber;

    @Builder
    public MemberDto(Long no, String memberEmail, String memberPassword, String memberNickname, Integer memberSex, String memberPhoneNumber) {
        this.no = no;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberNickname = memberNickname;
        this.memberSex = memberSex;
        this.memberPhoneNumber = memberPhoneNumber;
    }
}
