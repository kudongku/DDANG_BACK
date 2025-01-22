package com.soulware.user_service_back.domain.user.controller;

import com.soulware.user_service_back.domain.user.dto.request.UserSignupRequestDto;
import com.soulware.user_service_back.domain.user.dto.request.UserValidateEmailResponseDto;
import com.soulware.user_service_back.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final UserService userService;

    @GetMapping("/validate")
    public ResponseEntity<UserValidateEmailResponseDto> validateEmail(
        @RequestParam String email
    ) {
        Boolean isExist = userService.isExistEmail(email);
        return ResponseEntity.ok(
            new UserValidateEmailResponseDto(isExist)
        );
    }

    @PostMapping("/signup")
    public void signup(
        @RequestBody UserSignupRequestDto userSignupRequestDto
    ) {
        userService.signup(userSignupRequestDto);
    }

}
