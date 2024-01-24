package com.example.catchroom_be.global.jwt.service;

import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.global.jwt.dto.JwtPayload;
import com.example.catchroom_be.global.exception.CustomAuthenticationException;
import com.example.catchroom_be.global.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MeJWTService {

    @Value("${JWT_TOKEN_SECRET_KEY}")
    private String secretKey;


    private final long accessTokenValidTime = 30 * 60*  1000L; // access토큰의 유효시간 (30분)
    private final long refreshTokenValidTime = 3000 * 60 * 1000L; //refresh 토큰의 유효시간 (3000분)


    public String createAccessToken(JwtPayload jwtPayload) {
        return Jwts.builder()
                .claim("name", jwtPayload.name())
                .claim("phoneNumber", jwtPayload.phoneNumber())
                .claim("nickName",jwtPayload.nickName())
                .claim("email",jwtPayload.email())
                .setIssuer("catchroom")
                .setIssuedAt(new Date())
                .setExpiration(new Date(jwtPayload.issuedAt().getTime() + accessTokenValidTime))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }


    public String createRefreshToken(JwtPayload jwtPayload) {

        try {// 1초의 지연 시간을 생성
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new UserException(ErrorCode.SERVER_ERROR);
        }

        return Jwts.builder()
                .claim("name", jwtPayload.name())
                .claim("phoneNumber", jwtPayload.phoneNumber())
                .claim("nickName",jwtPayload.nickName())
                .claim("email",jwtPayload.email())
                .setIssuer("catchroom")
                .setIssuedAt(new Date())
                .setExpiration(new Date(jwtPayload.issuedAt().getTime() + refreshTokenValidTime))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

   /* public boolean isTokenExpired(JwtPayload jwtPayload) {
        Date expirationDate = jwtPayload.issuedAt();
        Date now = new Date();
        return now.after(expirationDate);

    }*/


    public JwtPayload verifyToken(String jwtToken) throws CustomAuthenticationException {


        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();


            return JwtPayload.builder()
                    .email(claims.get("email", String.class))
                    .nickName(claims.get("nickName", String.class))
                    .phoneNumber(claims.get("phoneNumber", String.class))
                    .name(claims.get("name", String.class))
                    .issuedAt(claims.getIssuedAt())
                    /*.issuedAt(claims.get("issuedAt", Date.class))*/
                    .build();


        } catch (ExpiredJwtException e) {
            throw new CustomAuthenticationException("엑세스 토큰이 만료되었습니다.",5001);
        } catch (MalformedJwtException e) {
            throw new CustomAuthenticationException("유효하지 않은 엑세스 토큰입니다.",5000);
        } catch (SignatureException e) {
            throw new CustomAuthenticationException("유효하지 않은 엑세스 토큰입니다.",5000);
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            throw new CustomAuthenticationException("유효하지 않은 엑세스 토큰입니다.",5000);
        }
    }
}
