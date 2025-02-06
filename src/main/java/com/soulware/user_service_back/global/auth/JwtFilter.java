package com.soulware.user_service_back.global.auth;

import com.soulware.user_service_back.global.config.SecurityConfig;
import com.soulware.user_service_back.global.exception.CustomTokenExpiredException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JwtFilter")
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
//        allowedUri Check
        String requestedUri = request.getRequestURI();

        if (isAllowedUrl(requestedUri)) {
            filterChain.doFilter(request, response);
            return;
        }

//        token valid check
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token;

        try {
            token = jwtService.getValidTokenOrThrow(bearerToken);
        } catch (CustomTokenExpiredException e) {
            log.error("JWT Expired: {}", e.getMessage());
            response.setStatus(401);
            return;
        }

//        set context in security contest holder
        Authentication authentication = getAuthenticationTokenFromToken(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationTokenFromToken(String token) {
        Claims claims = jwtService.getClaimsFromToken(token);

        return new UsernamePasswordAuthenticationToken(
            claims.get("email", String.class),
            null,
            List.of(new SimpleGrantedAuthority("role"))
        );
    }

    private boolean isAllowedUrl(String uri) {
        return Arrays.stream(SecurityConfig.ALLOWED_URLS)
            .anyMatch(pattern ->
                matcher.match(pattern, uri)
            );
    }

}
