package com.soulware.user_service_back.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class UserValidateEmailResponseDto {
    private boolean isExist;
}
