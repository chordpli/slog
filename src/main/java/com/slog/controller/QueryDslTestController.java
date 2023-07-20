package com.slog.controller;

import com.slog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class QueryDslTestController {

    private final MemberService memberService;

    @GetMapping
    public void testQueryDsl() {
        memberService.testQueryDsl();
    }
}
