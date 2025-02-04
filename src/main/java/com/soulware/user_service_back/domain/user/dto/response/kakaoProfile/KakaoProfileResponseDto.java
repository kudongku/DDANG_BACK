package com.soulware.user_service_back.domain.user.dto.response.kakaoProfile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class KakaoProfileResponseDto {

    private Long id;

    @JsonProperty("connected_at")
    private String connectedAt;

    private KakaoProfileProperties properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

}
