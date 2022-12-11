package com.slog.utils;

import com.slog.domain.enums.MemberStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtil {

    public static HashMap<String, String> getMemberInfo(String token, String secretKey) {
        HashMap<String, String> memberInfo = new HashMap<>();

        String memberNickname = getMemberNickname(token, secretKey);
        String memberEmail = getMemberEmail(token, secretKey);
        String memberStatus = getMemberStatus(token, secretKey);

        memberInfo.put("memberNickname", memberNickname);
        memberInfo.put("memberEmail", memberEmail);
        memberInfo.put("memberStatus", memberStatus);

        return memberInfo;
    }

    private static String getMemberStatus(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("memberStatus", String.class);
    }

    private static String getMemberEmail(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("memberEmail", String.class);
    }

    public static String getMemberNickname(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("memberNickname", String.class);
    }



    public static boolean isExpired(String token, String secretKey) {
        log.info("token = {}", token);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public static String createJwt(String memberEmail, String memberNickname,  MemberStatus memberStatus, String secretKey, Long expiredMs) {
        Claims claims = Jwts.claims();
        claims.put("memberNickname", memberNickname);
        claims.put("memberEmail", memberEmail);
        claims.put("memberStatus", memberStatus);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
