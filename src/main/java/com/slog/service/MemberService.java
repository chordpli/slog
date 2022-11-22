package com.slog.service;

import com.slog.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    MemberRepository userRepository;

    public MemberService(MemberRepository userRepository) {
        this.userRepository = userRepository;
    }
}
