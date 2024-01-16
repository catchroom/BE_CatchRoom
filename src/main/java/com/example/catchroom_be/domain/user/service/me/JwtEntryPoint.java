package com.example.catchroom_be.domain.user.service.me;

import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.global.exception.CustomAuthenticationException;
import com.example.catchroom_be.global.exception.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<Object> apiResponse = null;

        if (authException instanceof CustomAuthenticationException) {
            // CustomAuthenticationException 유형의 예외에 대한 처리
            CustomAuthenticationException customException = (CustomAuthenticationException) authException;
            apiResponse = ApiResponse.create(customException.getErrorCode(), ErrorMessage.createErrorMessage(customException.getMessage()));

        }
        else {
            log.trace(authException.getMessage());
            apiResponse = ApiResponse.create(response.getStatus(), authException.getMessage());


        }

        objectMapper.writeValue(response.getOutputStream(),apiResponse);
    }
}
