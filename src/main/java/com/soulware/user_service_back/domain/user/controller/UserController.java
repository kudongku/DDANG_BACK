package com.soulware.user_service_back.domain.user.controller;

import com.soulware.user_service_back.domain.user.dto.request.UserLocationRequestDto;
import com.soulware.user_service_back.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<String> validateEmail(
        Authentication authentication
    ) {
        String username = authentication.getName();
        return ResponseEntity.ok(username);
    }

    @PostMapping("/location")
    public void setLocation(
        @RequestBody UserLocationRequestDto userLocationRequestDto,
        Authentication authentication
    ) {
        userService.setLocation(userLocationRequestDto, authentication.getName());
    }

}
