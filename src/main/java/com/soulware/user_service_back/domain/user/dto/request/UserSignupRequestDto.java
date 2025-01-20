package com.soulware.user_service_back.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class UserSignupRequestDto {

    private String email;
    private String password;

}
