package com.soulware.user_service_back.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests((request) -> {
                request.requestMatchers(
                    "/api/auth/*",
                    "/v3/api-docs/**",       // OpenAPI 3 문서 관련 엔드포인트
                    "/swagger-ui/**",        // Swagger UI 관련 엔드포인트
                    "/swagger-ui.html",      // Swagger UI 메인 페이지
                    "/swagger-resources/**", // Swagger 리소스
                    "/webjars/**",           // 웹자바 리소스
                    "/configuration/ui",     // Swagger UI 설정
                    "/configuration/security"// Swagger 보안 설정
                ).permitAll();
                request.anyRequest().authenticated();
            });

        return http.build();
    }

}
