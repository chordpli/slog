package com.slog.domain.enums;

public enum MemberStatus {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");

	private final String role;

	MemberStatus(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}
}
