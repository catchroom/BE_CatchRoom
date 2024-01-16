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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JwtFilterConfig extends OncePerRequestFilter {

    private static final String AUTHENTICATION_SCHEME_JWT = "Bearer";
    private final MeJWTService meJWTService;
    private final UserEntityRepository userEntityRepository;
    private final AuthenticationEntryPoint jwtEntryPoint;


  /*  //TODO 서비스로직에 인가 관련 코드 추가 후 v1/sales 삭제예정_정혜민
    private static final List<String> EXCLUDE_URLS = Arrays.asList("/v1/user/register", "/v1/user/login","/v1/user/nickname/check","v1/user/email/check","v1/sales/**");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return EXCLUDE_URLS.contains(request.getServletPath());
    }*/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String rawAuthHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (rawAuthHeaderValue == null) {
                filterChain.doFilter(request, response);
                return;
            }


            String authHeaderValue = rawAuthHeaderValue.substring(7).trim();
            JwtPayload jwtPayload = meJWTService.verifyToken(authHeaderValue);
            Optional<User> result = userEntityRepository.findByEmail(jwtPayload.email());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    UsernamePasswordAuthenticationToken.authenticated(result.get(),authHeaderValue,List.of(new SimpleGrantedAuthority("USER")));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        } catch (AuthenticationException e) {

            jwtEntryPoint.commence(request,response,e);
            return;
        }

        filterChain.doFilter(request, response);

    }

    private static boolean isValidAuthHeader(String authHeaderValue) {
        return !StringUtils.startsWithIgnoreCase(authHeaderValue, AUTHENTICATION_SCHEME_JWT)
                && authHeaderValue.equalsIgnoreCase(AUTHENTICATION_SCHEME_JWT);
    }






}
