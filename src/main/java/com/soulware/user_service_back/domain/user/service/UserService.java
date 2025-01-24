package com.soulware.user_service_back.domain.user.service;

import com.soulware.user_service_back.domain.user.dto.request.UserLoginRequestDto;
import com.soulware.user_service_back.domain.user.dto.request.UserSignupRequestDto;
import com.soulware.user_service_back.domain.user.dto.response.UserLoginResponseDto;
import com.soulware.user_service_back.domain.user.entity.User;
import com.soulware.user_service_back.domain.user.repository.UserRepository;
import com.soulware.user_service_back.global.exception.ExistEmailException;
import com.soulware.user_service_back.global.util.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(UserSignupRequestDto userSignupRequestDto) {
        if (isExistEmail(userSignupRequestDto.getEmail())) {
            throw new ExistEmailException(
                "이미 존재하는 이메일입니다. email : " + userSignupRequestDto.getEmail()
            );
        }

        String encodedPassword = passwordEncoder.encode(userSignupRequestDto.getPassword());

        User user = new User(
            userSignupRequestDto.getEmail(),
            encodedPassword
        );

        userRepository.save(user);
    }

    public Boolean isExistEmail(String email) {
        User user = userRepository.getUserByEmail(email).orElse(null);
        return Objects.nonNull(user);
    }

    public UserLoginResponseDto login(
        UserLoginRequestDto userLoginRequestDto
    ) {
        User user = userRepository.getUserByEmail(userLoginRequestDto.getEmail()).orElseThrow(
            () -> new IllegalArgumentException("없는 유저입니다.")
        );

        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("틀린 비밀번호입니다.");
        }

        String token = jwtUtil.createJwt(user.getId(), user.getEmail());

        return new UserLoginResponseDto(token);
    }
}
