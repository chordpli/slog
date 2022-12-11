package com.slog.config;

import com.slog.service.MemberService;
import com.slog.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final MemberService memberService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);

        // token 안보내면 block
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("authorization이 없습니다");
            filterChain.doFilter(request, response);
            return;
        }

        // Token을 꺼내자.
        String token = authorization.split(" ")[1];

        // Token Expired 여부 확인
        if (JwtUtil.isExpired(token, secretKey)) {
            log.info("Token이 만료 되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        HashMap<String, String> memberInfo = JwtUtil.getMemberInfo(token, secretKey);
        log.info("memberInfo = {}", memberInfo);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberInfo, null, List.of(new SimpleGrantedAuthority("USER")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
