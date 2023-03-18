package com.slog.exception;

import static com.slog.exception.ErrorCode.*;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slog.domain.Response;
import com.slog.domain.enums.ResultCode;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");
        ErrorCode errorCode;

        log.debug("log: exception: {} ", exception);

        if (exception.equals(UNKNOWN_ERROR.name())) {
            log.info("알 수 없는 에러가 발생하였습니다.");
            setResponse(response, UNKNOWN_ERROR);

        }
        if (exception.equals(INVALID_TOKEN.name())) {
            log.info("토큰이 만료되었습니다.");
            setResponse(response, INVALID_TOKEN);
        }

        if (exception.equals(INVALID_PERMISSION.name())) {
            log.info("권한이 없습니다.");
            setResponse(response, INVALID_PERMISSION);
        }
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {

        ResultCode resultCode = errorCode.getResultCode();
        Response<Map<String, Object>> errorResponse = Response.error(resultCode, Map.of("errorCode", errorCode.name(), "message", errorCode.getMessage()));
        ResponseEntity<Object> responseEntity = errorResponse.toResponseEntity();

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(responseEntity.getStatusCodeValue());

        response.getWriter().println(objectMapper.writeValueAsString(responseEntity.getBody()));
    }
}