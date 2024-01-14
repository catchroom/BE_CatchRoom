package com.example.catchroom_be.domain.user.service.me;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MeJWTService {

    @Value("${JWT_TOKEN_SECRET_KEY}")
    private String secretKey;
    private final long accessTokenValidTime = 30 * 60 * 1000L; // access토큰의 유효시간 (30분)
    private final long refreshTokenValidTime = 3000 * 60 * 1000L; //refresh 토큰의 유효시간 (3000분)


    public String createAccessToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
