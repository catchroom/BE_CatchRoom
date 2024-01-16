package com.example.catchroom_be.domain.user.service.me;

import com.example.catchroom_be.global.config.JwtPayload;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.Date;

@Service
public class MeJWTService {

    @Value("${JWT_TOKEN_SECRET_KEY}")
    private String secretKey;
    private final long accessTokenValidTime = 30 * 60 * 1000L; // access토큰의 유효시간 (30분)
    private final long refreshTokenValidTime = 3000 * 60 * 1000L; //refresh 토큰의 유효시간 (3000분)


    public String createAccessToken(JwtPayload jwtPayload) {
        return Jwts.builder()
                .claim("name", jwtPayload.name())
                .claim("phoneNumber", jwtPayload.phoneNumber())
                .claim("nickName",jwtPayload.nickName())
                .claim("email",jwtPayload.email())
                .setIssuer("catchroom")
                .setIssuedAt(jwtPayload.issuedAt())
                .setExpiration(new Date(jwtPayload.issuedAt().getTime() + accessTokenValidTime))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }


    public String createRefreshToken(JwtPayload jwtPayload) {
        return Jwts.builder()
                .claim("name", jwtPayload.name())
                .claim("phoneNumber", jwtPayload.phoneNumber())
                .claim("nickName",jwtPayload.nickName())
                .claim("email",jwtPayload.email())
                .setIssuer("catchroom")
                .setIssuedAt(jwtPayload.issuedAt())
                .setExpiration(new Date(jwtPayload.issuedAt().getTime() + refreshTokenValidTime))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public boolean isTokenExpired(JwtPayload jwtPayload) {
        Date expirationDate = jwtPayload.expiration();
        Date now = new Date();
        return now.after(expirationDate);

    }


    public JwtPayload verifyToken(String jwtToken) throws AuthenticationException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            return JwtPayload.builder()
                    .email(claims.get("email", String.class))
                    .nickName(claims.get("nickName", String.class))
                    .phoneNumber(claims.get("phoneNumber", String.class))
                    .name(claims.get("name", String.class))
                    .issuedAt(claims.get("issuedAt", Date.class))
                    .build();


        } catch (ExpiredJwtException e) {
            throw new AuthenticationException();
        } catch (MalformedJwtException e) {
            throw new AuthenticationException();
        } catch (SignatureException e) {
            throw new AuthenticationException();
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            throw new AuthenticationException();
        }
    }
}
