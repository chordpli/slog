package com.slog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SlogAppException extends RuntimeException {

    private ErrorCode errorCode;

    public SlogAppException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
