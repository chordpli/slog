package com.slog.controller;

import com.slog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

}
