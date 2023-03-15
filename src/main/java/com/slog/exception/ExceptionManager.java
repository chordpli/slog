package com.slog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.slog.domain.Response;
import com.slog.domain.enums.ResultCode;

@RestControllerAdvice
public class ExceptionManager {

  @ExceptionHandler(SlogAppException.class)
  public ResponseEntity<?> SlogAppExceptionHandler(SlogAppException e) {
    Map<String, Object> result = new HashMap<>();
    result.put("errorCode", e.getErrorCode());
    result.put("message", e.getMessage());
    return ResponseEntity.status(e.getErrorCode().getResultCode().getHttpStatus())
        .body(Response.error(e.getErrorCode().getResultCode(), result));
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<?> databaseExceptionHandler(DatabaseException e) {
    Map<String, Object> result = new HashMap<>();
    result.put("errorCode", e.getErrorCode());
    result.put("sqlErrorCode", e.getSqlErrorCode());
    result.put("message", e.getMessage());
    return ResponseEntity.status(e.getErrorCode().getResultCode().getHttpStatus())
        .body(Response.error(ResultCode.valueOf(e.getErrorCode().getResultCode().getHttpStatus().name()), result));
  }
}