package com.slog.domain.enums;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResultCode {
	SUCCESS("200", "OK", HttpStatus.OK),
	INVALID_ARGUMENT("400", "Bad Request", HttpStatus.BAD_REQUEST),
	UNAUTHORIZED("401", "Unauthorized", HttpStatus.UNAUTHORIZED),
	FORBIDDEN("403", "Forbidden", HttpStatus.FORBIDDEN),
	NOT_FOUND("404", "Not Found", HttpStatus.NOT_FOUND),
	INTERNAL_SERVER_ERROR("500", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

	private final String code;
	private final String message;
	private final HttpStatus httpStatus;
}