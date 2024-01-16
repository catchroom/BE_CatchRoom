package com.example.catchroom_be.global.config;

import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.domain.user.service.me.JwtEntryPoint;
import com.example.catchroom_be.domain.user.service.me.MeJWTService;
import com.example.catchroom_be.global.exception.CustomAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtFilterConfig extends OncePerRequestFilter {

    private static final String AUTHENTICATION_SCHEME_JWT = "Bearer";
    private final MeJWTService meJWTService;
    private final UserEntityRepository userEntityRepository;
    private final JwtEntryPoint jwtEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String rawAuthHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (rawAuthHeaderValue == null) {
                filterChain.doFilter(request, response);
                return;
            }

            String authHeaderValue = rawAuthHeaderValue.substring(7).trim();

            if (isValidAuthHeader(authHeaderValue)) {
                throw new CustomAuthenticationException("유효하지 않은 엑세스 토큰입니다.",5000);
            }

            JwtPayload jwtPayload = meJWTService.verifyToken(authHeaderValue);

            if (jwtPayload == null || meJWTService.isTokenExpired(jwtPayload)) {

                throw new CustomAuthenticationException("토큰 기한이 만료되었습니다.",5001);
            }

            Optional<User> result = userEntityRepository.findByEmail(jwtPayload.email());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken.authenticated(result.get().getEmail(),authHeaderValue, Collections.emptyList());
            SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(usernamePasswordAuthenticationToken);

        } catch (AuthenticationException e) {
            jwtEntryPoint.commence(request,response,e);
        }
    }

    private static boolean isValidAuthHeader(String authHeaderValue) {
        return !StringUtils.startsWithIgnoreCase(authHeaderValue, AUTHENTICATION_SCHEME_JWT)
                && authHeaderValue.equalsIgnoreCase(AUTHENTICATION_SCHEME_JWT);
    }






}
