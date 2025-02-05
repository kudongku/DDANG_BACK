package com.soulware.user_service_back.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    @GetMapping("/info")
    public ResponseEntity<String> validateEmail(
        Authentication authentication
    ) {
        String username = authentication.getName();
        return ResponseEntity.ok(username);
    }

}
