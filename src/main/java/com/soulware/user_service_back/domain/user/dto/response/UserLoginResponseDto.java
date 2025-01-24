package com.soulware.user_service_back.domain.user.dto.response;

import com.soulware.user_service_back.global.annotaion.Sensitive;
import com.soulware.user_service_back.global.util.JwtUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class UserLoginResponseDto {

    private final String tokenType = JwtUtil.tokenType;

    @Sensitive
    private String token;

    public UserLoginResponseDto(String token) {
        this.token = token;
    }

}
