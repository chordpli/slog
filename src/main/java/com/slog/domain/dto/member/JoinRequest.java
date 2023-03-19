package com.slog.domain.dto.member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.slog.domain.entity.Member;
import com.slog.domain.enums.MemberStatus;
import com.slog.exception.ErrorCode;
import com.slog.exception.SlogAppException;
import com.slog.utils.PasswordValidator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class JoinRequest {

	@NotBlank
	@Email
	private String memberEmail;

	@NotBlank
	@Size(min = 8, max = 16)
	private String memberPassword;

	@NotBlank
	@Size(max = 12)
	private String memberNickname;

	public static Member toEntity(JoinRequest request, PasswordEncoder passwordEncoder) {
		if (!PasswordValidator.isValid(request.getMemberPassword())) {
			throw new SlogAppException(ErrorCode.INVALID_PASSWORD, "비밀번호는 최소 8자리 이상이며, 대문자, 소문자, 숫자, 특수문자를 모두 포함해야 합니다.");
		}

		return Member.builder()
			.memberEmail(request.getMemberEmail())
			.memberPassword(passwordEncoder.encode(request.getMemberPassword()))
			.memberNickname(request.getMemberNickname())
			.memberStatus(MemberStatus.USER)
			.build();
	}
}
