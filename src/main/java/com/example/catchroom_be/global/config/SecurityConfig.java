package com.example.catchroom_be.global.config;

import com.example.catchroom_be.domain.user.service.me.JwtEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsUtils;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtEntryPoint jwtEntryPoint;
    private final JwtFilterConfig jwtFilterConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrfConfig) ->
                        csrfConfig.disable()
                )
                .sessionManagement((config) ->
                        config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(config ->
                        config.disable())
                .formLogin((config) ->
                        config.disable())
                .exceptionHandling((config)->
                        config.authenticationEntryPoint(jwtEntryPoint))
                .headers((headersConfig)->
                        headersConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
                );

        http
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**","GET")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/v1/user/register","POST")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/v1/user/login","POST")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/v1/user/email/check","GET")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/v1/user/nickname/check","GET")).permitAll();


                    request.anyRequest().authenticated();
                }
                );

        http.addFilterBefore(jwtFilterConfig, UsernamePasswordAuthenticationFilter.class);

        return http.build();/**/
    }






}
