package com.soulware.user_service_back.domain.user.dto.response;

import com.soulware.user_service_back.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDto {

    private String email;
    private String address;

    public UserInfoResponseDto(User user) {
        this.email = user.getEmail();
        this.address = user.getTown().getAddress();
    }

}
