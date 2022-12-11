package com.slog.domain.dto;

import com.slog.domain.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @NoArgsConstructor
@Builder
@AllArgsConstructor
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

    private String memberAuthKey;

    private MemberStatus memberStatus;

    private String memberSnsProvider;


}
