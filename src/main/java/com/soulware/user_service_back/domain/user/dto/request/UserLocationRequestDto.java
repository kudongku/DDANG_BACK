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

    private String address="서울특별시 중구 소공동";
    private double latitude=126.97978948415194;
    private double longitude=37.56389256147569;

}
