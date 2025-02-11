package com.soulware.user_service_back.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLocationRequestDto {

    private String address;
    private double latitude;
    private double longitude;

}
