package com.soulware.user_service_back.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UserValidateEmailResponseDto {
    private boolean isExist;
}
