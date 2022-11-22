package com.slog.controller;

import com.slog.domain.dto.MemberDto;
import com.slog.domain.entity.Member;
import com.slog.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/member")
public class MemberController {
    MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String loginForm(){
        System.out.println("로그인 폼 입니다.");
        return "member/login";
    }

    @PostMapping("/login")
    public String login(MemberDto memberDto, Model model) {
        Optional<Member> member = memberService.login(memberDto);
        member.ifPresent(value -> model.addAttribute("member", value));
        System.out.println(member);
        return"redirect:/index";
    }



}
