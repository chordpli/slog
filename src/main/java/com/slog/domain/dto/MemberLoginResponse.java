package com.slog.domain.dto;

import com.slog.domain.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MemberLoginResponse {

    private String memberEmail;
    private String memberNickname;
    private MemberStatus memberStatus;

}
