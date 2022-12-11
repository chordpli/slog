package com.slog.domain.dto;

import com.slog.domain.entity.Member;
import com.slog.domain.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberJoinRequest {

    private String memberEmail;
    private String memberPassword;
    private String memberNickname;
    private String memberAuthKey;
    private MemberStatus authStatus;
    // private String memberSnsProvider;

    public Member toEntity(String password, String authKey) {
        return Member.builder()
                .memberEmail(this.memberEmail)
                .memberPassword(password)
                .memberNickname(this.memberNickname)
                .memberAuthKey(authKey)
                .memberStatus(MemberStatus.NOT_PERMITTED)
                .build();
    }


}
