package com.soulware.user_service_back.domain.user.service;

import com.soulware.user_service_back.domain.user.dto.response.KakaoTokenResponseDto;
import com.soulware.user_service_back.domain.user.dto.response.UserLoginResponseDto;
import com.soulware.user_service_back.domain.user.dto.response.kakaoProfile.KakaoProfileResponseDto;
import com.soulware.user_service_back.domain.user.entity.User;
import com.soulware.user_service_back.domain.user.repository.UserRepository;
import com.soulware.user_service_back.global.auth.JwtService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j(topic = "Oauth2Service")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class Oauth2Service {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String client;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirect;

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;

    @Transactional
    public UserLoginResponseDto kakaoLogin(String code) {
        String oAuthToken = requestToken(code);
        String email = requestProfile(oAuthToken);

        User user = userRepository.getUserByEmail(email).orElseGet(() -> {
            User newUser = new User(email);
            return userRepository.save(newUser);
        });

        String token = jwtService.createJwt(user.getId(), user.getEmail());

        return new UserLoginResponseDto(token);
    }

    private String requestToken(String accessCode) {
//        URI build
        UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString("https://kauth.kakao.com/oauth/token")
            .queryParam("grant_type", "authorization_code")
            .queryParam("client_id", client)
            .queryParam("redirect_url", redirect)
            .queryParam("code", accessCode);

//        create HttpEntity
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
            MediaType.valueOf("application/x-www-form-urlencoded;charset=utf-8")
        );
        HttpEntity<String> kakaoTokenRequest = new HttpEntity<>(headers);

//        exchange
        ResponseEntity<KakaoTokenResponseDto> response = restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.POST,
            kakaoTokenRequest,
            KakaoTokenResponseDto.class
        );

        log.info("KakaoTokenResponse : {}", response.getBody());

        return Objects.requireNonNull(response.getBody()).getAccessToken();
    }

    private String requestProfile(String oAuthToken) {
//        create httpEntity
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(oAuthToken);
        HttpEntity<String> kakaoProfileRequest = new HttpEntity<>(headers);

//        exchange
        ResponseEntity<KakaoProfileResponseDto> response = restTemplate.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.GET,
            kakaoProfileRequest,
            KakaoProfileResponseDto.class
        );

        log.info("KakaoProfileResponseDto : {}", response.getBody());

        return Objects.requireNonNull(response.getBody()).getKakaoAccount().getEmail();
    }

}
