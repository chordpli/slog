package com.slog.controller;

import com.slog.service.MemberService;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    MemberService userService;

    public MemberController(MemberService userService) {
        this.userService = userService;
    }
}
