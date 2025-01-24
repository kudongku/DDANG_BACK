package com.soulware.user_service_back.global.util;

import io.jsonwebtoken.Claims;
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

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;
    private static final Long expiredMs = 1000 * 60 * 60L;
    public static final String tokenType = "Bearer ";

    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createJwt(Long userId, String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("userId", userId);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

}
