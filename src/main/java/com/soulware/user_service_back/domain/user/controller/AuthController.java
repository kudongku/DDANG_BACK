package com.soulware.user_service_back.domain.user.controller;

import com.soulware.user_service_back.domain.user.dto.request.TokenRefreshRequestDto;
import com.soulware.user_service_back.domain.user.dto.request.UserLoginRequestDto;
import com.soulware.user_service_back.domain.user.dto.request.UserSignupRequestDto;
import com.soulware.user_service_back.domain.user.dto.response.UserLoginResponseDto;
import com.soulware.user_service_back.domain.user.dto.response.UserValidateEmailResponseDto;
import com.soulware.user_service_back.domain.user.service.Oauth2Service;
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
    private final Oauth2Service oauth2Service;

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

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(
        @RequestBody UserLoginRequestDto userLoginRequestDto
    ) {
        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);
        return ResponseEntity.ok(userLoginResponseDto);
    }

    @GetMapping("/authorization/kakao")
    public ResponseEntity<UserLoginResponseDto> kakaoLogin(
        @RequestParam("code") String code
    ) {
        UserLoginResponseDto userLoginResponseDto = oauth2Service.kakaoLogin(code);
        return ResponseEntity.ok(userLoginResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserLoginResponseDto> refresh(
        @RequestBody TokenRefreshRequestDto tokenRefreshRequestDto
    ) {
        UserLoginResponseDto userLoginResponseDto = userService.refresh(tokenRefreshRequestDto);
        return ResponseEntity.ok(userLoginResponseDto);
    }

}
