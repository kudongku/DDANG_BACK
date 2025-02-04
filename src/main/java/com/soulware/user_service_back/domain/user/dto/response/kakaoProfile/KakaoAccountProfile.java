package com.soulware.user_service_back.domain.user.dto.response.kakaoProfile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class KakaoAccountProfile {

    private String nickname;

    @JsonProperty("is_default_nickname")
    private Boolean isDefaultNickname;

}
