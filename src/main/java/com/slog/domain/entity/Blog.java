package com.slog.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    @Id
    @Column(name = "blog_no")
    private Long blogNo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Member member;

    @Column(name = "blog_title")
    private String blogTitle;

    @Column(name = "blog_logo")
    private String blogLogo;

}
