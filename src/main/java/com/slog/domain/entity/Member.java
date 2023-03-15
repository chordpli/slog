package com.slog.domain.entity;

import com.slog.domain.enums.MemberStatus;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Member implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberNo;

	private String memberEmail;
	private String memberPassword;
	private String memberNickname;
	private MemberStatus memberStatus;
	private String memberSNSProvider;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(memberStatus.name()));
	}

	@Override
	public String getPassword() {
		return memberPassword;
	}

	@Override
	public String getUsername() {
		return memberEmail;
	}

	@Override
	public boolean isAccountNonExpired() {
		return memberStatus.isAccountNonExpired(); // MemberStatus를 기반으로 반환
	}

	@Override
	public boolean isAccountNonLocked() {
		return memberStatus.isAccountNonLocked(); // MemberStatus를 기반으로 반환
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return memberStatus.isCredentialsNonExpired(); // MemberStatus를 기반으로 반환
	}

	@Override
	public boolean isEnabled() {
		return memberStatus.isEnabled();
	}
}
