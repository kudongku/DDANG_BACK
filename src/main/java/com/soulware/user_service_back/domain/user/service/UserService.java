package com.soulware.user_service_back.domain.user.service;

import static com.soulware.user_service_back.global.auth.JwtService.ACCESS_TOKEN_EXPIRED_MS;
import static com.soulware.user_service_back.global.auth.JwtService.REFRESH_TOKEN_EXPIRED_MS;

import com.soulware.user_service_back.domain.town.entity.Town;
import com.soulware.user_service_back.domain.town.service.TownService;
import com.soulware.user_service_back.domain.user.dto.request.TokenRefreshRequestDto;
import com.soulware.user_service_back.domain.user.dto.request.UserLocationRequestDto;
import com.soulware.user_service_back.domain.user.dto.request.UserLoginRequestDto;
import com.soulware.user_service_back.domain.user.dto.request.UserSignupRequestDto;
import com.soulware.user_service_back.domain.user.dto.response.TokenResponseDto;
import com.soulware.user_service_back.domain.user.entity.User;
import com.soulware.user_service_back.domain.user.repository.UserRepository;
import com.soulware.user_service_back.global.auth.JwtService;
import com.soulware.user_service_back.global.exception.CustomIllegalArgumentException;
import io.jsonwebtoken.Claims;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TownService townService;

    @Transactional
    public TokenResponseDto signup(UserSignupRequestDto userSignupRequestDto) {
        if (isExistEmail(userSignupRequestDto.getEmail())) {
            throw new CustomIllegalArgumentException(
                "이미 존재하는 이메일입니다. email : " + userSignupRequestDto.getEmail()
            );
        }

        String encodedPassword = passwordEncoder.encode(userSignupRequestDto.getPassword());

        User user = new User(
            userSignupRequestDto.getEmail(),
            encodedPassword
        );

        userRepository.save(user);

        String token = jwtService.createJwt(
            user.getId(),
            user.getEmail(),
            ACCESS_TOKEN_EXPIRED_MS
        );
        String refreshToken = jwtService.createJwt(
            user.getId(),
            user.getEmail(),
            REFRESH_TOKEN_EXPIRED_MS
        );

        return new TokenResponseDto(token, refreshToken);
    }

    public Boolean isExistEmail(String email) {
        User user = userRepository.getUserByEmail(email).orElse(null);
        return Objects.nonNull(user);
    }

    public TokenResponseDto login(
        UserLoginRequestDto userLoginRequestDto
    ) {
        User user = userRepository.getUserByEmail(userLoginRequestDto.getEmail()).orElseThrow(
            () -> new CustomIllegalArgumentException("없는 유저입니다.")
        );

        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomIllegalArgumentException("틀린 비밀번호입니다.");
        }

        String token = jwtService.createJwt(
            user.getId(),
            user.getEmail(),
            ACCESS_TOKEN_EXPIRED_MS
        );
        String refreshToken = jwtService.createJwt(
            user.getId(),
            user.getEmail(),
            REFRESH_TOKEN_EXPIRED_MS
        );

        return new TokenResponseDto(token, refreshToken);
    }

    public TokenResponseDto refresh(TokenRefreshRequestDto tokenRefreshRequestDto) {
        String refreshToken = tokenRefreshRequestDto.getRefreshToken();

        if (jwtService.isExpired(refreshToken)) {
            throw new CustomIllegalArgumentException("만료된 refresh token입니다.");
        }

        Claims claims = jwtService.getClaimsFromToken(refreshToken);
        String email = claims.get("email", String.class);
        long userId = claims.get("userId", Integer.class);

        User user = userRepository.getUserByEmail(email).orElseThrow(
            () -> new CustomIllegalArgumentException("유효하지 않은 토큰입니다.")
        );

        if (user.getId() != userId) {
            throw new CustomIllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        String token = jwtService.createJwt(
            user.getId(),
            user.getEmail(),
            ACCESS_TOKEN_EXPIRED_MS
        );

        return new TokenResponseDto(token, refreshToken);
    }

    @Transactional
    public void setLocation(UserLocationRequestDto userLocationRequestDto, String email) {
        User user = userRepository.getUserByEmail(email).orElseThrow(
            () -> new CustomIllegalArgumentException("없는 유저입니다.")
        );
        Town town = townService.getTownByUserLocationRequestDto(userLocationRequestDto);
        user.setTown(town);
    }

}
