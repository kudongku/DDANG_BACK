package com.soulware.user_service_back.domain.user.dto.response.kakaoProfile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class KakaoAccount {

    private String email;

    @JsonProperty("is_email_verified")
    private Boolean isEmailVerified;

    @JsonProperty("has_email")
    private Boolean hasEmail;

    @JsonProperty("profile_nickname_needs_agreement")
    private Boolean profileNicknameNeedsAgreement;

    @JsonProperty("email_needs_agreement")
    private Boolean emailNeedsAgreement;

    @JsonProperty("is_email_valid")
    private Boolean isEmailValid;

    private KakaoAccountProfile profile;

}
