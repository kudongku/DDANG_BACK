package com.soulware.user_service_back.global.auth;

import com.soulware.user_service_back.global.exception.CustomNotAuthenticatedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "JwtService")
@Component
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;

    public static final String TOKEN_TYPE = "Bearer ";
    public static final Long ACCESS_TOKEN_EXPIRED_MS = 1000 * 60 * 60L; // 1 hour
    public static final Long REFRESH_TOKEN_EXPIRED_MS = 1000 * 60 * 60 * 24L; // 1 day

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createJwt(Long userId, String email, Long expiredMs) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("userId", userId);

        long currentTimeMillis = System.currentTimeMillis();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(currentTimeMillis))
            .setExpiration(new Date(currentTimeMillis + expiredMs))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String getValidTokenOrThrow(String bearerToken) {
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(TOKEN_TYPE)) {
            throw new CustomNotAuthenticatedException("유효하지 않은 token type");
        }

        String token = bearerToken.substring(TOKEN_TYPE.length());

        if (isExpired(token)) {
            throw new CustomNotAuthenticatedException("만료된 token");
        }

        return token;
    }

    public boolean isExpired(String token) {
        try {
            Date now = new Date();

            Date expirationDate = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

            return expirationDate.before(now);
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            throw new CustomNotAuthenticatedException("유효하지 않은 토큰입니다.");
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

}