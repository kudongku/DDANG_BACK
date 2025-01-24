package com.soulware.user_service_back.domain.user.dto.request;

import com.soulware.user_service_back.global.annotaion.Sensitive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequestDto {

    private String email;

    @Sensitive
    private String password;

}
