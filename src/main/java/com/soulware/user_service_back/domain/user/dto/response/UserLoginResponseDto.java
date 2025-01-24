package com.soulware.user_service_back.domain.user.dto.response;

import com.soulware.user_service_back.global.util.JwtUtil;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserLoginResponseDto {

    private final String tokenType = JwtUtil.tokenType;
    private final String token;

    public UserLoginResponseDto(String token) {
        this.token = token;
    }

}
