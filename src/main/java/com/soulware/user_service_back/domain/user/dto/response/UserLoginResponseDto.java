package com.soulware.user_service_back.domain.user.dto.response;

import com.soulware.user_service_back.global.annotaion.Sensitive;
import com.soulware.user_service_back.global.auth.JwtService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class UserLoginResponseDto {

    private final String tokenType = JwtService.TOKEN_TYPE;

    @Sensitive
    private String token;

    @Sensitive
    private String refreshToken;

    public UserLoginResponseDto(
        String token,
        String refreshToken
    ) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

}
