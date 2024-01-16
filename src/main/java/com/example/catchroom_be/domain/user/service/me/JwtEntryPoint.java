package com.example.catchroom_be.domain.user.service.me;

import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.global.exception.CustomAuthenticationException;
import com.example.catchroom_be.global.exception.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
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
            /*authException.printStackTrace();
            ErrorMessage errorMessage = ErrorMessage.createErrorMessage("인증을 제외한 에러가 발생했습니다.")
            apiResponse = ApiResponse.create(HttpServletResponse.SC_UNAUTHORIZED, errorMessage); */
            apiResponse = ApiResponse.create(response.getStatus(), authException.getMessage());


        }

        objectMapper.writeValue(response.getOutputStream(),apiResponse);
    }
}
